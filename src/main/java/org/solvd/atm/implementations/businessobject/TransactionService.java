package org.solvd.atm.implementations.businessobject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.atm.Transfer;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.dtos.TransactionDTO;
import org.solvd.atm.interfaces.businessObjects.IAccountService;
import org.solvd.atm.interfaces.businessObjects.ICurrencyService;
import org.solvd.atm.interfaces.businessObjects.ITransactionService;
import org.solvd.atm.interfaces.data.ITransactionDAO;
import org.solvd.atm.utils.exceptions.BusinessException;

import java.util.List;

public class TransactionService implements ITransactionService {
    private static final Logger logger = LogManager.getLogger(TransactionService.class);

    private IAccountService accountService;
    private ICurrencyService currencyService;
    private ITransactionDAO transactionDAO;

    public void setTransactionDAO(ITransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    public void setCurrencyService(ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public TransactionDTO sendTransaction(String originAccount, String destinationAccount, Double amount, String senderCurrency, String receiverCurrency) {
        validateInputs(originAccount, destinationAccount, amount, senderCurrency,receiverCurrency);

        try {
            Transfer transfer = transactionDAO.sendTransaction(originAccount, destinationAccount, senderCurrency,receiverCurrency, amount);
            return createDTO(transfer);
        } catch (Exception e) {
            logger.error("Error generating a transaction between {} and {} ",originAccount,destinationAccount);
            throw new BusinessException("Failed to process transaction "+ e);
        }
    }


    private void validateInputs(String originAccount, String destinationAccount,
                                          Double amount, String originCurrency, String destinationCurrency) {
        if (originAccount == null || originAccount.trim().isEmpty()) {
            throw new BusinessException("Origin account cannot be empty");
        }
        if (destinationAccount == null || destinationAccount.trim().isEmpty()) {
            throw new BusinessException("Destination account cannot be empty");
        }
        if (amount == null || amount <= 0) {
            throw new BusinessException("Amount must be greater than zero");
        }
        if (originCurrency == null || originCurrency.trim().isEmpty()) {
            throw new BusinessException("Currency cannot be empty");
        }
        if (destinationCurrency == null || destinationCurrency.trim().isEmpty()) {
            throw new BusinessException("Currency cannot be empty");
        }
        if (originAccount.equals(destinationAccount)) {
            throw new BusinessException("Cannot transfer to the same account");
        }
        if (!accountService.validateAccount(originAccount)) {
            throw new BusinessException("Origin account not found: " + originAccount);
        }
        if (!accountService.validateAccount(destinationAccount)) {
            throw new BusinessException("Destination account not found: " + destinationAccount);
        }
        CurrencyDTO sender_currencyDTO = currencyService.findCurrencyByName(originCurrency);
        CurrencyDTO receiver_currencyDTO = currencyService.findCurrencyByName(destinationCurrency);
        if (sender_currencyDTO == null || receiver_currencyDTO == null ) {
            throw new BusinessException("Invalid currency detected ");
        }
    }

    private TransactionDTO createDTO(Transfer transfer) {
        if (transfer == null) {
            return null;
        }

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setReferenceNumber(transfer.getReferenceNumber());

        AccountDTO originDTO = new AccountDTO();
        originDTO.setNumber(transfer.getOrigin().getNumber());
        List<CurrencyDTO> originCurrencies = currencyService
                .getAccountCurrenciesBalanceByAccountNum(transfer.getOrigin().getNumber());
        originDTO.setCurrencies(originCurrencies);

        AccountDTO destinationDTO = new AccountDTO();
        destinationDTO.setNumber(transfer.getDestination().getNumber());
        List<CurrencyDTO> destinationCurrencies = currencyService
                .getAccountCurrenciesBalanceByAccountNum(transfer.getDestination().getNumber());
        destinationDTO.setCurrencies(destinationCurrencies);


        transactionDTO.setDestinationAccount(destinationDTO);
        transactionDTO.setOriginAccount(originDTO);

        return transactionDTO;
    }


}
