package org.solvd.atm.implementations.presentation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.interfaces.business.IBalanceBusiness;
import org.solvd.atm.interfaces.presentation.IBalanceScreen;

import java.util.List;

public class BalanceScreen implements IBalanceScreen {

    private IBalanceBusiness balanceBusiness;
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void showAllCurrenciesBalance() {
        List<CurrencyDTO> currencyList = balanceBusiness.getAllCurrenciesBalance();

        if(currencyList.isEmpty()){
            logger.info("There are no currencies in this account");
        } else {
            //currencyList.toString();
            for (CurrencyDTO currency : currencyList){
                System.out.println("Currency: " + currency.getName());
                System.out.println("Total: " + currency.getAmount());
                System.out.println("Equivalent in US Dolars: " + currency.getEquivalentToDollar());
            }
        }
    }

    @Override
    public void setBalanceBusiness(IBalanceBusiness balanceBusiness) {
        this.balanceBusiness = balanceBusiness;
    }
}
