package org.solvd.atm.interfaces.business;

import org.solvd.atm.domain.atm.ATM;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.dtos.TransactionDTO;
import org.solvd.atm.interfaces.businessObjects.ICurrencyService;
import org.solvd.atm.interfaces.businessObjects.ITransactionService;

import java.util.List;

public interface ITransactionBusiness {
    List<CurrencyDTO> getCurrenciesByAccount();
    TransactionDTO sendTransaction(String destinationAccount, Double amountToSend, String senderCurrency,String receiverCurrency);
    void setSessionAccountReference(AccountDTO accountReference);
    void setATM(ATM atm);
    void setCurrencyService(ICurrencyService currencyService);
    void setTransactionService(ITransactionService transactionService);
}
