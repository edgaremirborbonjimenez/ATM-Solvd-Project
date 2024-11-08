package org.solvd.atm.interfaces.business;

import org.solvd.atm.domain.atm.ATM;

public interface ILoginBusiness {
    void start();
    void loginAccount(String accountNumber,String PIN);
    void setATM(ATM ATM);
}
