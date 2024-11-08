package org.solvd.atm.interfaces.businessObjects;

import org.solvd.atm.dtos.WithdrawDTO;
import org.solvd.atm.interfaces.data.IWithdrawDAO;

public interface IWithdrawService {
    WithdrawDTO doWithdraw(String accountOrigin,Double withdrawAmount,String currency,String atmSerial);
    void setWithdrawDAO(IWithdrawDAO withdrawDAO);
    void setCurrencyService(ICurrencyService currencyService);
}
