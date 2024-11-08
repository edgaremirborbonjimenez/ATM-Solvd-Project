package org.solvd.atm.implementations.businessobject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.atm.Withdraw;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.WithdrawDTO;
import org.solvd.atm.interfaces.businessObjects.ICurrencyService;
import org.solvd.atm.interfaces.businessObjects.IWithdrawService;
import org.solvd.atm.interfaces.data.IWithdrawDAO;
import org.solvd.atm.utils.exceptions.BusinessException;

public class WithdrawService implements IWithdrawService {
    private static final Logger logger = LogManager.getLogger(WithdrawService.class);

    private IWithdrawDAO withdrawDAO;
    private ICurrencyService currencyService;

    public void setWithdrawDAO(IWithdrawDAO withdrawDAO) {
        this.withdrawDAO = withdrawDAO;
    }
    public void setCurrencyService(ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public WithdrawDTO doWithdraw(String accountOrigin, Double withdrawAmount, String currency,String atmSerial) {
        try{
            Withdraw withdraw = withdrawDAO.doWithdraw(accountOrigin, withdrawAmount, currency,atmSerial);
            return convertToDTO(withdraw);
        } catch (Exception e) {
            logger.error("Error processing deposit - Account: {}, Amount: {}, Currency: {}",
                    accountOrigin, withdrawAmount, currency, e);
            throw new BusinessException("Failed to process deposit " + e);
        }
    }

    private WithdrawDTO convertToDTO(Withdraw withdraw) {
        if(withdraw == null){
            return null;
        }
        WithdrawDTO withdrawDTO = new WithdrawDTO();
        withdrawDTO.setReferenceNumber(withdraw.getReferenceNumber());

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setNumber(withdraw.getOrigin().getNumber());
        accountDTO.setCurrencies(currencyService.getAccountCurrenciesBalanceByAccountNum(accountDTO.getNumber()));
        withdrawDTO.setOriginAccount(accountDTO);

        return withdrawDTO;
    }
}
