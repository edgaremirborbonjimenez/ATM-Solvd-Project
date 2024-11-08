package org.solvd.atm.interfaces.data;

import org.solvd.atm.domain.atm.Account;

public interface IAccountDAO {
    Account findAccountByNumberAndPin(String accountNumber,String PIN);
    Account findAccountByNumber(String accountNumber);

}
