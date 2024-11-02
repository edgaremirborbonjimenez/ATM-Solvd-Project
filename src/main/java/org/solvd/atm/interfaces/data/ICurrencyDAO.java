package org.solvd.atm.interfaces.data;

import org.solvd.atm.domain.Currency;

import java.util.List;

public interface ICurrencyDAO {
    List<Currency> getAccountCurrenciesByAccountNum(String accountNumber);
    Currency findCurrencyByName(String name);
    Currency getAccountCurrencyWithBalanceByCurrencyName(String accountNumber,String currency);
}
