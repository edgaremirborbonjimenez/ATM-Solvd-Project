package org.solvd.atm.interfaces.presentation;

import org.solvd.atm.interfaces.business.ILoginBusiness;

public interface ILoginAccountScreen {
    void showLogin();
    void errorMessage(String message);
    void setLoginBusiness (ILoginBusiness loginBusiness);
}
