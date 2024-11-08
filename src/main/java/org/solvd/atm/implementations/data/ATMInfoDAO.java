package org.solvd.atm.implementations.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.atm.ATM;
import org.solvd.atm.domain.files.*;
import org.solvd.atm.domain.stadistics.AverageWithdraw;
import org.solvd.atm.domain.stadistics.LargestWithdraw;
import org.solvd.atm.domain.stadistics.TotalTransactions;
import org.solvd.atm.interfaces.data.IATMInfoDAO;
import org.solvd.atm.utils.DollarDenomination;
import org.solvd.atm.utils.database.implementations.HikariCPDataSource;
import org.solvd.atm.utils.exceptions.DataException;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ATMInfoDAO implements IATMInfoDAO {

    private static final Logger logger = LogManager.getLogger(ATMInfoDAO.class);

    String dataSourceBefore;
    String dataSourceAfter;
    ObjectMapper objectMapper;
    HikariCPDataSource dataSource;

    private String AVERAGE_WITHDRAWS_BY_ATM_SERIE = "SELECT a.serie_number, c.name AS currency_name, AVG(w.money) AS average_withdrawn FROM withdraws w JOIN atms a ON w.atm_id = a.id JOIN currencies c ON w.currency_id = c.id WHERE a.serie_number = ? GROUP BY c.name";
    private String TOTAL_TRANSACTIONS_BY_ATM_SERIE = "SELECT a.serie_number, COUNT(*) AS total_transactions FROM (SELECT atm_id FROM withdraws UNION ALL SELECT atm_id FROM deposits UNION ALL SELECT atm_id FROM transfers) t JOIN atms a ON t.atm_id = a.id WHERE a.serie_number = ?";
    private String LARGEST_WITHDRAW_BY_ATM_SERIE = "SELECT a.serie_number,c.name AS currency_name,MAX(w.money) AS largest_withdrawn FROM withdraws w JOIN atms a ON w.atm_id = a.id JOIN currencies c ON w.currency_id = c.id WHERE a.serie_number = ? GROUP BY c.name";

    public ATMInfoDAO(){
        Properties properties = new Properties();
        try (FileReader input = new FileReader("src/main/resources/env.properties")) {
            properties.load(input);
            dataSourceBefore = properties.getProperty("json.atm.before.info");
            dataSourceAfter = properties.getProperty("json.atm.after.info");
            this.dataSource = HikariCPDataSource.getInstance();
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new DataException("Something went wrong in SessionInfoDAO constructor");
        }
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.INDENT_OUTPUT,true);
    }

    @Override
    public ATMInfo createNewATM(String atmSerie) {
        try{
            FileInputStream file = new FileInputStream(dataSourceBefore);
            ATMInfo[] atmInfo;
            if(file.available()!=0){
                atmInfo = this.objectMapper.readValue(file,ATMInfo[].class);
            }else{
                atmInfo = new ATMInfo[0];
            }

            List<ATMInfo> atmList = new LinkedList<>(Arrays.stream(atmInfo).toList());
            ATMInfo newATM = initATM(atmSerie);
            atmList.add(newATM);
            ATMInfo[] arr = atmList.toArray(new ATMInfo[0]);
            objectMapper.writeValue(new File(dataSourceBefore),arr);
            return newATM;
        }catch(Exception e){
            logger.error(e.getMessage());
            throw new DataException("Something went wrong");
        }
    }

    private ATMInfo initATM(String serie){
        ATMStadisticsInfo atmStadisticsInfo = new ATMStadisticsInfo();
        atmStadisticsInfo.setAverageWithdraw(new AverageWithdraw());
        atmStadisticsInfo.setLargestWithdraw(new LargestWithdraw());
        atmStadisticsInfo.setTransactionCounter(new TotalTransactions());
        ATMInfo newATM = new ATMInfo();
        newATM.setSerie(serie);
        newATM.setStadistics(atmStadisticsInfo);
        ATMBillsInfo[] bills = new ATMBillsInfo[DollarDenomination.values().length];

        DollarDenomination[] denominations = DollarDenomination.values();

        Double money = 0d;
        for(int i = 0; i<denominations.length; i++){
            ATMBillsInfo atmBillsInfo = new ATMBillsInfo();
            atmBillsInfo.setBill(denominations[i]);
            atmBillsInfo.setAmount(100);
            money+= atmBillsInfo.getAmount();
            bills[i] = atmBillsInfo;
        }
        newATM.setBills(bills);
        newATM.setTotalMoney(money);
        return newATM;
    }

    @Override
    public void updateATMBillsBySerie(String atmSerie, ATM atm) {
        try{
            FileInputStream file = new FileInputStream(dataSourceBefore);
            ATMInfo[] atmInfo;
            if(file.available()==0){
                logger.error("Error: there are no ATMs to update");
                return;
            }
            atmInfo = this.objectMapper.readValue(file,ATMInfo[].class);
            List<ATMInfo> atmList = new LinkedList<>(Arrays.stream(atmInfo).toList());

            atmList.forEach(a-> {
                if(a.getSerie().equalsIgnoreCase(atmSerie)){
                    a = updateMoney(a,atm);
                }
            });

            ATMInfo[] arr = atmList.toArray(new ATMInfo[0]);
            objectMapper.writeValue(new File(dataSourceBefore),arr);
        }catch(Exception e){
            logger.error(e.getMessage());
            throw new DataException("Something went wrong");
        }
    }

    private ATMInfo updateMoney(ATMInfo atmInfo,ATM atm){

        Map<DollarDenomination,Integer> list = atm.getMoney();
        List<ATMBillsInfo> billsList = new LinkedList<>();
        list.forEach((k,v)->{
            ATMBillsInfo bill = new ATMBillsInfo();
            bill.setBill(k);
            bill.setAmount(v);
            billsList.add(bill);
        });
        atmInfo.setBills(billsList.toArray(new ATMBillsInfo[0]));
        return atmInfo;
    }

    @Override
    public void updateATMStadisticsBySerie(String atmSerie) {
        try{
            FileInputStream file = new FileInputStream(dataSourceBefore);
            ATMInfo[] atmInfos;
            if(file.available()==0){
                logger.error("Error: there are no ATMs to update");
                return;
            }
            atmInfos = this.objectMapper.readValue(file,ATMInfo[].class);
            List<ATMInfo> atmList = new LinkedList<>(Arrays.stream(atmInfos).toList());
            if(atmList.isEmpty()){
                logger.error("Error: there are no ATMs to update");
                return;
            }

        try(Connection connection = dataSource.getDataSource().getConnection();
            PreparedStatement statementAverageWithdraws = connection.prepareStatement(AVERAGE_WITHDRAWS_BY_ATM_SERIE);
            PreparedStatement statementTotalTransacions = connection.prepareStatement(TOTAL_TRANSACTIONS_BY_ATM_SERIE);
            PreparedStatement statementLargestWithdraw = connection.prepareStatement(LARGEST_WITHDRAW_BY_ATM_SERIE)) {

            statementAverageWithdraws.setString(1, atmSerie);
            statementTotalTransacions.setString(1, atmSerie);
            statementLargestWithdraw.setString(1, atmSerie);

            try (ResultSet rAverageWithdraws = statementAverageWithdraws.executeQuery();
                 ResultSet rTotalTransacions = statementTotalTransacions.executeQuery();
                 ResultSet rLargestWithdraw = statementLargestWithdraw.executeQuery()) {

                AverageWithdraw averageWithdraw = new AverageWithdraw();
                LargestWithdraw largestWithdraw = new LargestWithdraw();
                TotalTransactions transactions = new TotalTransactions();

                while (rAverageWithdraws.next()) {
                    averageWithdraw.put(rAverageWithdraws.getString("currency_name"), rAverageWithdraws.getDouble("average_withdrawn"));
                }
                if (rTotalTransacions.next()) {
                    transactions.setTotalTransactions(rTotalTransacions.getString("total_transactions"));
                }
                while (rLargestWithdraw.next()) {
                    largestWithdraw.put(rLargestWithdraw.getString("currency_name"), rLargestWithdraw.getDouble("largest_withdrawn"));
                }
                ATMStadisticsInfo atmStadisticsInfo  = new ATMStadisticsInfo();
                atmStadisticsInfo.setAverageWithdraw(averageWithdraw);
                atmStadisticsInfo.setLargestWithdraw(largestWithdraw);
                atmStadisticsInfo.setTransactionCounter(transactions);

                atmList.forEach(a -> {
                    if(a.getSerie().equalsIgnoreCase(atmSerie)){
                        a.setStadistics(atmStadisticsInfo);
                    }
                });
                ATMInfo[] arr = atmList.toArray(new ATMInfo[0]);
                objectMapper.writeValue(new File(dataSourceBefore),arr);
            }

        }
        }catch (SQLException | IOException e){
            logger.error(e.getMessage());
            throw new DataException(e.getMessage());
        }
    }

    @Override
    public ATMInfo findATMBySerie(String atmSerie) {
        try{
            FileInputStream file = new FileInputStream(dataSourceBefore);
            ATMInfo[] atmInfo;
            if(file.available()==0){
                return null;
            }
            atmInfo = this.objectMapper.readValue(file,ATMInfo[].class);
            List<ATMInfo> list = new LinkedList<>(Arrays.stream(atmInfo).filter(a->a.getSerie().equalsIgnoreCase(atmSerie)).toList());
            if(list.isEmpty()){
                return null;
            }
            return list.getFirst();
        }catch(Exception e){
            logger.error(e.getMessage());
            throw new DataException("Something went wrong");
        }
    }
}
