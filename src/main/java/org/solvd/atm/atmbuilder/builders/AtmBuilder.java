package org.solvd.atm.atmbuilder.builders;

import org.solvd.atm.atmbuilder.products.AbstractAtmMachine;
import org.solvd.atm.interfaces.business.*;
import org.solvd.atm.interfaces.businessObjects.*;
import org.solvd.atm.interfaces.data.*;
import org.solvd.atm.interfaces.presentation.*;

public abstract class AtmBuilder {
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
    protected ICurrencyService currencyService;
    protected ITransactionService transactionService;
    protected IWithdrawService withdrawService;
    protected IDepositService depositService;

    //DAOs
    protected IAccountDAO accountDAO;
    protected ICurrencyDAO currencyDAO;
    protected ITransactionDAO transactionDAO;
    protected IWithdrawDAO withdrawDAO;
    protected IDepositDAO depositDAO;

    protected AbstractAtmMachine atmMachine;
    public abstract void reset();
    public abstract void setPresentation();
    public abstract void setBusiness();
    public abstract void setServices();
    public abstract void setDAOs();
    public abstract void setDependencies();
    public AbstractAtmMachine getResult(){
        return atmMachine;
    }
}
