package org.solvd.atm.domain.files;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SessionInfo {
    Integer id;
    @JsonProperty("atm_serie")
    String atmSerie;
    TransactionInfo[] transactions;

    public SessionInfo(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAtmSerie() {
        return atmSerie;
    }

    public void setAtmSerie(String atmSerie) {
        this.atmSerie = atmSerie;
    }

    public TransactionInfo[] getTransactions() {
        return transactions;
    }

    public void setTransactions(TransactionInfo[] transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(TransactionInfo transaction){
        List<TransactionInfo> newList = new LinkedList<>(Arrays.stream(this.transactions).toList());
        newList.add(transaction);
        this.transactions = newList.toArray(new TransactionInfo[0]);
    }
}
