package org.solvd.atm.interfaces.data;

import org.solvd.atm.domain.files.SessionInfo;
import org.solvd.atm.dtos.DepositDTO;
import org.solvd.atm.dtos.TransactionDTO;
import org.solvd.atm.dtos.WithdrawDTO;

public interface ISessionInfoDAO {
    SessionInfo createNewSession(String atmSerie, String accountNumber);
    void addTransferToSessionBySessionId(Integer id, TransactionDTO transaction, String currencySent, Double amount);
    void addDepositToSessionBySessionId(Integer id, DepositDTO deposit, String currencySent, Double amount);
    void addWithdrawToSessionBySessionId(Integer id, WithdrawDTO withdraw, String currencySent, Double amount);
}
