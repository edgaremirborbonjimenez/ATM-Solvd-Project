package org.solvd.atm.interfaces.data;

import org.solvd.atm.domain.atm.AccountCurrency;
import org.solvd.atm.domain.atm.Currency;

import java.util.List;

public interface ICurrencyDAO {
    List<Currency> getAccountCurrenciesByAccountNum(String accountNumber);
    Currency findCurrencyByName(String name);
    AccountCurrency getAccountCurrencyWithBalanceByCurrencyName(String accountNumber, String currency);
}
