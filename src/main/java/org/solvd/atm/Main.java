package org.solvd.atm;

import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.implementations.business.BalanceBusiness;
import org.solvd.atm.implementations.business.LoginBusiness;
import org.solvd.atm.implementations.business.OptionMenuBusiness;
import org.solvd.atm.implementations.business.TransactionBusiness;
import org.solvd.atm.implementations.businessobject.AccountService;
import org.solvd.atm.implementations.businessobject.CurrencyService;
import org.solvd.atm.implementations.data.AccountDAO;
import org.solvd.atm.implementations.data.CurrencyDAO;
import org.solvd.atm.implementations.presentation.BalanceScreen;
import org.solvd.atm.implementations.presentation.LoginScreen;
import org.solvd.atm.implementations.presentation.OptionMenuScreen;
import org.solvd.atm.implementations.presentation.TransactionScreen;
import org.solvd.atm.interfaces.business.IBalanceBusiness;
import org.solvd.atm.interfaces.businessObjects.IAccountService;
import org.solvd.atm.interfaces.data.IAccountDAO;
import org.solvd.atm.interfaces.presentation.IBalanceScreen;
import org.solvd.atm.utils.database.implementations.HikariCPDataSource;

public class Main {

    public static void main(String [] args){
        HikariCPDataSource.getInstance().setPoolSize(5);
        AccountDAO accountDAO = new AccountDAO();

        AccountService accountService = new AccountService();
        accountService.setAccountDAO(accountDAO);

        CurrencyService currencyService = new CurrencyService();


        LoginBusiness loginBusiness = new LoginBusiness();
        OptionMenuBusiness optionMenuBusiness = new OptionMenuBusiness();
        LoginScreen loginScreen = new LoginScreen();
        loginBusiness.setLoginAccountScreen(loginScreen);
        loginScreen.setLoginBusiness(loginBusiness);
        loginBusiness.setAccountService(accountService);
        loginBusiness.setOptionsMenuBusiness(optionMenuBusiness);
        OptionMenuScreen optionMenuScreen = new OptionMenuScreen();
        loginBusiness.setOptionsMenuScreen(optionMenuScreen);
        IBalanceBusiness balanceBusiness = new BalanceBusiness();
        IBalanceScreen balanceScreen = new BalanceScreen();
        balanceScreen.setBalanceBusiness(balanceBusiness);
        optionMenuBusiness.setBalanceScreen(balanceScreen);
        optionMenuBusiness.setBalanceBusiness(balanceBusiness);
        optionMenuScreen.setOptionsMenuBusiness(optionMenuBusiness);
        balanceBusiness.setCurrencyService(currencyService);
        currencyService.setAccountDAO(accountDAO);
        CurrencyDAO currencyDAO = new CurrencyDAO();
        currencyService.setCurrencyDAO(currencyDAO);
        optionMenuBusiness.setTransactionBusiness(new TransactionBusiness());
        optionMenuBusiness.setTransactionScreen(new TransactionScreen());
        loginBusiness.start();
    }
}
