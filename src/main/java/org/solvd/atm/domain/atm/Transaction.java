package org.solvd.atm.domain.atm;

import java.util.Date;

public class Transaction {
    private Integer id;
    private Double money;
    private String referenceNumber;
    private Date createdAt;
    private Currency currency;
    private Account origin;

    public Transaction(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Account getOrigin() {
        return origin;
    }

    public void setOrigin(Account origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", money=" + money +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", createdAt=" + createdAt +
                ", currency=" + currency +
                ", origin=" + origin +
                '}';
    }
}
