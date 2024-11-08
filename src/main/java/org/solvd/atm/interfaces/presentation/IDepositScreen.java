package org.solvd.atm.interfaces.presentation;

import org.solvd.atm.dtos.DepositDTO;
import org.solvd.atm.interfaces.business.IDepositBusiness;

public interface IDepositScreen {
    void enterAmountToDeposit();
    void selectBillsInserted();
    void selectAccountCurrencyToDeposit();
    void showSuccess(DepositDTO depositDTO);
    void showErroMessage(String err);
    void setDepositBusiness(IDepositBusiness depositBusiness);
}
