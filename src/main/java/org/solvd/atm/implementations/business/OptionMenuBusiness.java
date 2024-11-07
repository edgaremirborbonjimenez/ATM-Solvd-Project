package org.solvd.atm.implementations.business;

import org.solvd.atm.domain.ATM;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.interfaces.business.*;
import org.solvd.atm.interfaces.presentation.IBalanceScreen;
import org.solvd.atm.interfaces.presentation.IDepositScreen;
import org.solvd.atm.interfaces.presentation.ITransactionScreen;
import org.solvd.atm.interfaces.presentation.IWithdrawScreen;

public class OptionMenuBusiness implements IOptionsMenuBusiness {


    private ATM ATM;
    private AccountDTO account;
    private IBalanceScreen balanceScreen;
    private IBalanceBusiness balanceBusiness;
    private ITransactionBusiness transactionBusiness;
    private ITransactionScreen transactionScreen;
    private IWithdrawBusiness withdrawBusiness;
    private IWithdrawScreen withdrawScreen;
    private IDepositBusiness depositBusiness;
    private IDepositScreen depositScreen;

    @Override
    public void showBalance() {
        //balanceBusiness.setSessionAccountReference(this.account);
        balanceScreen.showAllCurrenciesBalance();
    }

    @Override
    public void showTransaction() {
        transactionBusiness.setSessionAccountReference(this.account);
        transactionScreen.selectTransactionCurrency();
    }

    @Override
    public void showWithraw() {
        withdrawBusiness.setATM(this.ATM);
        withdrawBusiness.setSessionAccountReference(this.account);
        withdrawScreen.selectAccountCurrency();
    }

    @Override
    public void showDeposit() {
        depositBusiness.setATM(this.ATM);
        depositBusiness.setSessionAccountReference(account);
        depositScreen.enterAmountToDeposit();

    }

    @Override
    public void setSessionAccountReference(AccountDTO account) {
        this.account = account;
        balanceBusiness.setSessionAccountReference(account);
    }

    @Override
    public void setATM(ATM ATM) {
        this.ATM = ATM;
    }

    public void setBalanceBusiness(BalanceBusiness balanceBusiness) {
        this.balanceBusiness = balanceBusiness;
    }
}
