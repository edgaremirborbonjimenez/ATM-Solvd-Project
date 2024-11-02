package org.solvd.atm.interfaces.data;

import org.solvd.atm.domain.Deposit;

public interface IDepositDAO {
    Deposit deposit(String accountNumber,Double amount,String currency);
}
