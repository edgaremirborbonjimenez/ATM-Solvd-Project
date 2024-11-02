package org.solvd.atm.interfaces.business;

import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.dtos.DepositDTO;

import java.util.List;
import java.util.Map;

public interface IDepositBusiness {
    List<CurrencyDTO> getAccountCurrencies();
    DepositDTO deposit(Map<Integer,Integer> denomination, Double amount, String currency);
    void setSessionAccountReference(AccountDTO accountReference);
}
