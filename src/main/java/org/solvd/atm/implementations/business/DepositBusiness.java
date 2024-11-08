package org.solvd.atm.implementations.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.atm.ATM;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.dtos.DepositDTO;
import org.solvd.atm.interfaces.business.IDepositBusiness;
import org.solvd.atm.interfaces.business.IOptionsMenuBusiness;
import org.solvd.atm.interfaces.businessObjects.IATMInfoService;
import org.solvd.atm.interfaces.businessObjects.ICurrencyService;
import org.solvd.atm.interfaces.businessObjects.IDepositService;
import org.solvd.atm.interfaces.businessObjects.ISessionInfoService;
import org.solvd.atm.interfaces.presentation.IDepositScreen;
import org.solvd.atm.sessionmanager.SessionManager;
import org.solvd.atm.utils.DollarDenomination;
import org.solvd.atm.utils.exceptions.BusinessException;

import java.util.List;
import java.util.Map;

public class DepositBusiness implements IDepositBusiness {

    private static final Logger logger = LogManager.getLogger(DepositBusiness.class);
    private IDepositService depositService;
    private ICurrencyService currencyService;
    private AccountDTO accountSession;
    private ATM atm;
    ISessionInfoService sessionInfoService;
    IDepositScreen depositScreen;
    IOptionsMenuBusiness optionsMenuBusiness;
    IATMInfoService atmInfoService;

    @Override
    public List<CurrencyDTO> getAccountCurrencies() {
        validateSessionState();
        return currencyService.getAccountCurrencies(accountSession.getNumber());
    }

    @Override
    public DepositDTO deposit(Map<DollarDenomination, Integer> denomination, int amount, String currency) {
        validateSessionState();
        validateDepositInput(denomination, amount, currency);

        try {
            CurrencyDTO targetCurrencyDTO = currencyService.findCurrencyByName(currency);
            if (targetCurrencyDTO == null) {
                throw new BusinessException("Invalid target currency: " + currency);
            }

            double convertedAmount = amount;
            if (!currency.equals(DollarDenomination.codeName)) {
                convertedAmount = amount * targetCurrencyDTO.getEquivalentToDollar();
            }

            List<CurrencyDTO> accountCurrencies = currencyService.getAccountCurrencies(accountSession.getNumber());
            if (!isCurrencyAccepted(accountCurrencies, currency)) {
                throw new BusinessException("Account does not support deposits in " + currency);
            }

            DepositDTO depositDTO = depositService.deposit(accountSession.getNumber(), convertedAmount, currency,atm.getSerieNumber());
            if(depositDTO != null){
                updateATMInventory(denomination);
            }
            this.sessionInfoService.addDepositToSessionBySessionId(
                    SessionManager.getInstance().getSessionId(this.accountSession.getNumber()),
                    depositDTO,
                    currency,
                    convertedAmount);
            this.optionsMenuBusiness.setATM(this.atm);
            this.atmInfoService.updateATMBillBySerie(this.atm.getSerieNumber(),this.atm);
            return depositDTO;

        } catch (Exception e) {
            logger.error("Error processing deposit for account {}: {}",
                    accountSession.getNumber(), e.getMessage());
            throw new BusinessException("Failed to process deposit "+ e);
        }
    }
    @Override
    public void setSessionAccountReference(AccountDTO accountReference) {
        if (accountReference == null) {
            throw new BusinessException("No account selected for deposit operation");
        }
        if (accountReference.getNumber().trim().isEmpty()) {
            throw new BusinessException("Account number cannot be null or empty");
        }
        this.accountSession = accountReference;
    }

    @Override
    public void setATM(ATM atm) {
        if (atm == null) {
            throw new BusinessException("ATM reference cannot be null");
        }
        this.atm = atm;
    }

    //map validator
    private void validateDepositInput(Map<DollarDenomination, Integer> denomination, int amount, String currency) {
        if (denomination == null) {
            throw new BusinessException("Denomination map cannot be null or empty");
        }
        if (amount <= 0) {
            throw new BusinessException("Amount must be positive");
        }
        if (currency == null || currency.trim().isEmpty()) {
            throw new BusinessException("Currency cannot be null or empty");
        }
        // check denomination counts are positive
        for (Map.Entry<DollarDenomination, Integer> entry : denomination.entrySet()) {
            if (entry.getValue() < 0) {
                throw new BusinessException(
                        "Invalid count for denomination " + entry.getKey() + ": " + entry.getValue()
                );
            }
        }
        //check that the amount i want to deposit matches the product of bills
        int calculatedAmount = denomination.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getValue() * entry.getValue())
                .sum();

        if (calculatedAmount != amount) {
            throw new BusinessException(
                    "Provided amount does not match the sum of denominations. " +
                            "Expected: " + amount + ", Calculated: " + calculatedAmount
            );
        }
    }

    private boolean isCurrencyAccepted(List<CurrencyDTO> accountCurrencies, String currency) {
        return accountCurrencies.stream()
                .anyMatch(curr -> curr.getName().equals(currency));
    }

    private void validateSessionState() {
        if (this.accountSession == null) {
            throw new BusinessException("No account selected for deposit operation");
        }
        if (this.atm == null) {
            throw new BusinessException("No ATM selected for deposit operation");
        }
    }

    private void updateATMInventory(Map<DollarDenomination, Integer> depositedDenominations) {
        Map<DollarDenomination, Integer> atmInventory = this.atm.getMoney();

        depositedDenominations.forEach((denomination, count) -> {
            int currentCount = atmInventory.getOrDefault(denomination, 0);
            atmInventory.put(denomination, currentCount + count);
        });

        logger.info("Updated ATM inventory after the deposit");
    }

    public void setDepositService(IDepositService depositService){
        this.depositService = depositService;
    }

    public void setCurrencyService(ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public void setSessionInfoService(ISessionInfoService sessionInfoService) {
        this.sessionInfoService = sessionInfoService;
    }

    public void setAccountSession(AccountDTO accountSession) {
        this.accountSession = accountSession;
    }

    public void setAtm(ATM atm) {
        this.atm = atm;
    }

    public void setDepositScreen(IDepositScreen depositScreen) {
        this.depositScreen = depositScreen;
    }

    public void setOptionsMenuBusiness(IOptionsMenuBusiness optionsMenuBusiness) {
        this.optionsMenuBusiness = optionsMenuBusiness;
    }

    public void setAtmInfoService(IATMInfoService atmInfoService) {
        this.atmInfoService = atmInfoService;
    }
}
