package org.solvd.atm.interfaces.data;

import org.solvd.atm.domain.atm.Withdraw;

public interface IWithdrawDAO {
    Withdraw doWithdraw(String accountOrigin, Double withdrawAmount, String currency, String atmSerial);
}
