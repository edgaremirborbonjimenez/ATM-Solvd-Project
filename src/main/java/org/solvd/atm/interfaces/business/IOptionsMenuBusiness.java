package org.solvd.atm.interfaces.business;

import org.solvd.atm.domain.ATM;
import org.solvd.atm.dtos.AccountDTO;

public interface IOptionsMenuBusiness {
    void showBalance();
    void setSessionAccountReference(AccountDTO account);
    void setATM(ATM ATM);
}
