package org.solvd.atm.implementations.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.atm.ATM;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.SessionDTO;
import org.solvd.atm.implementations.data.AccountDAO;
import org.solvd.atm.interfaces.business.ILoginBusiness;
import org.solvd.atm.interfaces.business.IOptionsMenuBusiness;
import org.solvd.atm.interfaces.businessObjects.IATMInfoService;
import org.solvd.atm.interfaces.businessObjects.IATMService;
import org.solvd.atm.interfaces.businessObjects.IAccountService;
import org.solvd.atm.interfaces.businessObjects.ISessionInfoService;
import org.solvd.atm.interfaces.data.IAccountDAO;
import org.solvd.atm.interfaces.presentation.ILoginAccountScreen;
import org.solvd.atm.interfaces.presentation.IOptionsMenuScreen;
import org.solvd.atm.sessionmanager.SessionManager;

public class LoginBusiness implements ILoginBusiness {

    private static final Logger logger = LogManager.getLogger();
    ILoginAccountScreen loginAccountScreen;
    IOptionsMenuScreen optionsMenuScreen;
    IAccountService accountService;
    ISessionInfoService sessionInfoService;
    IATMService atmService;
    IATMInfoService atmInfoService;
    AccountDTO accountDTO;
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
             accountDTO = accountService.validateAccount(accountNumber,PIN);
            if(accountDTO == null){
                loginAccountScreen.errorMessage("Invalid credentials.");
                loginAccountScreen.showLogin();
                return;
            }

            if(SessionManager.getInstance().isActive(this.accountDTO.getNumber())){
                loginAccountScreen.errorMessage("Account is already in use, pleas try another or try later");
                loginAccountScreen.showLogin();
                return;
            }

            this.init();

            optionsMenuBusiness.setATM(ATM);
            optionsMenuBusiness.setSessionAccountReference(accountDTO);
            optionsMenuScreen.showOptionsMenu();
        } catch (Exception e){
            logger.error(e.getMessage());
            loginAccountScreen.errorMessage("Invalid credentials.");
            loginAccountScreen.showLogin();
        }
    }

    private void init(){
        this.ATM = atmService.findOneATMSerie();
        this.ATM = atmInfoService.createNewATM(this.ATM.getSerieNumber());
        atmInfoService.updateATMBillBySerie(this.ATM.getSerieNumber(),this.ATM);
        atmInfoService.updateATMStadisticsBySerie(this.ATM.getSerieNumber());
        SessionManager.getInstance().login(this.accountDTO.getNumber(),this.ATM.getSerieNumber());
        SessionDTO sessionCreated = sessionInfoService.createNewSession(this.ATM.getSerieNumber(),accountDTO.getNumber());
        SessionManager.assignIdToAccount(this.accountDTO.getNumber(),sessionCreated.getId());

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


    public void setSessionInfoService(ISessionInfoService sessionInfoService) {
        this.sessionInfoService = sessionInfoService;
    }

    public void setAtmInfoService(IATMInfoService atmInfoService) {
        this.atmInfoService = atmInfoService;
    }

    public void setAtmService(IATMService atmService) {
        this.atmService = atmService;
    }
}
