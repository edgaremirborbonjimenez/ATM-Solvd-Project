package org.solvd.atm.interfaces.businessObjects;

import org.solvd.atm.dtos.TransactionDTO;
import org.solvd.atm.interfaces.data.ITransactionDAO;

public interface ITransactionService {
    TransactionDTO sendTransaction(String originAccount,String destinationAccount,Double amount, String senderCurrency,String receiverCurrency,String atmSerial);
    void setTransactionDAO(ITransactionDAO transactionDAO);
    void setCurrencyService(ICurrencyService currencyService);
    void setAccountService(IAccountService accountService);
}
