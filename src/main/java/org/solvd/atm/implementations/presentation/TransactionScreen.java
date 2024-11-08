package org.solvd.atm.implementations.presentation;

import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.dtos.TransactionDTO;
import org.solvd.atm.interfaces.business.IBalanceBusiness;
import org.solvd.atm.interfaces.business.ITransactionBusiness;
import org.solvd.atm.interfaces.presentation.ITransactionScreen;

import java.util.List;
import java.util.Scanner;

public class TransactionScreen implements ITransactionScreen {

    private ITransactionBusiness transactionBusiness;
    Scanner scanner = new Scanner(System.in);

    private String accountCurrency;
    private String currencyToSend;
    private String accountNumber;
    private Double amount;

    @Override
    public void selectTransactionCurrency() {
        List<CurrencyDTO> currencyList = transactionBusiness.getCurrenciesByAccount();


        for(int i=0 ; i<currencyList.size(); i++){
           System.out.println(i + "-"+ currencyList.get(i).getName());
        }

        while(true){
            System.out.println("Select account currency to use: ");
            if(!scanner.hasNextInt()){
                System.out.println("Select valid option: ");
                scanner.next();
                continue;
            }

            int option = scanner.nextInt();
            scanner.nextLine();
            this.accountCurrency = currencyList.get(option).getName();

            System.out.println("Select currency to send: ");
            if(!scanner.hasNextInt()){
                System.out.println("Select valid option: ");
                scanner.next();
                continue;
            }
            int currToSend = scanner.nextInt();
            scanner.nextLine();
            this.currencyToSend = currencyList.get(currToSend).getName();
            break;
        }

        enterAccountNumber();

    }

    @Override
    public void enterAccountNumber() {
        System.out.println("Insert destination account: ");
        this.accountNumber = scanner.nextLine();
        enterAmount();
    }

    @Override
    public void enterAmount() {
        System.out.println("Insert amount: ");


        while (true){
            if(!scanner.hasNextDouble()){
                System.out.println("Select valid option: ");
                scanner.next();
                continue;
            }
            this.amount = scanner.nextDouble();
            break;
        }


        transactionBusiness.sendTransaction(this.accountNumber, this.amount, this.accountCurrency, this.currencyToSend);
    }

    @Override
    public void showSuccess(TransactionDTO transaction) {
        System.out.println("Transaction done succesfully" + transaction.getReferenceNumber());
    }

    @Override
    public void showErrorMessage(String err) {
        System.out.println(err);
    }

    public void setTransactionBusiness(ITransactionBusiness transactionBusiness) {
        this.transactionBusiness = transactionBusiness;
    }

}
