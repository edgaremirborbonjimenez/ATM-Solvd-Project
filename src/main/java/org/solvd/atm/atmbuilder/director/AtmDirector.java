package org.solvd.atm.atmbuilder.director;

import org.solvd.atm.atmbuilder.builders.AtmBuilder;

public class AtmDirector {
    static AtmDirector atmDirector;

    private AtmDirector(){}

    public static AtmDirector getInstance(){
        if(atmDirector == null){
            atmDirector = new AtmDirector();
        }
        return atmDirector;
    }

    public void makeATM(AtmBuilder builder){
        builder.reset();
        builder.setPresentation();
        builder.setBusiness();
        builder.setServices();
        builder.setDAOs();
        builder.setDependencies();
    }
}
