package org.solvd.atm;

import org.solvd.atm.implementations.business.BalanceBusiness;
import org.solvd.atm.implementations.business.LoginBusiness;
import org.solvd.atm.implementations.business.OptionMenuBusiness;
import org.solvd.atm.implementations.businessobject.AccountService;
import org.solvd.atm.implementations.data.AccountDAO;
import org.solvd.atm.implementations.presentation.LoginScreen;
import org.solvd.atm.implementations.presentation.OptionMenuScreen;
import org.solvd.atm.interfaces.businessObjects.IAccountService;
import org.solvd.atm.interfaces.data.IAccountDAO;
import org.solvd.atm.utils.database.implementations.HikariCPDataSource;

public class Main {

    public static void main(String [] args){
        HikariCPDataSource.getInstance().setPoolSize(5);
        LoginBusiness loginBusiness = new LoginBusiness();
        LoginScreen loginScreen = new LoginScreen();
        loginBusiness.setLoginAccountScreen(loginScreen);
        loginScreen.setLoginBusiness(loginBusiness);
        AccountService accountService = new AccountService();
        loginBusiness.setAccountService(accountService);
        AccountDAO accountDAO = new AccountDAO();
        accountService.setAccountDAO(accountDAO);
        OptionMenuBusiness optionMenuBusiness = new OptionMenuBusiness();
        loginBusiness.setOptionsMenuBusiness(optionMenuBusiness);
        OptionMenuScreen optionMenuScreen = new OptionMenuScreen();
        loginBusiness.setOptionsMenuScreen(optionMenuScreen);
        BalanceBusiness balanceBusiness = new BalanceBusiness();
        optionMenuBusiness.setBalanceBusiness(balanceBusiness);
        loginBusiness.start();
    }
}
