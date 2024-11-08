package org.solvd.atm.domain.stadistics;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class TotalTransactions{
    @JsonProperty("total_transactions")
    private String totalTransactions;

    public TotalTransactions(){}

    public String getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(String totalTransactions) {
        this.totalTransactions = totalTransactions;
    }
}

