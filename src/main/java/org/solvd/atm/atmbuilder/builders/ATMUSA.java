package org.solvd.atm.atmbuilder.builders;

import org.solvd.atm.atmbuilder.products.AtmMachine;
import org.solvd.atm.implementations.business.*;
import org.solvd.atm.implementations.businessobject.*;
import org.solvd.atm.implementations.data.*;
import org.solvd.atm.implementations.presentation.*;
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
        this.balanceScreen = new BalanceScreen();
        this.depositScreen = new DepositScreen();
        this.loginAccountScreen = new LoginScreen();
        this.optionsMenuScreen = new OptionMenuScreen();
        this.transactionScreen = new TransactionScreen();
        this.withdrawScreen = new WithdrawnScreen();
    }

    @Override
    public void setBusiness() {
        //set Atm Businesses
        this.loginBusiness = new LoginBusiness();
        this.optionsMenuBusiness = new OptionMenuBusiness();
        this.balanceBusiness = new BalanceBusiness();
        this.depositBusiness = new DepositBusiness();
        this.withdrawBusiness = new WithdrawBusiness();
        this.transactionBusiness = new TransactionBusiness();
    }

    @Override
    public void setServices() {
        //set Atm Services
        this.accountService = new AccountService();
        this.atmInfoService = new ATMInfoService();
        this.atmService = new ATMService();
        this.sessionInfoService = new SessionInfoService();
        this.currencyService = new CurrencyService();
        this.transactionService = new TransactionService();
        this.withdrawService = new WithdrawService();
        this.depositService = new DepositService();

    }

    @Override
    public void setDAOs() {
        //set Atm Daos
        this.accountDAO = new AccountDAO();
        this.currencyDAO = new CurrencyDAO();
        this.transactionDAO = new TransactionDAO();
        this.withdrawDAO = new WithdrawDAO();
        this.depositDAO = new DepositDAO();
        this.atmdao = new ATMDAO();
        this.atmInfoDAO = new ATMInfoDAO();
        this.sessionInfoDAO = new SessionInfoDAO();
    }

    @Override
    public void setDependencies() {
        this.loginAccountScreen.setLoginBusiness(this.loginBusiness);
        this.balanceScreen.setBalanceBusiness(this.balanceBusiness);
        this.depositScreen.setDepositBusiness(this.depositBusiness);
        this.optionsMenuScreen.setOptionsMenuBusiness(this.optionsMenuBusiness);
        this.transactionScreen.setTransactionBusiness(this.transactionBusiness);
        this.withdrawScreen.setWithdrawBusiness(this.withdrawBusiness);



        this.loginBusiness.setLoginAccountScreen(this.loginAccountScreen);
        this.loginBusiness.setAccountService(this.accountService);
        this.loginBusiness.setOptionsMenuScreen(this.optionsMenuScreen);
        this.loginBusiness.setOptionsMenuBusiness(this.optionsMenuBusiness);
        this.loginBusiness.setAtmService(this.atmService);
        this.loginBusiness.setAtmInfoService(this.atmInfoService);
        this.loginBusiness.setSessionInfoService(this.sessionInfoService);

        this.balanceBusiness.setCurrencyService(this.currencyService);
        this.depositBusiness.setDepositScreen(this.depositScreen);
        this.depositBusiness.setDepositService(this.depositService);
        this.depositBusiness.setOptionsMenuBusiness(this.optionsMenuBusiness);
        this.depositBusiness.setAtmInfoService(this.atmInfoService);
        this.depositBusiness.setCurrencyService(this.currencyService);
        this.depositBusiness.setSessionInfoService(this.sessionInfoService);

        this.optionsMenuBusiness.setBalanceScreen(this.balanceScreen);
        this.optionsMenuBusiness.setBalanceBusiness(this.balanceBusiness);
        this.optionsMenuBusiness.setTransactionBusiness(this.transactionBusiness);
        this.optionsMenuBusiness.setTransactionScreen(this.transactionScreen);
        this.optionsMenuBusiness.setDepositScreen(this.depositScreen);
        this.optionsMenuBusiness.setDepositBusiness(this.depositBusiness);
        this.optionsMenuBusiness.setWithdrawBusiness(this.withdrawBusiness);
        this.optionsMenuBusiness.setWithdrawScreen(this.withdrawScreen);
        this.optionsMenuBusiness.setAtmInfoService(this.atmInfoService);

        this.transactionBusiness.setTransactionService(this.transactionService);
        this.transactionBusiness.setCurrencyService(this.currencyService);

        this.withdrawBusiness.setWithdrawScreen(this.withdrawScreen);
        this.withdrawBusiness.setOptionsMenuBusiness(this.optionsMenuBusiness);
        this.withdrawBusiness.setWithdrawService(this.withdrawService);
        this.withdrawBusiness.setCurrencyService(this.currencyService);
        this.withdrawBusiness.setAtmInfoService(this.atmInfoService);
        this.withdrawBusiness.setSessionInfoService(this.sessionInfoService);

        this.accountService.setAccountDAO(this.accountDAO);

        this.atmInfoService.setATMInfoDAO(this.atmInfoDAO);

        this.atmService.setATMDAO(this.atmdao);

        this.currencyService.setCurrencyDAO(this.currencyDAO);
        this.currencyService.setAccountDAO(this.accountDAO);

        this.depositService.setCurrencyService(this.currencyService);
        this.depositService.setDepositDAO(this.depositDAO);

        this.sessionInfoService.setSessionInfoDAO(this.sessionInfoDAO);

        this.transactionService.setAccountService(this.accountService);
        this.transactionService.setCurrencyService(this.currencyService);
        this.transactionService.setCurrencyService(this.currencyService);
        transactionService.setTransactionDAO(transactionDAO);

        this.withdrawService.setWithdrawDAO(this.withdrawDAO);
        this.withdrawService.setCurrencyService(this.currencyService);

        atmMachine.setBalanceScreen(balanceScreen);
        atmMachine.setDepositScreen(depositScreen);
        atmMachine.setLoginAccountScreen(loginAccountScreen);
        atmMachine.setOptionsMenuScreen(optionsMenuScreen);
        atmMachine.setTransactionScreen(transactionScreen);
        atmMachine.setWithdrawScreen(withdrawScreen);

        atmMachine.setLoginBusiness(loginBusiness);
        atmMachine.setOptionsMenuBusiness(optionsMenuBusiness);
        atmMachine.setBalanceBusiness(balanceBusiness);
        atmMachine.setDepositBusiness(depositBusiness);
        atmMachine.setWithdrawBusiness(withdrawBusiness);
        atmMachine.setTransactionBusiness(transactionBusiness);

        atmMachine.setAccountService(accountService);
        atmMachine.setAtmInfoService(atmInfoService);
        atmMachine.setAtmService(atmService);
        atmMachine.setSessionInfoService(sessionInfoService);
        atmMachine.setCurrencyService(currencyService);
        atmMachine.setTransactionService(transactionService);
        atmMachine.setWithdrawService(withdrawService);
        atmMachine.setDepositService(depositService);

        atmMachine.setAccountDAO(accountDAO);
        atmMachine.setCurrencyDAO(currencyDAO);
        atmMachine.setTransactionDAO(transactionDAO);
        atmMachine.setWithdrawDAO(withdrawDAO);
        atmMachine.setDepositDAO(depositDAO);
        atmMachine.setAtmdao(atmdao);
        atmMachine.setAtmInfoDAO(atmInfoDAO);
        atmMachine.setSessionInfoDAO(sessionInfoDAO);
    }
}
