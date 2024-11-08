package org.solvd.atm.implementations.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.atm.ATM;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.implementations.data.AccountDAO;
import org.solvd.atm.interfaces.business.ILoginBusiness;
import org.solvd.atm.interfaces.business.IOptionsMenuBusiness;
import org.solvd.atm.interfaces.businessObjects.IAccountService;
import org.solvd.atm.interfaces.data.IAccountDAO;
import org.solvd.atm.interfaces.presentation.ILoginAccountScreen;
import org.solvd.atm.interfaces.presentation.IOptionsMenuScreen;

public class LoginBusiness implements ILoginBusiness {

    private static final Logger logger = LogManager.getLogger();
    ILoginAccountScreen loginAccountScreen;
    IAccountDAO accountDAO = new AccountDAO();
    IOptionsMenuScreen optionsMenuScreen;
    IAccountService accountService;
    private ATM ATM;
    private IOptionsMenuBusiness optionsMenuBusiness;

    public LoginBusiness(){
    }

    @Override
    public void start(){
        loginAccountScreen.showLogin();
    }

    @Override
    public void loginAccount(String accountNumber,String PIN){
        try{
            AccountDTO acc = accountService.validateAccount(accountNumber,PIN);
            optionsMenuBusiness.setATM(ATM);
            optionsMenuBusiness.setSessionAccountReference(acc);
            optionsMenuScreen.showOptionsMenu();
        } catch (Exception e){
            logger.error(e.getMessage());
            loginAccountScreen.errorMessage("Invalid credentials.");
        }
    }

    @Override
    public void setATM(ATM ATM){
        this.ATM = ATM;
    }

    public void setLoginAccountScreen(ILoginAccountScreen loginAccountScreen){
        this.loginAccountScreen = loginAccountScreen;
    }

    public void setAccountService(IAccountService accountService){
        this.accountService = accountService;
    }


    public void setOptionsMenuScreen(IOptionsMenuScreen optionsMenuScreen){
        this.optionsMenuScreen = optionsMenuScreen;
    }

    public void setOptionsMenuBusiness(IOptionsMenuBusiness optionsMenuBusiness){
        this.optionsMenuBusiness = optionsMenuBusiness;
    }


}
