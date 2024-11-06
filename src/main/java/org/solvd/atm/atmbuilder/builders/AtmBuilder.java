package org.solvd.atm.atmbuilder.builders;

import org.solvd.atm.atmbuilder.products.AbstractAtmMachine;

public abstract class AtmBuilder {
    protected AbstractAtmMachine atmMachine;
    public abstract void reset();
    public abstract void setPresentation();
    public abstract void setBusiness();
    public abstract void setServices();
    public abstract void setDAOs();
    public AbstractAtmMachine getResult(){
        return atmMachine;
    }
}
