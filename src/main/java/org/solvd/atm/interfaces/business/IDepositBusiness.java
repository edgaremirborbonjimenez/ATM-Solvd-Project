package org.solvd.atm.interfaces.business;

import org.solvd.atm.domain.ATM;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.dtos.DepositDTO;
import org.solvd.atm.utils.DollarDenomination;

import java.util.List;
import java.util.Map;

public interface IDepositBusiness {
    List<CurrencyDTO> getAccountCurrencies();
    DepositDTO deposit(Map<DollarDenomination,Integer> denomination, int amount, String currency);
    void setSessionAccountReference(AccountDTO accountReference);
    void setATM(ATM ATM);
}
