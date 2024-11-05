package org.solvd.atm.utils;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Denominator {

    // Upper caps for money construction. I will modify these values until they're reasonable
    private final int DEFAULT_LIMIT_$1 = 5; //  Up to 5 dollars in 1-dollar bills
    private final int DEFAULT_LIMIT_$2 = 5; //  Up to 10 dollars in 2-dollar bills
    private final int DEFAULT_LIMIT_$5 = 5; //  Up to 25 dollars in 5-dollar bills
    private final int DEFAULT_LIMIT_$10 = 5;//  Up to 50 dollars in 10-dollar bills
    private final int DEFAULT_LIMIT_$20 = 10;//  Up to 200 dollars in 20-dollar bills
    private final int DEFAULT_LIMIT_$50 = 20;//  Up to 1000 dollars in 50-dollar bills
    private final int DEFAULT_LIMIT_$100 = 30; // Up to 3000 dollars in 100-dollar bills

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
    private EnumMap<DollarDenomination, Integer> upperBounds(
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

    /**
     * Method to generate all possible Map combinations of DollarDenomination and their amounts
     * required to sum up to the target amount.
     *
     * @param targetAmount  the target dollar amount to reach with combinations
     * @param atmBillMap    the map holding the amount of bills per bill-type within the current ATM in use
     * @return a list of EnumMap combinations summing up to the targetAmount
     */
    public List<EnumMap<DollarDenomination, Integer>> generateCombinations(
            int targetAmount, EnumMap<DollarDenomination, Integer> atmBillMap) {

        //  Return list for the function
        List<EnumMap<DollarDenomination, Integer>> combinations = new ArrayList<>();
        //  Stores the values from the enum
        DollarDenomination[] denominations = DollarDenomination.values();

        //  Use upperBounds to get the max limits for each denomination
        EnumMap<DollarDenomination, Integer> atmBillLimits = upperBounds(atmBillMap);
        int[] limits = new int[denominations.length];
        // Initialize limits based on atmBillLimits obtained from upperBounds
        for (int i = 0; i < denominations.length; i++) {
            limits[i] = atmBillLimits.getOrDefault(denominations[i], 0);
        }


        // Initialize counters for each denomination starting at 0
        int[] counts = new int[denominations.length];

        //  Iterate for every possible combination
        boolean keep_iterating = true;
        while (keep_iterating) {
            // Calculate the total amount for the current combination
            int totalAmount = 0;
            for (int i = 0; i < denominations.length; i++) {
                totalAmount += counts[i] * denominations[i].getValue();
            }

            //  Check if the current combination matches the target amount.
            //  If it does, store the current counts as the corresponding Map.
            if (totalAmount == targetAmount) {
                EnumMap<DollarDenomination, Integer> combination = new EnumMap<>(DollarDenomination.class);
                for (int i = 0; i < denominations.length; i++) {
                    combination.put(denominations[i], counts[i]);
                }
                combinations.add(combination);
            }

            //  Increment counters and handle overflow for each denomination
            //  This code will increment I-LIMIT-times the i'th counts counter, then
            //  start counting the I+1-th counter when it overflows, thus checking
            //  all potential combinations of bills within the bounds.
            //  When going back to the start of this menu, it will only save it as a Map
            //  if it matches the parsed amount.
            int i = 0;
            while (i < denominations.length) {
                counts[i]++;
                if (counts[i] <= limits[i]) {
                    break;
                } else {
                    counts[i] = 0; // Reset current counter and carry over
                    i++;
                }
            }

            // If we've exhausted all combinations, exit the loop
            if (i == denominations.length) {
                keep_iterating = false;
            }
        }

        return combinations;
    }

    public static int totalMappedAmount(EnumMap<DollarDenomination, Integer> parsedMap){

        int ret = 0;
        for (Map.Entry<DollarDenomination, Integer> entry : parsedMap.entrySet()) {
            ret += entry.getKey().getValue() * entry.getValue();
        }
        return ret;
    }

}
