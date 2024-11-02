package org.solvd.atm.interfaces.presentation;

import org.solvd.atm.dtos.WithdrawDTO;

import java.util.List;
import java.util.Map;

public interface IWithdrawScreen {
    void selectAccountCurrency();
    void enterAmount();
    void showDenomination(List<Map<Integer,Integer>> denominations);
    void showSuccess(WithdrawDTO withdraw);
    void showErrorMessage(String err);
}
