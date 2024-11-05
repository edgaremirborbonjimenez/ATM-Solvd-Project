package org.solvd.atm.implementations.businessobject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.Deposit;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.DepositDTO;
import org.solvd.atm.interfaces.businessObjects.ICurrencyService;
import org.solvd.atm.interfaces.businessObjects.IDepositService;
import org.solvd.atm.interfaces.data.IDepositDAO;
import org.solvd.atm.utils.exceptions.BusinessException;


public class DepositService implements IDepositService {
    private static final Logger logger = LogManager.getLogger(DepositService.class);
    private ICurrencyService currencyService;
    private IDepositDAO depositDAO;

    public void setCurrencyService(ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public void setDepositDAO(IDepositDAO depositDAO){
        this.depositDAO = depositDAO;
    }

    @Override
    public DepositDTO deposit(String accountNumber, Double amount, String currency) {

        try{
            if (!currencyService.validateAccountCurrencyBalance(accountNumber, amount, currency)) {
                throw new BusinessException("Invalid deposit amount for account currency balance " + currency);
            }
            Deposit depositResult = depositDAO.deposit(accountNumber, amount, currency);

            return mapToDepositDTO(depositResult);

        } catch (Exception e) {
            logger.error("Error processing deposit - Account: {}, Amount: {}, Currency: {}",
                    accountNumber, amount, currency, e);
            throw new BusinessException("Failed to process deposit " + e);
        }
    }

    private DepositDTO mapToDepositDTO(Deposit deposit) {
        if (deposit == null) {
            return null;
        }

        String accountNumber = deposit.getReferenceNumber();
        AccountDTO accountDTO = new AccountDTO();
        DepositDTO depositDTO = new DepositDTO();

        accountDTO.setNumber(accountNumber);
        accountDTO.setCurrencies(currencyService.getAccountCurrenciesBalanceByAccountNum(accountNumber));

        depositDTO.setOriginAccount(accountDTO);
        depositDTO.setReferenceNumber(deposit.getReferenceNumber());

        return depositDTO;
    }

}
