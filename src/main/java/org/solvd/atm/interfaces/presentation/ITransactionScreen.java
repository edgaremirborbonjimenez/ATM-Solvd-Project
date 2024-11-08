package org.solvd.atm.interfaces.presentation;

import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.TransactionDTO;
import org.solvd.atm.interfaces.business.ITransactionBusiness;

public interface ITransactionScreen {
    void selectTransactionCurrency();
    void enterAccountNumber();
    void enterAmount();
    void showSuccess(TransactionDTO transaction);
    void showErrorMessage(String err);
    void setTransactionBusiness(ITransactionBusiness transactionBusiness);
}
