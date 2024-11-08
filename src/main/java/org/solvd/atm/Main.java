package org.solvd.atm;

import org.solvd.atm.atmbuilder.client.AtmClient;
import org.solvd.atm.implementations.data.DepositDAO;
import org.solvd.atm.implementations.data.WithdrawDAO;

public class Main {
    public static void main(String[] args) {
        AtmClient.getInstance().startNewATM();
    }
}
