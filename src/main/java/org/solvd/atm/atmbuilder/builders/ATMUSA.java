package org.solvd.atm.atmbuilder.builders;

import org.solvd.atm.atmbuilder.products.AtmMachine;
import org.solvd.atm.implementations.business.BalanceBusiness;
import org.solvd.atm.implementations.business.DepositBusiness;
import org.solvd.atm.implementations.business.LoginBusiness;
import org.solvd.atm.implementations.business.OptionMenuBusiness;
import org.solvd.atm.implementations.businessobject.AccountService;
import org.solvd.atm.implementations.businessobject.CurrencyService;
import org.solvd.atm.implementations.businessobject.DepositService;
import org.solvd.atm.implementations.data.AccountDAO;
import org.solvd.atm.implementations.data.CurrencyDAO;
import org.solvd.atm.implementations.data.DepositDAO;
import org.solvd.atm.implementations.presentation.LoginScreen;
import org.solvd.atm.implementations.presentation.OptionMenuScreen;
import org.solvd.atm.interfaces.businessObjects.ICurrencyService;
import org.solvd.atm.interfaces.businessObjects.ITransactionService;

public class ATMUSA extends AtmBuilder{
    static ATMUSA atmusa;

    private ATMUSA(){}

    public static ATMUSA getInstance(){
        if(atmusa == null){
            atmusa = new ATMUSA();
        }
        return atmusa;
    }


    @Override
    public void reset() {
        this.atmMachine = new AtmMachine();
    }

    @Override
    public void setPresentation() {
        //set Atm Presentations
        this.loginAccountScreen = new LoginScreen();
        this.atmMachine.setLoginAccountScreen(this.loginAccountScreen);

        this.optionsMenuScreen = new OptionMenuScreen();
//        this.optionsMenuScreen.setOptionsMenuBusiness(this.optionsMenuBusiness);
//
//        this.balanceScreen = new BalanceScreen();
//        this.balanceScreen.setBalanceBusiness(this.balanceBusiness);
//
//        this.transactionScreen = new TransactionScreen();
//        this.transactionScreen.setTransactionBusiness(this.transactionBusiness);
//
//        this.withdrawScreen = new WithDrawScreen();
//        this.withdrawScreen.setWithdrawBusiness(this.withdrawBusiness);
//
//        this.depositScreen = new DepositScreen();
//        this.depositScreen.setDepositBusiness(this.depositBusiness);
    }

    @Override
    public void setBusiness() {
        //set Atm Businesses
        this.loginBusiness = new LoginBusiness();
        this.atmMachine.setLoginBusiness(this.loginBusiness);

        this.optionsMenuBusiness = new OptionMenuBusiness();
//        this.optionsMenuBusiness.setBalanceScreen(this.balanceScreen);
//        this.optionsMenuBusiness.setBalanceBusiness(this.balanceBusiness);
//        this.optionsMenuBusiness.setTransactionScreen(this.transactionScreen);
//        this.optionsMenuBusiness.setTransactionBusiness(this.transactionBusiness);
//        this.optionsMenuBusiness.setWithdrawScreen(this.withdrawScreen);
//        this.optionsMenuBusiness.setWithdrawBusiness(this.withdrawBusiness);
//        this.optionsMenuBusiness.setDepositScreen(this.depositScreen);
//        this.optionsMenuBusiness.setDepositBusiness(this.depositBusiness);

        this.balanceBusiness = new BalanceBusiness();
//        this.balanceBusiness.setCurrencyService(this.currencyService);


//        this.transactionBusiness = new TransactionBusiness();
//        this.transactionBusiness.setCurrencyService(this.currencyService);
//        this.transactionBusiness.setAccountService(this.accountService);
//        this.transactionBusiness.setTransactionService(this.transactionService);

//        this.withdrawBusiness = new WithdrawBusiness();
//        this.withdrawBusiness.setWithdrawService(this.withdrawService);
//        this.withdrawBusiness.setAccountService(this.accountService);
//        this.withdrawBusiness.setCurrencyService(this.currencyService);
//        this.withdrawBusiness.setWithdrawService(this.withdrawService);
//        this.withdrawBusiness.setOptionMenuBusiness(this.optionsMenuBusiness);

        this.depositBusiness = new DepositBusiness();
//        this.depositBusiness.setDepositService(this.depositService);
//        this.depositBusiness.setCurrencyService(this.currencyService);
//        this.depositBusiness.setOptionMenuBusiness(this.optionsMenuBusiness);

    }

    @Override
    public void setServices() {
        //set Atm Services
        this.accountService = new AccountService();
        this.currencyService = new CurrencyService();
//        this.transactionService = new TransactionService();
//        this.withdrawService = new WithdrawService();
        this.depositService = new DepositService();
    }

    @Override
    public void setDAOs() {
        //set Atm Daos
        this.accountDAO = new AccountDAO();
        this.currencyDAO = new CurrencyDAO();
//        this.transactionDAO = new TransactionDAO();
//        this.withdrawDAO = new WithdrawDAO();
        this.depositDAO = new DepositDAO();
    }

    @Override
    public void setDependencies() {
        this.loginAccountScreen.setLoginBusiness(this.loginBusiness);

        this.loginBusiness.setLoginAccountScreen(this.loginAccountScreen);
        this.loginBusiness.setAccountService(this.accountService);
        this.loginBusiness.setOptionsMenuScreen(this.optionsMenuScreen);
        this.loginBusiness.setOptionsMenuBusiness(this.optionsMenuBusiness);

        this.accountService.setAccountDAO(this.accountDAO);
        this.optionsMenuScreen.setOptionsMenuBusiness(this.optionsMenuBusiness);

    }
}
