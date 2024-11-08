package org.solvd.atm.domain.atm;

public class AccountCurrency {
    private Double amount;
    private Currency currency;

    public AccountCurrency(){}

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "AccountCurrency{" +
                "amount=" + amount +
                ", currency=" + currency +
                '}';
    }
}
