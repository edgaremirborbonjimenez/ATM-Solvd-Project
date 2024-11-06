package org.solvd.atm.implementations.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.dtos.TransactionDTO;
import org.solvd.atm.interfaces.business.ITransactionBusiness;
import org.solvd.atm.interfaces.businessObjects.ICurrencyService;
import org.solvd.atm.interfaces.businessObjects.ITransactionService;
import org.solvd.atm.utils.exceptions.BusinessException;

import java.util.List;

public class TransactionBusiness implements ITransactionBusiness {
    private static final Logger logger = LogManager.getLogger(TransactionBusiness.class);

    private ITransactionService transactionService;
    private ICurrencyService currencyService;
    private AccountDTO accountSession;

    public void setCurrencyService(ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public void setTransactionService(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @Override
    public List<CurrencyDTO> getCurrenciesByAccount() {
        validateSessionAccount();
        return currencyService.getAccountCurrenciesBalanceByAccountNum(accountSession.getNumber());
    }

    @Override
    public TransactionDTO sendTransaction(String destinationAccount, Double amountToSend, String senderCurrency,String receiverCurrency) {
        validateSessionAccount();
        validateParameters(destinationAccount,amountToSend,senderCurrency,receiverCurrency);

        if (!currencyService.validateAccountCurrencyBalance(
                accountSession.getNumber(), amountToSend, senderCurrency)) {
            throw new BusinessException("Insufficient funds in sender account");
        }
        validateCurrencies(destinationAccount, senderCurrency, receiverCurrency);

        Double newAmount = convertAmount(amountToSend, senderCurrency, receiverCurrency);

        try {
            return transactionService.sendTransaction(
                    accountSession.getNumber(),
                    destinationAccount,
                    newAmount,
                    senderCurrency,
                    receiverCurrency
            );
        } catch (Exception e) {
            logger.error("Error processing transaction");
            throw new BusinessException("Error processing transaction: " + e.getMessage());
        }
    }

    @Override
    public void setSessionAccountReference(AccountDTO accountReference) {
        if (accountReference == null) {
            throw new BusinessException("Account reference cannot be null");
        }
        this.accountSession = accountReference;
    }

    private void validateSessionAccount() {
        if (accountSession == null) {
            throw new BusinessException("No account session reference set yet");
        }
    }

    private void validateParameters(String destinationAccount, Double amount,
                                String senderCurrency, String receiverCurrency) {

        if (destinationAccount == null || destinationAccount.trim().isEmpty()) {
            throw new BusinessException("Destination account cannot be empty");
        }
        if (amount == null || amount <= 0) {
            throw new BusinessException("Invalid amount");
        }
        if (senderCurrency == null || senderCurrency.trim().isEmpty()) {
            throw new BusinessException("Sender currency cannot be empty");
        }
        if (receiverCurrency == null || receiverCurrency.trim().isEmpty()) {
            throw new BusinessException("Receiver currency cannot be empty");
        }
        if (accountSession.getNumber().equals(destinationAccount)) {
            throw new BusinessException("Cannot transfer to same account");
        }
    }

    private void validateCurrencies(String destinationAccount,
                                    String senderCurrency, String receiverCurrency) {

        List<CurrencyDTO> senderCurrencies =
                currencyService.getAccountCurrencies(accountSession.getNumber());
        List<CurrencyDTO> receiverCurrencies =
                currencyService.getAccountCurrencies(destinationAccount);

        boolean senderHasCurrency = senderCurrencies.stream()
                .anyMatch(c -> c.getName().equals(senderCurrency));
        if (!senderHasCurrency) {
            throw new BusinessException("Sender account doesn't have " + senderCurrency + " currency");
        }
        boolean receiverHasCurrency = receiverCurrencies.stream()
                .anyMatch(c -> c.getName().equals(receiverCurrency));
        if (!receiverHasCurrency) {
            throw new BusinessException("Receiver account doesn't have " + receiverCurrency + " currency");
        }
    }

    private Double convertAmount(Double amount, String senderCurrency,
                                            String receiverCurrency) {
        if (senderCurrency.equals(receiverCurrency)) {
            return amount;
        }

        CurrencyDTO senderCurrency_ = currencyService.findCurrencyByName(senderCurrency);
        CurrencyDTO receiverCurrency_ = currencyService.findCurrencyByName(receiverCurrency);

        if (senderCurrency_ == null || receiverCurrency_ == null) {
            throw new BusinessException("Currency information can't be null");
        }

        Double amountInUSD = amount / senderCurrency_.getEquivalentToDollar();
        return amountInUSD  * receiverCurrency_.getEquivalentToDollar();
    }


}
