package org.solvd.atm.interfaces.businessObjects;

import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.interfaces.data.IAccountDAO;
import org.solvd.atm.interfaces.data.ICurrencyDAO;

import java.util.List;

public interface ICurrencyService {
    List<CurrencyDTO> getAccountCurrenciesBalanceByAccountNum(String accountNumber);
    List<CurrencyDTO> getAccountCurrencies(String accountNumber);
    boolean validateAccountCurrencyBalance(String accountNumber,Double amount,String currency);
    CurrencyDTO findCurrencyByName(String currencyName);
    void setAccountDAO(IAccountDAO accountDAO);
    void setCurrencyDAO(ICurrencyDAO currencyDAO);
}
