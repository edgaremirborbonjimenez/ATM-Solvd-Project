package org.solvd.atm.interfaces.business;

import org.solvd.atm.domain.ATM;
import org.solvd.atm.interfaces.businessObjects.IAccountService;
import org.solvd.atm.interfaces.presentation.ILoginAccountScreen;
import org.solvd.atm.interfaces.presentation.IOptionsMenuScreen;

public interface ILoginBusiness {
    void start();
    void loginAccount(String accountNumber,String PIN);
    void setATM(ATM ATM);
    void setLoginAccountScreen(ILoginAccountScreen loginAccountScreen);
    void setAccountService(IAccountService accountService);
    void setOptionsMenuScreen(IOptionsMenuScreen optionsMenuScreen);
    void setOptionsMenuBusiness(IOptionsMenuBusiness optionsMenuBusiness);
}
