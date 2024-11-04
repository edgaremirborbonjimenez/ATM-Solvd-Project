package org.solvd.atm.utils;

import java.util.EnumMap;

public class Denominator {

    // Upper caps for money construction. I will modify these values until they're reasonable
    private final int DEFAULT_LIMIT_$1 = 4; //  Up to 4 dollars in 1-dollar bills
    private final int DEFAULT_LIMIT_$2 = 4; //  Up to 8 dollars in 2-dollar bills
    private final int DEFAULT_LIMIT_$5 = 4; //  Up to 20 dollars in 5-dollar bills
    private final int DEFAULT_LIMIT_$10 = 4;//  Up to 40 dollars in 10-dollar bills
    private final int DEFAULT_LIMIT_$20 = 4;//  Up to 80 dollars in 20-dollar bills
    private final int DEFAULT_LIMIT_$50 = 9;//  Up to 450 dollars in 50-dollar bills
    private final int DEFAULT_LIMIT_$100 = 20; // Up to 2000 dollars in 100-dollar bills

    //  Array to easily handle the limits defined above. Used this way to conveniently modify
    //  each specific limit per denomination
    private final int[] DEFAULT_LIMITS = { DEFAULT_LIMIT_$1, DEFAULT_LIMIT_$2, DEFAULT_LIMIT_$5,
            DEFAULT_LIMIT_$10, DEFAULT_LIMIT_$20, DEFAULT_LIMIT_$50, DEFAULT_LIMIT_$100};

    /**
     * Method that returns the upper bound limits for withdraw-breakdown
     * @param AtmBillMap    , the map holding the amount of bills per bill-type within
     *                      the current ATM in use.
     * @return  An enumMap containing the upper limits used for calculations in withdraw-breakdown
     */
    public EnumMap<DollarDenomination, Integer> upperBounds(
            EnumMap<DollarDenomination, Integer> AtmBillMap){

        // Return object (EnumMap) declaration
        EnumMap<DollarDenomination, Integer> ret = new EnumMap<>(DollarDenomination.class);

        //  Store all values in the Enum as an array
        DollarDenomination[] enumKeys = DollarDenomination.values();

        // For each enumKeys, I'll store in the return map the upperbound cap for bill limits.
        //  I use Math.min to ensure the use of the upper limit declared in this class,
        //  or the amount of those dollar bills currently in the ATM (whichever is lower).
        //  If there's an issue with the AtmBillMap, its value defaults to zero.
        for(int i=0; i<enumKeys.length; i++){
            ret.put(enumKeys[i],
                    Math.min(DEFAULT_LIMITS[i],
                            AtmBillMap.getOrDefault(enumKeys[i], 0)));
        }
        return ret;
    }





}
