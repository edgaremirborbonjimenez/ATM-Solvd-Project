package org.solvd.atm.interfaces.businessObjects;

import org.solvd.atm.dtos.AccountDTO;

public interface IAccountService {
    AccountDTO validateAccount(String accountNumber,String PIN);
    boolean validateAccount(String accountNumber);
}
