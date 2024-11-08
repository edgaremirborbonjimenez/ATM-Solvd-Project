package org.solvd.atm.interfaces.data;

import org.solvd.atm.domain.atm.Deposit;

public interface IDepositDAO {
    Deposit deposit(String accountNumber,Double amount,String currency,String atmSerial);
}
