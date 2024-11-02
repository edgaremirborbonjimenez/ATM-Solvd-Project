package org.solvd.atm.interfaces.presentation;

import org.solvd.atm.dtos.DepositDTO;

public interface IDepositScreen {
    void enterAmountToDeposit();
    void selectBillsInserted();
    void selectAccountCurrencyToDeposit();
    void showSuccess(DepositDTO depositDTO);
    void showErroMessage(String err);
}
