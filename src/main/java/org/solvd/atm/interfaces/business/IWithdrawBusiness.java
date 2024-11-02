package org.solvd.atm.interfaces.business;

import org.solvd.atm.domain.ATM;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.CurrencyDTO;

import java.util.List;
import java.util.Map;

public interface IWithdrawBusiness {
    List<CurrencyDTO> getCurrenciesByAccount();
    void getDenominations(Double amountToWithdraw);
    void doWithdraw(Double withdrawAmount, Map<Integer,Integer> denomination);
    void setSessionAccountReference(AccountDTO accountReference);
    void setATM(ATM ATM);
}
