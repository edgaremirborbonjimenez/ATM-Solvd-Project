package org.solvd.atm.atmbuilder.products;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.interfaces.business.*;
import org.solvd.atm.interfaces.businessObjects.*;
import org.solvd.atm.interfaces.data.*;
import org.solvd.atm.interfaces.presentation.*;

public abstract class AbstractAtmMachine implements Runnable{
    private static final Logger logger = LogManager.getLogger();

    //Presentations
    protected ILoginAccountScreen loginAccountScreen;
    protected IOptionsMenuScreen optionsMenuScreen;
    protected IBalanceScreen balanceScreen;
    protected ITransactionScreen transactionScreen;
    protected IWithdrawScreen withdrawScreen;
    protected IDepositScreen depositScreen;

    //Businesses
    protected ILoginBusiness loginBusiness;
    protected IOptionsMenuBusiness optionsMenuBusiness;
    protected IBalanceBusiness balanceBusiness;
    protected ITransactionBusiness transactionBusiness;
    protected IWithdrawBusiness withdrawBusiness;
    protected IDepositBusiness depositBusiness;

    //Services (Businesses Objects)
    protected IAccountService accountService;
    protected IATMInfoService atmInfoService;
    protected IATMService atmService;
    protected ISessionInfoService sessionInfoService;
    protected ICurrencyService currencyService;
    protected ITransactionService transactionService;
    protected IWithdrawService withdrawService;
    protected IDepositService depositService;

    //DAOs
    protected IAccountDAO accountDAO;
    protected IATMDAO atmdao;
    protected IATMInfoDAO atmInfoDAO;
    protected ICurrencyDAO currencyDAO;
    protected ITransactionDAO transactionDAO;
    protected IWithdrawDAO withdrawDAO;
    protected IDepositDAO depositDAO;
    protected ISessionInfoDAO sessionInfoDAO;

    public synchronized void pauseExecution(){
        try{
            System.out.println("Waiting new ATM");
            wait();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    public synchronized void resumeExecution() {
        notify();
        System.out.println("ATM resume");
    }

    public void setLoginAccountScreen(ILoginAccountScreen loginAccountScreen) {
        this.loginAccountScreen = loginAccountScreen;
    }

    public void setOptionsMenuScreen(IOptionsMenuScreen optionsMenuScreen) {
        this.optionsMenuScreen = optionsMenuScreen;
    }

    public void setTransactionScreen(ITransactionScreen transactionScreen) {
        this.transactionScreen = transactionScreen;
    }

    public void setWithdrawScreen(IWithdrawScreen withdrawScreen) {
        this.withdrawScreen = withdrawScreen;
    }

    public void setDepositScreen(IDepositScreen depositScreen) {
        this.depositScreen = depositScreen;
    }

    public void setBalanceScreen(IBalanceScreen balanceScreen) {
        this.balanceScreen = balanceScreen;
    }

    public void setLoginBusiness(ILoginBusiness loginBusiness) {
        this.loginBusiness = loginBusiness;
    }

    public void setOptionsMenuBusiness(IOptionsMenuBusiness optionsMenuBusiness) {
        this.optionsMenuBusiness = optionsMenuBusiness;
    }

    public void setBalanceBusiness(IBalanceBusiness balanceBusiness) {
        this.balanceBusiness = balanceBusiness;
    }

    public void setTransactionBusiness(ITransactionBusiness transactionBusiness) {
        this.transactionBusiness = transactionBusiness;
    }

    public void setWithdrawBusiness(IWithdrawBusiness withdrawBusiness) {
        this.withdrawBusiness = withdrawBusiness;
    }

    public void setDepositBusiness(IDepositBusiness depositBusiness) {
        this.depositBusiness = depositBusiness;
    }

    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    public void setTransactionService(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void setCurrencyService(ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public void setWithdrawService(IWithdrawService withdrawService) {
        this.withdrawService = withdrawService;
    }

    public void setDepositService(IDepositService depositService) {
        this.depositService = depositService;
    }

    public void setAccountDAO(IAccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public void setCurrencyDAO(ICurrencyDAO currencyDAO) {
        this.currencyDAO = currencyDAO;
    }

    public void setTransactionDAO(ITransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    public void setWithdrawDAO(IWithdrawDAO withdrawDAO) {
        this.withdrawDAO = withdrawDAO;
    }

    public void setDepositDAO(IDepositDAO depositDAO) {
        this.depositDAO = depositDAO;
    }

    public void setAtmInfoService(IATMInfoService atmInfoService) {
        this.atmInfoService = atmInfoService;
    }

    public void setAtmService(IATMService atmService) {
        this.atmService = atmService;
    }

    public void setSessionInfoService(ISessionInfoService sessionInfoService) {
        this.sessionInfoService = sessionInfoService;
    }

    public void setAtmdao(IATMDAO atmdao) {
        this.atmdao = atmdao;
    }

    public void setAtmInfoDAO(IATMInfoDAO atmInfoDAO) {
        this.atmInfoDAO = atmInfoDAO;
    }

    public void setSessionInfoDAO(ISessionInfoDAO sessionInfoDAO) {
        this.sessionInfoDAO = sessionInfoDAO;
    }
}
