package org.solvd.atm.interfaces.business;

import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.dtos.TransactionDTO;

import java.util.List;

public interface ITransactionBusiness {
    List<CurrencyDTO> getCurrenciesByAccount();
    TransactionDTO sendTransaction(String destinationAccount, Double amountToSend, String senderCurrency,String receiverCurrency);
    void setSessionAccountReference(AccountDTO accountReference);
}
