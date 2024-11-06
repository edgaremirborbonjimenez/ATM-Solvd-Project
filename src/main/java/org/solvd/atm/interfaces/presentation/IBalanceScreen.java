package org.solvd.atm.interfaces.presentation;

import org.solvd.atm.implementations.business.BalanceBusiness;
import org.solvd.atm.interfaces.business.IBalanceBusiness;

public interface IBalanceScreen {
    void showAllCurrenciesBalance();
    void setBalanceBusiness(IBalanceBusiness balanceBusiness);
}
