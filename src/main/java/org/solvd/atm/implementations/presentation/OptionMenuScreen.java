package org.solvd.atm.implementations.presentation;

import org.solvd.atm.interfaces.business.IOptionsMenuBusiness;
import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.interfaces.business.IBalanceBusiness;
import org.solvd.atm.interfaces.business.IOptionsMenuBusiness;
import org.solvd.atm.interfaces.presentation.IBalanceScreen;
import org.solvd.atm.interfaces.presentation.IOptionsMenuScreen;

import java.util.List;
import java.util.Scanner;

public class OptionMenuScreen implements IOptionsMenuScreen {

    private IOptionsMenuBusiness optionsMenuBusiness;
    private IBalanceScreen balanceScreen;
    @Override
    public void showOptionsMenu(){

        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("Select an option:");
            System.out.println("1 - See balance");
            System.out.println("2 - Transfer money");
            System.out.println("3 - Extract money");
            System.out.println("4 - Deposit money");
            System.out.println("5 - Initialize a new ATM");
            System.out.println("0 - Close session");
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    optionsMenuBusiness.showBalance();
                    break;
                case 2:
                    System.out.println("Transfer money");
                    optionsMenuBusiness.showTransaction();
                    break;
                case 3:
                    System.out.println("Extract money");
                    optionsMenuBusiness.showWithraw();
                    break;
                case 4:
                    System.out.println("Deposit money");
                    optionsMenuBusiness.showDeposit();
                    break;
                case 5:
                    System.out.println("Initialize atm");
                    this.optionsMenuBusiness.startNewATM();
                    break;
                case 0:
                    System.out.println("Session Closed");
                    this.optionsMenuBusiness.closeSession();
                    break;
                default:
                    System.out.println("Please select a correct option");
                    this.showOptionsMenu();

            }
        } while (option != 0);

    }

    @Override
    public void setOptionsMenuBusiness(IOptionsMenuBusiness optionsMenuBusiness) {
        this.optionsMenuBusiness = optionsMenuBusiness;
    }
}
