package org.solvd.atm.implementations.presentation;

import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.dtos.DepositDTO;
import org.solvd.atm.interfaces.business.IDepositBusiness;
import org.solvd.atm.interfaces.presentation.IDepositScreen;
import org.solvd.atm.utils.DollarDenomination;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DepositScreen implements IDepositScreen {

    IDepositBusiness depositBusiness;
    Scanner scanner = new Scanner(System.in);

    private String currencyChoice;
    private Integer amount = 0;
    Map<DollarDenomination, Integer> denominationMap;

    @Override
    public void enterAmountToDeposit() {
        System.out.println("Insert amount of Dollars to deposit (should be integer): ");
        while (true){
            if(!scanner.hasNextDouble()){
                System.out.println("Select valid option: ");
                scanner.next();
                continue;
            }
            if(!scanner.hasNextInt()){
                System.out.println("Please insert an integer value");
                continue;
            }
            this.amount = scanner.nextInt();
            break;
        }
        selectBillsInserted();

    }

    @Override
    public void selectBillsInserted() {
        denominationMap = new HashMap<>();
        for (DollarDenomination denom : DollarDenomination.values()) {
            System.out.println("Enter the count for " + denom + " bills:");
            int count = scanner.nextInt();
            denominationMap.put(denom, count);
        }
        selectAccountCurrencyToDeposit();
    }

    @Override
    public void selectAccountCurrencyToDeposit() {
        List<CurrencyDTO> currencies = depositBusiness.getAccountCurrencies();
        System.out.println("Select the currency of the account to deposit to:");
        for (int i = 0; i < currencies.size(); i++) {
            System.out.println((i + 1) + ". " + currencies.get(i).getName());
        }
        int option = scanner.nextInt();
        this.currencyChoice = depositBusiness.getAccountCurrencies().get(option - 1).getName();
        DepositDTO depositDTO = this.depositBusiness.deposit(this.denominationMap,amount,this.currencyChoice);
        if(depositDTO == null){
            showErroMessage("Try later");
            return;
        }
        showSuccess(depositDTO);
    }

    @Override
    public void showSuccess(DepositDTO depositDTO) {
        System.out.println("Deposit successful! Details:");
        System.out.println("Reference Number: " + depositDTO.getReferenceNumber());
    }

    @Override
    public void showErroMessage(String err) {
        System.out.println("Could not process the deposit "+err );
    }

    public void setDepositBusiness(IDepositBusiness depositBusiness) {
        this.depositBusiness = depositBusiness;
    }
}
