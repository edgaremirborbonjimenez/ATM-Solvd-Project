package org.solvd.atm.interfaces.business;

import org.solvd.atm.domain.atm.ATM;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.interfaces.presentation.IBalanceScreen;
import org.solvd.atm.interfaces.presentation.IDepositScreen;
import org.solvd.atm.interfaces.presentation.ITransactionScreen;
import org.solvd.atm.interfaces.presentation.IWithdrawScreen;

public interface IOptionsMenuBusiness {
    void showBalance();
    void setSessionAccountReference(AccountDTO account);
    void setATM(ATM ATM);
    void showTransaction();
    void showWithraw();
    void showDeposit();
    void setBalanceBusiness(IBalanceBusiness balanceBusiness);
    void setBalanceScreen(IBalanceScreen balanceScreen);
    void setAccountReference(AccountDTO account);
    void setDepositScreen(IDepositScreen depositScreen);
    void setTransactionBusiness(ITransactionBusiness transactionBusiness);
    void setTransactionScreen(ITransactionScreen transactionScreen);
    void setWithdrawBusiness(IWithdrawBusiness withdrawBusiness);
    void setWithdrawScreen(IWithdrawScreen withdrawScreen);
}
