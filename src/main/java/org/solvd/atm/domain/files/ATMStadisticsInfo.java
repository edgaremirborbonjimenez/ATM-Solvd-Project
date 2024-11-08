package org.solvd.atm.domain.files;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.solvd.atm.domain.stadistics.AverageWithdraw;
import org.solvd.atm.domain.stadistics.LargestWithdraw;
import org.solvd.atm.domain.stadistics.TotalTransactions;

public class ATMStadisticsInfo {
    @JsonProperty("average_withdraw")
    AverageWithdraw averageWithdraw;
    @JsonProperty("transaction_counter")
    TotalTransactions transactionCounter;
    @JsonProperty("largest_withdraw")
    LargestWithdraw largestWithdraw;

    public ATMStadisticsInfo(){}

    public AverageWithdraw getAverageWithdraw() {
        return averageWithdraw;
    }

    public void setAverageWithdraw(AverageWithdraw averageWithdraw) {
        this.averageWithdraw = averageWithdraw;
    }

    public TotalTransactions getTransactionCounter() {
        return transactionCounter;
    }

    public void setTransactionCounter(TotalTransactions transactionCounter) {
        this.transactionCounter = transactionCounter;
    }

    public LargestWithdraw getLargestWithdraw() {
        return largestWithdraw;
    }

    public void setLargestWithdraw(LargestWithdraw largestWithdraw) {
        this.largestWithdraw = largestWithdraw;
    }
}
