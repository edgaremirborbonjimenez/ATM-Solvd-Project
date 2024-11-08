package org.solvd.atm.implementations.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.atm.ATM;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.dtos.WithdrawDTO;
import org.solvd.atm.interfaces.business.IWithdrawBusiness;
import org.solvd.atm.interfaces.businessObjects.ICurrencyService;
import org.solvd.atm.interfaces.businessObjects.IWithdrawService;
import org.solvd.atm.utils.Denominator;
import org.solvd.atm.utils.DollarDenomination;
import org.solvd.atm.utils.exceptions.BusinessException;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class WithdrawBusiness implements IWithdrawBusiness {
    private static final Logger logger = LogManager.getLogger(WithdrawBusiness.class);

    private IWithdrawService withdrawService;
    private ICurrencyService currencyService;
    private AccountDTO sessionAccount;
    private ATM atm;

    public void setCurrencyService(ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public void setWithdrawService(IWithdrawService withdrawService) {
        this.withdrawService = withdrawService;
    }

    @Override
    public List<CurrencyDTO> getCurrenciesByAccount() {
        validateSessionState();
        return currencyService.getAccountCurrenciesBalanceByAccountNum(sessionAccount.getNumber());
    }

    @Override
    public List<EnumMap<DollarDenomination, Integer>> getDenominations(int amountToWithdraw) {
        //returns a map of valid options to extract from ATM to the presentation layer
        validateSessionState();

        if (amountToWithdraw <= 0) {
            throw new BusinessException("Invalid withdraw amount, must be higher than zero");
        }

        if (amountToWithdraw > getAtmMoney()) {
            throw new BusinessException("ATM has insufficient funds");
        }

        List<EnumMap<DollarDenomination, Integer>> results =
                Denominator.generateCombinations(amountToWithdraw, (EnumMap<DollarDenomination, Integer>) atm.getMoney());

        if (results.isEmpty()) {
            throw new BusinessException("Cannot provide exact change for this amount");
        }
        return results;
    }

    @Override /*withdrawAmount = USD to withdraw , currency = account currency to get the money */
    public WithdrawDTO doWithdraw(int withdrawAmount, Map<DollarDenomination, Integer> denomination, String currency) {
        validateSessionState();
        validateInputs(withdrawAmount,denomination,currency);

        //ATM cant handle this operation
        validateATMoperation(denomination,withdrawAmount);

        double amountInOriginalCurrency = withdrawAmount;
        if (!currency.equals("USD")) {
            CurrencyDTO currencyData = currencyService.findCurrencyByName(currency);
            amountInOriginalCurrency = withdrawAmount * currencyData.getEquivalentToDollar();
        }
        if (!currencyService.validateAccountCurrencyBalance(
                sessionAccount.getNumber(), amountInOriginalCurrency, currency)) {
            throw new BusinessException("Insufficient funds in "+currency+" account");
        }

        WithdrawDTO withdraw = withdrawService.doWithdraw(
                sessionAccount.getNumber(),
                amountInOriginalCurrency,
                currency
        );

        if (withdraw != null){
            updateATMInventory(denomination);
        }

        return withdraw;
    }

    @Override
    public void setSessionAccountReference(AccountDTO accountReference) {
        if (accountReference == null) {
            throw new BusinessException("No account selected for deposit operation");
        }
        if (accountReference.getNumber().trim().isEmpty()) {
            throw new BusinessException("Account number cannot be null or empty");
        }
        this.sessionAccount = accountReference;
    }

    @Override
    public void setATM(ATM ATM) {
        this.atm = ATM;
    }

    private void validateSessionState() {
        if (this.sessionAccount == null) {
            throw new BusinessException("No account selected for deposit operation");
        }
        if (this.atm == null) {
            throw new BusinessException("No ATM selected for deposit operation");
        }
    }

    private void validateInputs(int amount, Map<DollarDenomination, Integer> denomination, String currency) {
        if (amount <= 0) {
            throw new BusinessException("Invalid withdrawal amount");
        }
        if (denomination == null || denomination.isEmpty()) {
            throw new BusinessException("Invalid denominations");
        }
        if (currency == null || currency.trim().isEmpty()) {
            throw new BusinessException("Invalid currency");
        }

        int amountDenominations = denomination.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getValue() * entry.getValue())
                .sum();

        if (amountDenominations != amount) {
            throw new BusinessException("Denominations don't match withdrawal amount");
        }
    }

    private void validateATMoperation(Map<DollarDenomination, Integer> denominationsToBeExtracted,double amount){
        if(amount > getAtmMoney()){
            throw new BusinessException("Insufficient funds in the ATM, try other day");
        }
        Map<DollarDenomination, Integer> atmInventory = this.atm.getMoney();
        denominationsToBeExtracted.forEach((denomination,count) -> {
            if(this.atm.getMoney().get(denomination)<count){
                throw new BusinessException("Insufficient funds in ATMs bill "+denomination);
            }
        });
    };


    private void updateATMInventory(Map<DollarDenomination, Integer> extractedDenominations) {
        Map<DollarDenomination, Integer> atmInventory = this.atm.getMoney();

        extractedDenominations.forEach((denomination, count) -> {
            int currentCount = atmInventory.get(denomination);
            if(currentCount - count <0){
                throw new BusinessException("ATM BILL: "+denomination+" cant be below zero!");
            }
            atmInventory.put(denomination, currentCount - count);
        });

        logger.info("Updated ATM inventory after the extraction");
    }

    private int getAtmMoney(){
        validateSessionState();
        return this.atm.getMoney().entrySet().stream()
                .mapToInt(entry-> entry.getKey().getValue()*entry.getValue())
                .sum();
    }

}
