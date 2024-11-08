package org.solvd.atm.implementations.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.files.*;
import org.solvd.atm.dtos.DepositDTO;
import org.solvd.atm.dtos.TransactionDTO;
import org.solvd.atm.dtos.WithdrawDTO;
import org.solvd.atm.implementations.businessobject.SessionInfoService;
import org.solvd.atm.interfaces.data.ISessionInfoDAO;
import org.solvd.atm.utils.exceptions.DataException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class SessionInfoDAO implements ISessionInfoDAO {
    private static final Logger logger = LogManager.getLogger(SessionInfoDAO.class);

    String dataSource;
    ObjectMapper objectMapper;

    public SessionInfoDAO(){
        Properties properties = new Properties();
        try (FileReader input = new FileReader("src/main/resources/env.properties")) {
            properties.load(input);
            dataSource = properties.getProperty("json.session.info");
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new DataException("Something went wrong in SessionInfoDAO constructor");
        }
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.INDENT_OUTPUT,true);
    }

    @Override
    public SessionInfo createNewSession(String atmSerie, String accountNumber){
        try{
            FileInputStream file = new FileInputStream(dataSource);
            SessionIndexesInfo sessions;
            if(file.available()!=0){
                sessions = this.objectMapper.readValue(file,SessionIndexesInfo.class);
            }else{
                sessions = new SessionIndexesInfo();
                sessions.setLastIndex(0);
                SessionInfo[] arr = new SessionInfo[0];
                sessions.setSessions(arr);
            }
            Integer newIndex = sessions.getLastIndex()+1;
            SessionInfo newSession = new SessionInfo();
            newSession.setId(newIndex);
            newSession.setAtmSerie(atmSerie);
            newSession.setTransactions( new TransactionInfo[0]);
            sessions.setLastIndex(newIndex);
            sessions.addSession(newSession);
            objectMapper.writeValue(new File(dataSource),sessions);
            return newSession;
        }catch(Exception e){
            logger.error(e.getMessage());
            throw new DataException("Something went wrong creating new Session in SessionInfoDAO");
        }
    }

    @Override
    public void addTransferToSessionBySessionId(Integer id, TransactionDTO transaction, String currencySent, Double amount) {
        try{
            FileInputStream file = new FileInputStream(dataSource);
            SessionIndexesInfo sessions;
            if(file.available()==0){
                logger.error("Error: can not add a new transfer to the session");
                return;
            }
            sessions = this.objectMapper.readValue(file,SessionIndexesInfo.class);
            TransferInfo newTransfer = new TransferInfo();
            newTransfer.setOrigin(transaction.getOriginAccount().getNumber());
            newTransfer.setDestination(transaction.getDestinationAccount().getNumber());
            newTransfer.setAmount(amount);
            newTransfer.setCurrency(currencySent);
            newTransfer.setReferenceNumber(transaction.getReferenceNumber());

            Arrays.stream(sessions.getSessions()).forEach(session -> {
                if(session.getId().equals(id)){
                    session.addTransaction(newTransfer);
                }
            });
            objectMapper.writeValue(new File(dataSource),sessions);
        }catch(Exception e){
            logger.error(e.getMessage());
            throw new DataException("Something went wrong creating new Session in SessionInfoDAO");
        }
    }

    @Override
    public void addDepositToSessionBySessionId(Integer id, DepositDTO deposit, String currencySent, Double amount) {
        try{
            FileInputStream file = new FileInputStream(dataSource);
            SessionIndexesInfo sessions;
            if(file.available()==0){
                logger.error("Error: can not add a new transfer to the session");
                return;
            }
            sessions = this.objectMapper.readValue(file,SessionIndexesInfo.class);
            DepositInfo newDeposit = new DepositInfo();
            newDeposit.setOrigin(deposit.getOriginAccount().getNumber());
            newDeposit.setAmount(amount);
            newDeposit.setCurrency(currencySent);
            newDeposit.setReferenceNumber(deposit.getReferenceNumber());

            Arrays.stream(sessions.getSessions()).forEach(session -> {
                if(session.getId().equals(id)){
                    session.addTransaction(newDeposit);
                }
            });
            objectMapper.writeValue(new File(dataSource),sessions);
        }catch(Exception e){
            logger.error(e.getMessage());
            throw new DataException("Something went wrong creating new Session in SessionInfoDAO");
        }
    }

    @Override
    public void addWithdrawToSessionBySessionId(Integer id, WithdrawDTO withdraw, String currencySent, Double amount) {
        try{
            FileInputStream file = new FileInputStream(dataSource);
            SessionIndexesInfo sessions;
            if(file.available()==0){
                logger.error("Error: can not add a new transfer to the session");
                return;
            }
            sessions = this.objectMapper.readValue(file,SessionIndexesInfo.class);
            WithdrawInfo withdrawInfo = new WithdrawInfo();
            withdrawInfo.setOrigin(withdraw.getOriginAccount().getNumber());
            withdrawInfo.setAmount(amount);
            withdrawInfo.setCurrency(currencySent);
            withdrawInfo.setReferenceNumber(withdraw.getReferenceNumber());

            Arrays.stream(sessions.getSessions()).forEach(session -> {
                if(session.getId().equals(id)){
                    session.addTransaction(withdrawInfo);
                }
            });
            objectMapper.writeValue(new File(dataSource),sessions);
        }catch(Exception e){
            logger.error(e.getMessage());
            throw new DataException("Something went wrong creating new Session in SessionInfoDAO");
        }
    }
}
