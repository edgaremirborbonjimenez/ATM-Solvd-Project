package org.solvd.atm.interfaces.business;

import org.solvd.atm.domain.ATM;

public interface ILoginBusiness {
    void start();
    void loginAccount(String accountNumber,String PIN);
    void setATM(ATM ATM);
}
