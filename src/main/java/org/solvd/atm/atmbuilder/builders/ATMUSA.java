package org.solvd.atm.atmbuilder.builders;

import org.solvd.atm.atmbuilder.products.AtmMachine;

public class ATMUSA extends AtmBuilder{

    @Override
    public void reset() {
        this.atmMachine = new AtmMachine();
    }

    @Override
    public void setPresentation() {
        //set Atm Presentations
    }

    @Override
    public void setBusiness() {
        //set Atm Businesses
    }

    @Override
    public void setServices() {
        //set Atm Services
    }

    @Override
    public void setDAOs() {
        //set Atm Daos
    }
}
