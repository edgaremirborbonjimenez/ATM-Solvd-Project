package org.solvd.atm.interfaces.business;

import org.solvd.atm.domain.atm.ATM;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.dtos.WithdrawDTO;
import org.solvd.atm.interfaces.businessObjects.IATMInfoService;
import org.solvd.atm.interfaces.businessObjects.ICurrencyService;
import org.solvd.atm.interfaces.businessObjects.ISessionInfoService;
import org.solvd.atm.interfaces.businessObjects.IWithdrawService;
import org.solvd.atm.interfaces.presentation.ITransactionScreen;
import org.solvd.atm.interfaces.presentation.IWithdrawScreen;
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
    void setCurrencyService(ICurrencyService currencyService);
    public void setWithdrawScreen(IWithdrawScreen withdrawScreen);
    void setAtm(ATM atm);
    void setSessionAccount(AccountDTO sessionAccount);
    public void setWithdrawService(IWithdrawService withdrawService);
    void setOptionsMenuBusiness(IOptionsMenuBusiness optionsMenuBusiness);
    void setSessionInfoService(ISessionInfoService sessionInfoService);
    void setAtmInfoService(IATMInfoService atmInfoService);
}
