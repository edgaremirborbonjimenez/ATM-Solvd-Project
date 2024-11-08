package org.solvd.atm.implementations.business;

import org.solvd.atm.atmbuilder.client.AtmClient;
import org.solvd.atm.domain.ATM;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.TransactionDTO;
import org.solvd.atm.implementations.presentation.TransactionScreen;
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
        balanceBusiness.setSessionAccountReference(this.account);
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
    public void startNewATM() {
        AtmClient.getInstance().startNewATM();
    }

    @Override
    public void closeSession() {
        AtmClient.getInstance().stopATM();
    }

    @Override
    public void setSessionAccountReference(AccountDTO account) {
        this.account = account;
    }

    @Override
    public void setATM(ATM ATM) {
        this.ATM = ATM;
    }

    public void setBalanceBusiness(IBalanceBusiness balanceBusiness) {
        this.balanceBusiness = balanceBusiness;
    }

    public void setBalanceScreen(IBalanceScreen balanceScreen) {
        this.balanceScreen = balanceScreen;
    }

    public void setTransactionBusiness(TransactionBusiness transactionBusiness){
        this.transactionBusiness = transactionBusiness;
    }
    public void setTransactionScreen(TransactionScreen transactionScreen){
        this.transactionScreen = transactionScreen;
    }
}
