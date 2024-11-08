package org.solvd.atm.interfaces.business;

import org.solvd.atm.domain.atm.ATM;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.interfaces.presentation.IBalanceScreen;

public interface IOptionsMenuBusiness {
    void showBalance();
    void setSessionAccountReference(AccountDTO account);
    void setATM(ATM ATM);
    void showTransaction();
    void showWithraw();
    void showDeposit();
    void setBalanceBusiness(IBalanceBusiness balanceBusiness);
    void setBalanceScreen(IBalanceScreen balanceScreen);
}
