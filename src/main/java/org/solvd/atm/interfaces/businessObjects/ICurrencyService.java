package org.solvd.atm.interfaces.businessObjects;

import org.solvd.atm.dtos.CurrencyDTO;

import java.util.List;

public interface ICurrencyService {
    List<CurrencyDTO> getAccountCurrenciesBalanceByAccountNum(String accountNumber);
    List<CurrencyDTO> getAccountCurrencies(String accountNumber);
    boolean validateAccountCurrencyBalance(String accountNumber,Double amount,String currency);
}
