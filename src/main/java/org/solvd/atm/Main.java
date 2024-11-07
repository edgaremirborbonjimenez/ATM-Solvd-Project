package org.solvd.atm;

import org.solvd.atm.atmbuilder.client.AtmClient;

public class Main {
    public static void main(String[] args) {
        AtmClient.getInstance().startNewATM();
    }
}
