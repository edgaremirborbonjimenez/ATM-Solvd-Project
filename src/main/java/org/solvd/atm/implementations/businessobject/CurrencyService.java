package org.solvd.atm.implementations.businessobject;

import org.solvd.atm.domain.Account;
import org.solvd.atm.domain.AccountCurrency;
import org.solvd.atm.domain.Currency;
import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.interfaces.businessObjects.ICurrencyService;
import org.solvd.atm.interfaces.data.IAccountDAO;
import org.solvd.atm.interfaces.data.ICurrencyDAO;
import org.solvd.atm.utils.exceptions.DataException;

import java.util.ArrayList;
import java.util.List;

public class CurrencyService implements ICurrencyService {

    private ICurrencyDAO currencyDAO;
    private IAccountDAO accountDAO;

    public void setAccountDAO(IAccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public void setCurrencyDAO(ICurrencyDAO currencyDAO){
        this.currencyDAO = currencyDAO;
    }


    @Override
    public List<CurrencyDTO> getAccountCurrenciesBalanceByAccountNum(String accountNumber) {
        validateAccount(accountNumber);
        Account account = accountDAO.findAccountByNumber(accountNumber);

        List<CurrencyDTO> currencyDTOs = new ArrayList<>();
        for (AccountCurrency accountCurrency : account.getAccountCurrency()) {
            CurrencyDTO currencyDTO = new CurrencyDTO();
            currencyDTO.setName(accountCurrency.getCurrency().getName());
            currencyDTO.setAmount(accountCurrency.getAmount());
            currencyDTO.setEquivalentToDollar(accountCurrency.getCurrency().getEquivalentToDollar());
            currencyDTOs.add(currencyDTO);
        }
        return currencyDTOs;
    }

    @Override
    public List<CurrencyDTO> getAccountCurrencies(String accountNumber) {
        validateAccount(accountNumber);

        List<Currency> currencies = currencyDAO.getAccountCurrenciesByAccountNum(accountNumber);
        List<CurrencyDTO> currencyDTOs = new ArrayList<>();

        for (Currency currency : currencies) {
            CurrencyDTO currencyDTO = new CurrencyDTO();
            currencyDTO.setName(currency.getName());
            currencyDTO.setEquivalentToDollar(currency.getEquivalentToDollar());
            currencyDTO.setAmount(null);
            currencyDTOs.add(currencyDTO);
        }
        return currencyDTOs;
    }

    @Override
    public boolean validateAccountCurrencyBalance(String accountNumber, Double amount, String currency) {
        validateAccount(accountNumber);

        if (amount <= 0) {
            return false;
        }

        AccountCurrency accountCurrency = currencyDAO.getAccountCurrencyWithBalanceByCurrencyName(
                accountNumber, currency);

        if (accountCurrency == null) {
            return false;
        }
        // if amount is greater we can make the transaction
        return accountCurrency.getAmount() >= amount;
    }

    @Override
    public CurrencyDTO findCurrencyByName(String currencyName) {
        Currency currency = currencyDAO.findCurrencyByName(currencyName);
        if (currency == null) {
            throw new DataException("no currency found with name: "+currencyName);
        }

        CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setName(currency.getName());
        currencyDTO.setEquivalentToDollar(currency.getEquivalentToDollar());
        currencyDTO.setAmount(null);

        return currencyDTO;
    }

    private void validateAccount(String accountNumber){
        Account account = accountDAO.findAccountByNumber(accountNumber);
        if (account == null) {
            throw new DataException("no account found with number: "+accountNumber);
        }
    }
}
