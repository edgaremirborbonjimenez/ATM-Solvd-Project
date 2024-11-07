package org.solvd.atm.interfaces.businessObjects;

import org.solvd.atm.dtos.TransactionDTO;

public interface ITransactionService {
    TransactionDTO sendTransaction(String originAccount,String destinationAccount,Double amount, String senderCurrency,String receiverCurrency);
}
