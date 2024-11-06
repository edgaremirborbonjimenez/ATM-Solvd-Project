package org.solvd.atm.dtos;

public class CurrencyDTO {
    private String name;
    private Double amount;
    private Double equivalentToDollar;

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

    public Double getEquivalentToDollar() {
        return equivalentToDollar;
    }

    public void setEquivalentToDollar(Double equivalentToDollar) {
        this.equivalentToDollar = equivalentToDollar;
    }

    @Override
    public String toString() {
        return "CurrencyDTO{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", equivalentToDollar=" + equivalentToDollar +
                '}';
    }
}
