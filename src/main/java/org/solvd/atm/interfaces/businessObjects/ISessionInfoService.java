package org.solvd.atm.interfaces.businessObjects;

import org.solvd.atm.dtos.DepositDTO;
import org.solvd.atm.dtos.SessionDTO;
import org.solvd.atm.dtos.TransactionDTO;
import org.solvd.atm.dtos.WithdrawDTO;
import org.solvd.atm.interfaces.data.ISessionInfoDAO;

public interface ISessionInfoService {
    SessionDTO createNewSession(String atmSerie, String accountNumber);
    void addTransferToSessionBySessionId(Integer id,TransactionDTO transaction, String currencySent,Double amount);
    void addDepositToSessionBySessionId(Integer id, DepositDTO deposit,String currencySent, Double amount);
    void addWithdrawToSessionBySessionId(Integer id, WithdrawDTO withdraw, String currencySent, Double amount);
    void setSessionInfoDAO(ISessionInfoDAO sessionInfoDAO);
}
