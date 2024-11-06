package org.solvd.atm.implementations.presentation;

import org.solvd.atm.implementations.business.BalanceBusiness;
import org.solvd.atm.interfaces.business.IBalanceBusiness;
import org.solvd.atm.interfaces.presentation.IOptionsMenuScreen;

import java.util.Scanner;

public class OptionMenuScreen implements IOptionsMenuScreen {

    @Override
    public void showOptionsMenu(){

        Scanner scanner = new Scanner(System.in);
        IBalanceBusiness balanceBusiness = new BalanceBusiness();
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
                    balanceBusiness.getAllCurrenciesBalance();
                    break;
                case 2:
                    System.out.println("Transfer money");
                    break;
                case 3:
                    System.out.println("Extract money");
                    break;
                case 4:
                    System.out.println("Deposit money");
                    break;
                case 5:
                    System.out.println("Initialize atm");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Please select a correct option");
                    this.showOptionsMenu();

            }
        } while (option != 0);

    }
}
