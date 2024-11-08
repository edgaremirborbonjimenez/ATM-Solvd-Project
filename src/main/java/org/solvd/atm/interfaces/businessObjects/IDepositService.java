package org.solvd.atm.interfaces.businessObjects;

import org.solvd.atm.dtos.DepositDTO;
import org.solvd.atm.interfaces.data.IDepositDAO;

public interface IDepositService {
    DepositDTO deposit(String accountNumber, Double amount, String currency,String atmSerial);
    void setDepositDAO(IDepositDAO depositDAO);
    void setCurrencyService(ICurrencyService currencyService);
}
