package org.solvd.atm.utils;

import java.util.EnumMap;

public class Denominator {



    /**
     * Method that returns the upper bound limits for withdraw-breakdown
     * @param AtmBillMap    , the map holding the amount of bills per bill-type within
     *                      the current ATM in use.
     * @return  An enumMap containing the upper limits used for calculations in withdraw-breakdown
     */
    public EnumMap<DollarDenomination, Integer> upperBounds(
            EnumMap<DollarDenomination,Integer> AtmBillMap){
        EnumMap<DollarDenomination, Integer> ret = new EnumMap<>(DollarDenomination.class);

        return ret;
    }



}
