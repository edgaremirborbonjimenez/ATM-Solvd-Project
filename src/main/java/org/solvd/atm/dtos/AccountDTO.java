package org.solvd.atm.dtos;

import java.util.List;

public class AccountDTO {
    private String number;
    private List<CurrencyDTO> currencies;

    public AccountDTO(){}

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<CurrencyDTO> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<CurrencyDTO> currencies) {
        this.currencies = currencies;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "number='" + number + '\'' +
                ", currencies=" + currencies +
                '}';
    }
}
