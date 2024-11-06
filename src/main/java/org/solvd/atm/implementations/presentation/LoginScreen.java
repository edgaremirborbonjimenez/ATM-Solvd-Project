package org.solvd.atm.implementations.presentation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.implementations.business.LoginBusiness;
import org.solvd.atm.interfaces.business.ILoginBusiness;
import org.solvd.atm.interfaces.presentation.ILoginAccountScreen;

import java.util.Scanner;

public class LoginScreen implements ILoginAccountScreen {

    Scanner scanner = new Scanner(System.in);
    ILoginBusiness loginBusiness;
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void showLogin(){
        System.out.println("Insert account number: ");
        String accountNumber = scanner.nextLine();
        System.out.println("Insert PIN");
        String pin = scanner.nextLine();
        loginBusiness.loginAccount(accountNumber,pin);
    }

    @Override
    public void errorMessage(String message){
        System.out.println(message);
        logger.error(message);
    }

    public void setLoginBusiness (ILoginBusiness loginBusiness){
        this.loginBusiness = loginBusiness;
    }

}
