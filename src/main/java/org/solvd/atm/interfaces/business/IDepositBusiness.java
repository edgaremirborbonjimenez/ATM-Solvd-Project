package org.solvd.atm.interfaces.business;

import org.solvd.atm.domain.atm.ATM;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.dtos.DepositDTO;
import org.solvd.atm.interfaces.businessObjects.IATMInfoService;
import org.solvd.atm.interfaces.businessObjects.ICurrencyService;
import org.solvd.atm.interfaces.businessObjects.IDepositService;
import org.solvd.atm.interfaces.businessObjects.ISessionInfoService;
import org.solvd.atm.interfaces.presentation.IDepositScreen;
import org.solvd.atm.utils.DollarDenomination;

import java.util.List;
import java.util.Map;

public interface IDepositBusiness {
    List<CurrencyDTO> getAccountCurrencies();
    DepositDTO deposit(Map<DollarDenomination,Integer> denomination, int amount, String currency);
    void setSessionAccountReference(AccountDTO accountReference);
    void setATM(ATM ATM);
    void setDepositService(IDepositService depositService);
    void setCurrencyService(ICurrencyService currencyService);
    void setSessionInfoService(ISessionInfoService sessionInfoService);
    void setAccountSession(AccountDTO accountSession);
    void setAtm(ATM atm);
    void setDepositScreen(IDepositScreen depositScreen);
    void setOptionsMenuBusiness(IOptionsMenuBusiness optionsMenuBusiness);
    void setAtmInfoService(IATMInfoService atmInfoService);
}
