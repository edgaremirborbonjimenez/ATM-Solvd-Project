package org.solvd.atm.interfaces.presentation;

import org.solvd.atm.dtos.TransactionDTO;

public interface ITransactionScreen {
    void selectTransactionCurrency();
    void enterAccountNumber();
    void enterAmount();
    void showSuccess(TransactionDTO transaction);
    void showErrorMessage(String err);
}
