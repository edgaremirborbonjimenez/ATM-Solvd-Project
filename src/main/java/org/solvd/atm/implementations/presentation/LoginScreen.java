package org.solvd.atm.implementations.presentation;

import org.solvd.atm.implementations.business.LoginBusiness;
import org.solvd.atm.interfaces.business.ILoginBusiness;
import org.solvd.atm.interfaces.presentation.ILoginAccountScreen;

import java.util.Scanner;

public class LoginScreen implements ILoginAccountScreen {

    Scanner scanner = new Scanner(System.in);
    ILoginBusiness loginBusiness;

    @Override
    public void showLogin(){
        System.out.println("Insert account number: ");
        String accountNumber = scanner.nextLine();
        System.out.println("Insert PIN");
        String pin = scanner.nextLine();
        loginBusiness.loginAccount(accountNumber,pin);
    }

    @Override
    public void errorMessage(){

    }

    public void setLoginBusiness (ILoginBusiness loginBusiness){
        this.loginBusiness = loginBusiness;
    }

}
