package org.solvd.atm.dtos;

public class CurrencyDTO {
    private String name;
    private Double amount;

    public CurrencyDTO(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CurrencyDTO{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
