package org.solvd.atm.implementations.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.ATM;
import org.solvd.atm.domain.Account;
import org.solvd.atm.implementations.data.AccountDAO;
import org.solvd.atm.implementations.presentation.LoginScreen;
import org.solvd.atm.implementations.presentation.OptionMenuScreen;
import org.solvd.atm.interfaces.business.ILoginBusiness;
import org.solvd.atm.interfaces.data.IAccountDAO;
import org.solvd.atm.interfaces.presentation.ILoginAccountScreen;
import org.solvd.atm.interfaces.presentation.IOptionsMenuScreen;

public class LoginBusiness implements ILoginBusiness {

    private static final Logger logger = LogManager.getLogger();
    ILoginAccountScreen loginAccountScreen;
    IAccountDAO accountDAO = new AccountDAO();
    IOptionsMenuScreen optionsMenuScreen = new OptionMenuScreen();
    private ATM ATM;

    public LoginBusiness(){
    }

    @Override
    public void start(){
        loginAccountScreen.showLogin();
    }

    @Override
    public void loginAccount(String accountNumber,String PIN){

        Account acc = accountDAO.findAccountByNumberAndPin(accountNumber,PIN);
        if(acc.getNumber().equals(accountNumber)  && acc.getPIN().equals(PIN)){
            optionsMenuScreen.showOptionsMenu();
        } else {
            logger.info("Invalid account number or pin");
        }

    }

    @Override
    public void setATM(ATM ATM){
        this.ATM = ATM;
    }

    public void setLoginAccountScreen(ILoginAccountScreen loginAccountScreen){
        this.loginAccountScreen = loginAccountScreen;
    }

}
