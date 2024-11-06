package org.solvd.atm.implementations.businessobject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.Account;
import org.solvd.atm.domain.AccountCurrency;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.interfaces.businessObjects.IAccountService;
import org.solvd.atm.interfaces.data.IAccountDAO;
import org.solvd.atm.utils.exceptions.BusinessException;

import java.util.ArrayList;
import java.util.List;

public class AccountService implements IAccountService {
    private static final Logger logger = LogManager.getLogger(AccountService.class);

    private IAccountDAO accountDAO;

    public void setAccountDAO(IAccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    @Override
    public AccountDTO validateAccount(String accountNumber, String PIN) {
        if (accountNumber == null || PIN == null) {
            throw new BusinessException("Account number and PIN cannot be null");
        }
        Account account = accountDAO.findAccountByNumberAndPin(accountNumber, PIN);
        if (account == null) {
            throw new BusinessException("Invalid account number or PIN");
        }
        return convertToDTO(account);
    }
    @Override
    public boolean validateAccount(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new BusinessException("Account number cannot be null");
        }

        Account account = accountDAO.findAccountByNumber(accountNumber);
        return account != null;
    }

    private AccountDTO convertToDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setNumber(account.getNumber());

        return accountDTO;
    }
}
