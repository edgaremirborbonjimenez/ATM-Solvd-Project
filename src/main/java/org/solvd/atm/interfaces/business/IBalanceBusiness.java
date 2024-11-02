package org.solvd.atm.interfaces.business;

import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.CurrencyDTO;

import java.util.List;

public interface IBalanceBusiness {
    List<CurrencyDTO> getAllCurrenciesBalance();
    void setSessionAccountReference(AccountDTO account);
}
