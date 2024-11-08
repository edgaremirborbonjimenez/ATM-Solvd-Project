package org.solvd.atm.interfaces.businessObjects;

import org.solvd.atm.dtos.DepositDTO;

public interface IDepositService {
    DepositDTO deposit(String accountNumber, Double amount, String currency,String atmSerial);
}
