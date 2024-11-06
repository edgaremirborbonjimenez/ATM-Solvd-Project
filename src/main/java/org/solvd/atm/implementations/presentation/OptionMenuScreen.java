package org.solvd.atm.implementations.presentation;

import org.solvd.atm.interfaces.presentation.IOptionsMenuScreen;

import java.util.Scanner;

public class OptionMenuScreen implements IOptionsMenuScreen {

    @Override
    public void showOptionsMenu(){

        Scanner scanner = new Scanner(System.in);

        int option;
        do {
            System.out.println("Select an option:");
            System.out.println("1 - See balance");
            System.out.println("2 - Transfer money");
            System.out.println("3 - Extract money");
            System.out.println("0 - Close session");
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    System.out.println("Balance here");
                    break;
                case 2:
                    System.out.println("Transfer money");
                    break;
                case 3:
                    System.out.println("Extract money");
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
