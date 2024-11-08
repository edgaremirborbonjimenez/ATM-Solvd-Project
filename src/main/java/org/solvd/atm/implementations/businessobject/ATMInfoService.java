package org.solvd.atm.implementations.businessobject;

import org.solvd.atm.domain.atm.ATM;
import org.solvd.atm.domain.files.ATMBillsInfo;
import org.solvd.atm.domain.files.ATMInfo;
import org.solvd.atm.implementations.data.ATMInfoDAO;
import org.solvd.atm.interfaces.businessObjects.IATMInfoService;
import org.solvd.atm.interfaces.data.IATMInfoDAO;
import org.solvd.atm.utils.DollarDenomination;

import java.util.*;

public class ATMInfoService implements IATMInfoService {

    IATMInfoDAO atmInfoDAO;

    public ATMInfoService(){}
    @Override
    public ATM createNewATM(String atmSerie) {
        ATMInfo atmInfo = null;
        atmInfo = atmInfoDAO.findATMBySerie(atmSerie);
        if(atmInfo == null){
            atmInfo = atmInfoDAO.createNewATM(atmSerie);
        }
        ATM atm = new ATM();
        atm.setSerieNumber(atmInfo.getSerie());
        Map<DollarDenomination,Integer> bills = new HashMap<>();
        List<ATMBillsInfo> atmBillsInfos = new LinkedList<>(Arrays.stream(atmInfo.getBills()).toList());
        atmBillsInfos.forEach(bill ->{
            bills.put(bill.getBill(),bill.getAmount());
        });
        atm.setMoney(bills);
        return atm;
    }

    @Override
    public void updateATMStadisticsBySerie(String atmSerie) {
        atmInfoDAO.updateATMStadisticsBySerie(atmSerie);
    }

    @Override
    public void updateATMBillBySerie(String atmSerie, ATM atm) {
        atmInfoDAO.updateATMBillsBySerie(atmSerie,atm);
    }

    @Override
    public ATM FinalcreateNewATM(String atmSerie) {
        ATMInfo atmInfo = null;
        atmInfo = atmInfoDAO.FinalfindATMBySerie(atmSerie);
        if(atmInfo == null){
            atmInfo = atmInfoDAO.FinalcreateNewATM(atmSerie);
        }
        ATM atm = new ATM();
        atm.setSerieNumber(atmInfo.getSerie());
        Map<DollarDenomination,Integer> bills = new HashMap<>();
        List<ATMBillsInfo> atmBillsInfos = new LinkedList<>(Arrays.stream(atmInfo.getBills()).toList());
        atmBillsInfos.forEach(bill ->{
            bills.put(bill.getBill(),bill.getAmount());
        });
        atm.setMoney(bills);
        return atm;
    }

    @Override
    public void FinalupdateATMStadisticsBySerie(String atmSerie) {
        atmInfoDAO.FinalupdateATMStadisticsBySerie(atmSerie);

    }

    @Override
    public void FinalupdateATMBillBySerie(String atmSerie, ATM atm) {
        atmInfoDAO.FinalupdateATMBillsBySerie(atmSerie,atm);
    }

    @Override
    public void setATMInfoDAO(IATMInfoDAO atmInfoDAO) {
        this.atmInfoDAO = atmInfoDAO;
    }
}
