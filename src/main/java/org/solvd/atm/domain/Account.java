package org.solvd.atm.domain;

import java.util.List;

public class Account {
    private Integer id;
    private String number;
    private String PIN;
    private List<AccountCurrency> accountCurrency;

    public Account(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public List<AccountCurrency> getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(List<AccountCurrency> accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", PIN='" + PIN + '\'' +
                ", accountCurrency=" + accountCurrency +
                '}';
    }
}
