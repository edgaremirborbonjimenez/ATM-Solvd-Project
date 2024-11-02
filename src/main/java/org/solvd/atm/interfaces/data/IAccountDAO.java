package org.solvd.atm.interfaces.data;

import org.solvd.atm.domain.Account;

public interface IAccountDAO {
    Account findAccountByNumberAndPin(String accountNumber,String PIN);
    Account findAccountByNumber(String accountNumber);

}
