package org.solvd.atm.interfaces.presentation;

import org.solvd.atm.dtos.WithdrawDTO;
import org.solvd.atm.interfaces.business.IWithdrawBusiness;
import org.solvd.atm.utils.DollarDenomination;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public interface IWithdrawScreen {
    void selectAccountCurrency();
    void enterAmount(String selectedCurrency);
    void showDenomination(List<EnumMap<DollarDenomination, Integer>> denominations, int amount, String currency);
    void showSuccess(WithdrawDTO withdraw);
    void showErrorMessage(String err);
    void setWithdrawBusiness(IWithdrawBusiness withdrawBusiness);
}
