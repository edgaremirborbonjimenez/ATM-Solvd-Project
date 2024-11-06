package org.solvd.atm.interfaces.data;

import org.solvd.atm.domain.Transfer;

public interface ITransactionDAO {
    Transfer sendTransaction(String originAccount, String destinationAccount, String senderCurrency,String receiverCurrency, Double amount);
}
