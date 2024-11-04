package org.solvd.atm.interfaces.businessObjects;

import org.solvd.atm.domain.ATM;
import org.solvd.atm.dtos.DepositDTO;

public interface IDepositService {
    DepositDTO deposit(String accountNumber, Double amount, String currency, ATM atm);
}
