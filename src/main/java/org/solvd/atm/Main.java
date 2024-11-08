package org.solvd.atm;

import org.solvd.atm.domain.files.DepositInfo;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.DepositDTO;
import org.solvd.atm.dtos.TransactionDTO;
import org.solvd.atm.dtos.WithdrawDTO;
import org.solvd.atm.implementations.businessobject.SessionInfoService;
import org.solvd.atm.implementations.data.SessionInfoDAO;

public class Main {
    public static void main(String[] args) {
        SessionInfoDAO dao = new SessionInfoDAO();
        SessionInfoService s = new SessionInfoService();
        s.setSessionInfoDAO(dao);

        s.createNewSession("ATM02","12345678");
        AccountDTO a = new AccountDTO();
        a.setNumber("987987564");
        AccountDTO a1 = new AccountDTO();
        a1.setNumber("321654789");
        DepositDTO depositInfo = new DepositDTO();
        depositInfo.setOriginAccount(a);
        depositInfo.setReferenceNumber("1546156");
        s.addDepositToSessionBySessionId(3,depositInfo,"MXN",800d);

        WithdrawDTO depositInf = new WithdrawDTO();
        depositInf.setOriginAccount(a);
        depositInf.setReferenceNumber("1546156");
        s.addWithdrawToSessionBySessionId(2,depositInf,"MXN",900d);

        TransactionDTO t = new TransactionDTO();

        t.setReferenceNumber("987987");
        t.setDestinationAccount(a);
        t.setOriginAccount(a);
        s.addTransferToSessionBySessionId(4,t,"USA",1000d);
    }
}
