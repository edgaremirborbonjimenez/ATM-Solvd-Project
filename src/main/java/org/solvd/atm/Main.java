package org.solvd.atm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.implementations.business.LoginBusiness;
import org.solvd.atm.implementations.businessobject.AccountService;
import org.solvd.atm.implementations.data.AccountDAO;
import org.solvd.atm.implementations.presentation.LoginScreen;
import org.solvd.atm.interfaces.business.ILoginBusiness;
import org.solvd.atm.utils.database.implementations.HikariCPDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

        LoginBusiness loginBusiness = new LoginBusiness();
        LoginScreen loginScreen = new LoginScreen();
        loginBusiness.setLoginAccountScreen(loginScreen);
        loginScreen.setLoginBusiness(loginBusiness);
        AccountService accountService = new AccountService();
        loginBusiness.setAccountService(accountService);
        accountService.setAccountDAO(new AccountDAO());
        loginBusiness.start();
        /*HikariCPDataSource.getInstance().setPoolSize(5);
        HikariCPDataSource  h = HikariCPDataSource.getInstance();

        try(Connection connection = h.getDataSource().getConnection()){
            //Transaction with rollbacks en all validations
        }catch (SQLException e){
            logger.error(e.getMessage());
        }*/
    }
}