package org.solvd.atm;

import org.solvd.atm.domain.atm.ATM;
import org.solvd.atm.domain.files.DepositInfo;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.DepositDTO;
import org.solvd.atm.dtos.TransactionDTO;
import org.solvd.atm.dtos.WithdrawDTO;
import org.solvd.atm.implementations.businessobject.SessionInfoService;
import org.solvd.atm.implementations.data.ATMDAO;
import org.solvd.atm.implementations.data.ATMInfoDAO;
import org.solvd.atm.implementations.data.SessionInfoDAO;
import org.solvd.atm.interfaces.businessObjects.IATMInfoService;
import org.solvd.atm.interfaces.data.IATMDAO;
import org.solvd.atm.interfaces.data.IATMInfoDAO;
import org.solvd.atm.utils.DollarDenomination;
import org.solvd.atm.utils.database.implementations.HikariCPDataSource;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
//        SessionInfoDAO dao = new SessionInfoDAO();
//        SessionInfoService s = new SessionInfoService();
//        s.setSessionInfoDAO(dao);
//
//        s.createNewSession("ATM02","12345678");
//        AccountDTO a = new AccountDTO();
//        a.setNumber("987987564");
//        AccountDTO a1 = new AccountDTO();
//        a1.setNumber("321654789");
//        DepositDTO depositInfo = new DepositDTO();
//        depositInfo.setOriginAccount(a);
//        depositInfo.setReferenceNumber("1546156");
//        s.addDepositToSessionBySessionId(3,depositInfo,"MXN",800d);
//
//        WithdrawDTO depositInf = new WithdrawDTO();
//        depositInf.setOriginAccount(a);
//        depositInf.setReferenceNumber("1546156");
//        s.addWithdrawToSessionBySessionId(2,depositInf,"MXN",900d);
//
//        TransactionDTO t = new TransactionDTO();
//
//        t.setReferenceNumber("987987");
//        t.setDestinationAccount(a);
//        t.setOriginAccount(a);
//        s.addTransferToSessionBySessionId(4,t,"USA",1000d);

        IATMInfoDAO atmInfoDAO  = new ATMInfoDAO();
        atmInfoDAO = new ATMInfoDAO();
        atmInfoDAO.createNewATM("ATM001");


        HikariCPDataSource.getInstance().setPoolSize(5);
        IATMDAO iatmdao = new ATMDAO();
        List<String> series = new LinkedList<>();
        series.add("ATM123");
        series.add("ATM456");
//        System.out.println(iatmdao.findOneATMSerieWhichIsNotInTheList(series));
//        System.out.println(atmInfoDAO.findATMBySerie("ATM002").getSerie());
        ATM atm = new ATM();
        Map<DollarDenomination,Integer> list = new HashMap<>();
        list.put(DollarDenomination.$1,10);
        list.put(DollarDenomination.$2,2);
        list.put(DollarDenomination.$5,6);
        list.put(DollarDenomination.$10,100);
        list.put(DollarDenomination.$20,8);
        list.put(DollarDenomination.$50,4);
        list.put(DollarDenomination.$100,50);
        atm.setMoney(list);
//        atmInfoDAO.updateATMBillsBySerie("ATM123",atm);
        atmInfoDAO.updateATMStadisticsBySerie("ATM001");

    }
}
