package org.solvd.atm.domain;

import java.util.List;
import java.util.Map;

public class ATM {
    private Integer id;
    private String serieNumber;
    private Map<Integer,Integer> money;
    private List<Transaction> transactions;
    private Currency currency;

    public ATM(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerieNumber() {
        return serieNumber;
    }

    public void setSerieNumber(String serieNumber) {
        this.serieNumber = serieNumber;
    }

    public Map<Integer, Integer> getMoney() {
        return money;
    }

    public void setMoney(Map<Integer, Integer> money) {
        this.money = money;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "ATM{" +
                "id=" + id +
                ", serieNumber='" + serieNumber + '\'' +
                ", money=" + money +
                ", transactions=" + transactions +
                ", currency=" + currency +
                '}';
    }
}
