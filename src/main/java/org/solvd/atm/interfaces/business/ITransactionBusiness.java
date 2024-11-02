package org.solvd.atm.interfaces.business;

import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.CurrencyDTO;

import java.util.List;

public interface ITransactionBusiness {
    List<CurrencyDTO> getCurrenciesByAccount();
    void sendTransaction(String destinationAccount,Double amount,String currency);
    void setSessionAccountReference(AccountDTO accountReference);
}
