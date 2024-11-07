package org.solvd.atm.implementations.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.interfaces.business.IBalanceBusiness;
import org.solvd.atm.interfaces.businessObjects.ICurrencyService;
import org.solvd.atm.utils.exceptions.DataException;

import java.util.List;

public class BalanceBusiness implements IBalanceBusiness {
    private static final Logger logger = LogManager.getLogger(BalanceBusiness.class);

    private AccountDTO accountSession;
    private ICurrencyService currencyService;

    public void setCurrencyService(ICurrencyService currencyService){
        this.currencyService = currencyService;
    }

    @Override
    public List<CurrencyDTO> getAllCurrenciesBalance() {
        validateSession();
        try {
            List<CurrencyDTO> balances = currencyService.getAccountCurrenciesBalanceByAccountNum(accountSession.getNumber());

            if (balances.isEmpty()) {
                logger.warn("No currencies found for account: {}", accountSession.getNumber());
                return null;
            }

            logger.info("Retrieved {} currencies for account: {}", balances.size(), accountSession.getNumber());
            return balances;

        } catch (Exception e) {
            logger.error("Error retrieving currency balances for account: {}", accountSession.getNumber(), e);
            throw new DataException("Failed to retrieve currency balances "+ e);
        }
    }

    @Override
    public void setSessionAccountReference(AccountDTO account) {
        if (account == null) {
            logger.error("Attempted to set null account reference");
            throw new DataException("Account reference cannot be null");
        }
        logger.info("Setting session account reference for account: {}", account.getNumber());
        this.accountSession = account;
    }

    private void validateSession() {
        if (this.accountSession == null) {
            logger.error("Session validation failed - no active session");
            throw new DataException("No active session found");
        }
    }
}
