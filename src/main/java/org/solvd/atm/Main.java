package org.solvd.atm;
import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.implementations.business.BalanceBusiness;
import org.solvd.atm.implementations.business.LoginBusiness;
import org.solvd.atm.implementations.business.OptionMenuBusiness;
import org.solvd.atm.implementations.business.TransactionBusiness;
import org.solvd.atm.implementations.businessobject.AccountService;
import org.solvd.atm.implementations.businessobject.CurrencyService;
import org.solvd.atm.implementations.data.AccountDAO;
import org.solvd.atm.implementations.data.CurrencyDAO;
import org.solvd.atm.implementations.presentation.BalanceScreen;
import org.solvd.atm.implementations.presentation.LoginScreen;
import org.solvd.atm.implementations.presentation.OptionMenuScreen;
import org.solvd.atm.implementations.presentation.TransactionScreen;
import org.solvd.atm.interfaces.business.IBalanceBusiness;
import org.solvd.atm.interfaces.businessObjects.IAccountService;
import org.solvd.atm.interfaces.data.IAccountDAO;
import org.solvd.atm.interfaces.presentation.IBalanceScreen;
import org.solvd.atm.utils.database.implementations.HikariCPDataSource;

public class Main {

    public static void main(String [] args){
        HikariCPDataSource.getInstance().setPoolSize(5);
        AccountDAO accountDAO = new AccountDAO();

        AccountService accountService = new AccountService();
        accountService.setAccountDAO(accountDAO);

        CurrencyService currencyService = new CurrencyService();


        LoginBusiness loginBusiness = new LoginBusiness();
        OptionMenuBusiness optionMenuBusiness = new OptionMenuBusiness();
        LoginScreen loginScreen = new LoginScreen();
        loginBusiness.setLoginAccountScreen(loginScreen);
        loginScreen.setLoginBusiness(loginBusiness);
        loginBusiness.setAccountService(accountService);
        loginBusiness.setOptionsMenuBusiness(optionMenuBusiness);
        OptionMenuScreen optionMenuScreen = new OptionMenuScreen();
        loginBusiness.setOptionsMenuScreen(optionMenuScreen);
        IBalanceBusiness balanceBusiness = new BalanceBusiness();
        IBalanceScreen balanceScreen = new BalanceScreen();
        balanceScreen.setBalanceBusiness(balanceBusiness);
        optionMenuBusiness.setBalanceScreen(balanceScreen);
        optionMenuBusiness.setBalanceBusiness(balanceBusiness);
        optionMenuScreen.setOptionsMenuBusiness(optionMenuBusiness);
        balanceBusiness.setCurrencyService(currencyService);
        currencyService.setAccountDAO(accountDAO);
        CurrencyDAO currencyDAO = new CurrencyDAO();
        currencyService.setCurrencyDAO(currencyDAO);
        optionMenuBusiness.setTransactionBusiness(new TransactionBusiness());
        optionMenuBusiness.setTransactionScreen(new TransactionScreen());
        loginBusiness.start();
      /*
              SessionInfoDAO dao = new SessionInfoDAO();
        SessionInfoService s = new SessionInfoService();
        s.setSessionInfoDAO(dao);

        s.createNewSession("ATM02","12345678");
        AccountDTO a = new AccountDTO();
        a.setNumber("987987564");
        AccountDTO a1 = new AccountDTO();
        a1.setNumber("321654789");
        DepositDTO depositInfo = new DepositDTO();
        depositInfo.setOriginAccount(a);
        depositInfo.setReferenceNumber("1546156");
        s.addDepositToSessionBySessionId(3,depositInfo,"MXN",800d);

        WithdrawDTO depositInf = new WithdrawDTO();
        depositInf.setOriginAccount(a);
        depositInf.setReferenceNumber("1546156");
        s.addWithdrawToSessionBySessionId(2,depositInf,"MXN",900d);

        TransactionDTO t = new TransactionDTO();

        t.setReferenceNumber("987987");
        t.setDestinationAccount(a);
        t.setOriginAccount(a);
        s.addTransferToSessionBySessionId(4,t,"USA",1000d);*/
    }
}
