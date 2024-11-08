package org.solvd.atm.implementations.businessobject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.files.SessionInfo;
import org.solvd.atm.dtos.DepositDTO;
import org.solvd.atm.dtos.SessionDTO;
import org.solvd.atm.dtos.TransactionDTO;
import org.solvd.atm.dtos.WithdrawDTO;
import org.solvd.atm.interfaces.businessObjects.ISessionInfoService;
import org.solvd.atm.interfaces.data.ISessionInfoDAO;

public class SessionInfoService implements ISessionInfoService {
    private static final Logger logger = LogManager.getLogger(SessionInfoService.class);
    ISessionInfoDAO sessionInfoDAO;


    public SessionInfoService(){}

    @Override
    public SessionDTO createNewSession(String atmSerie, String accountNumber) {
        if(atmSerie.isBlank() || accountNumber.isBlank()){
            logger.error("invalid entry values");
            return null;
        }
        SessionDTO sessionDTO = new SessionDTO();
        SessionInfo sessionInfo = sessionInfoDAO.createNewSession(atmSerie,accountNumber);
        if(sessionInfo == null){
            logger.error("Something went wrong creating a new session");
            return null;
        }
        sessionDTO.setId(sessionInfo.getId());
        return sessionDTO;
    }

    @Override
    public void addTransferToSessionBySessionId(Integer id, TransactionDTO transaction, String currencySent, Double amount) {
        if(id == null || transaction == null || currencySent.isBlank() || amount.isNaN()){
            logger.error("invalid entry values");
            return;
        }
        sessionInfoDAO.addTransferToSessionBySessionId(id,transaction,currencySent,amount);
    }

    @Override
    public void addDepositToSessionBySessionId(Integer id, DepositDTO deposit, String currencySent, Double amount) {
        if(id == null || deposit == null || currencySent.isBlank() || amount.isNaN()){
            logger.error("invalid entry values");
            return;
        }
        sessionInfoDAO.addDepositToSessionBySessionId(id,deposit,currencySent,amount);
    }

    @Override
    public void addWithdrawToSessionBySessionId(Integer id, WithdrawDTO withdraw, String currencySent, Double amount) {
        if(id == null || withdraw == null || currencySent.isBlank() || amount.isNaN()){
            logger.error("invalid entry values");
            return;
        }
        sessionInfoDAO.addWithdrawToSessionBySessionId(id,withdraw,currencySent,amount);
    }

    @Override
    public void setSessionInfoDAO(ISessionInfoDAO sessionInfoDAO) {
        this.sessionInfoDAO = sessionInfoDAO;
    }
}
