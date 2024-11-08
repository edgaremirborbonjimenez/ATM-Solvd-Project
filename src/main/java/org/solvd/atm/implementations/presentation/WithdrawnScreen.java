package org.solvd.atm.implementations.presentation;

import org.solvd.atm.dtos.CurrencyDTO;
import org.solvd.atm.dtos.WithdrawDTO;
import org.solvd.atm.interfaces.business.IWithdrawBusiness;
import org.solvd.atm.interfaces.presentation.IWithdrawScreen;
import org.solvd.atm.utils.DollarDenomination;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class WithdrawnScreen implements IWithdrawScreen {

    IWithdrawBusiness withdrawBusiness;
    Scanner scanner = new Scanner(System.in);

    @Override
    public void selectAccountCurrency() {
        List<CurrencyDTO> currencies = withdrawBusiness.getCurrenciesByAccount();
        System.out.println("Select the currency of the account to withdraw from:");
        for (int i = 0; i < currencies.size(); i++) {
            System.out.println((i + 1) + ". " + currencies.get(i).getName());
        }

        int currencyChoice = scanner.nextInt();
        String selectedCurrency = currencies.get(currencyChoice - 1).getName();

        enterAmount(selectedCurrency);
    }

    @Override
    public void enterAmount(String selectedCurrency) {
        System.out.println("Enter the amount to withdraw (should be integer):");
        Scanner scanner = new Scanner(System.in);
        int amountToWithdraw = scanner.nextInt();

        List<EnumMap<DollarDenomination, Integer>> availableDenominations = withdrawBusiness.getDenominations(amountToWithdraw);
        showDenomination(availableDenominations, amountToWithdraw,selectedCurrency);
    }

    public void showDenomination(List<EnumMap<DollarDenomination, Integer>> denominations, int amount, String currency) {
        System.out.println("Select denominations for the withdrawal:");
        for (int i = 0; i < denominations.size(); i++) {
            System.out.println("Option " + (i + 1) + ": " + denominations.get(i));
        }

        Scanner scanner = new Scanner(System.in);
        int denominationChoice = scanner.nextInt();
        Map<DollarDenomination, Integer> selectedDenomination = denominations.get(denominationChoice - 1);

        try {
            WithdrawDTO withdrawResult = withdrawBusiness.doWithdraw(amount, selectedDenomination, currency);
            showSuccess(withdrawResult);
        } catch (Exception e) {
            showErrorMessage(e.getMessage());
        }
    }


    @Override
    public void showSuccess(WithdrawDTO withdraw) {
        System.out.println("Withdrawal successful! Details:");
        System.out.println("Reference Number: " + withdraw.getReferenceNumber());
    }

    @Override
    public void showErrorMessage(String err) {
        System.out.println("Error: " + err);
    }

    public void setWithdrawBusiness(IWithdrawBusiness withdrawBusiness) {
        this.withdrawBusiness = withdrawBusiness;
    }
}
