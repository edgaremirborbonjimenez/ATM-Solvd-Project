package org.solvd.atm.interfaces.business;

import org.solvd.atm.domain.ATM;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.dtos.WithdrawDTO;
import org.solvd.atm.utils.DollarDenomination;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public interface IWithdrawBusiness {
    List<CurrencyDTO> getCurrenciesByAccount();
    List<EnumMap<DollarDenomination, Integer>> getDenominations(int amountToWithdraw);
    WithdrawDTO doWithdraw(int withdrawAmount, Map<DollarDenomination,Integer> denomination, String currency);
    void setSessionAccountReference(AccountDTO accountReference);
    void setATM(ATM ATM);
}
