package org.solvd.atm.utils;

public enum DollarDenomination {
    $1(1),
    $2(2),
    $5(5),
    $10(10),
    $20(20),
    $50(50),
    $100(100);

    private final int value;
    public static final String codeName = "USD";

    DollarDenomination(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

}