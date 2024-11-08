package org.solvd.atm.interfaces.businessObjects;

import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.interfaces.data.IAccountDAO;

public interface IAccountService {
    AccountDTO validateAccount(String accountNumber,String PIN);
    boolean validateAccount(String accountNumber);
    void setAccountDAO(IAccountDAO accountDAO);
}
