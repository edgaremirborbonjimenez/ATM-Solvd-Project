package org.solvd.atm.interfaces.businessObjects;

import org.solvd.atm.dtos.WithdrawDTO;

public interface IWithdrawService {
    WithdrawDTO doWithdraw(String accountOrigin,Double withdrawAmount,String currency);
}
