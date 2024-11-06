package org.solvd.atm.atmbuilder.products;

public class AtmMachine extends AbstractAtmMachine{
    @Override
    public void run() {
        loginBusiness.start();
    }
}
