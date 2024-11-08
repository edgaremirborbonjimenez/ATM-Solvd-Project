package org.solvd.atm.domain.files;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ATMInfo {
    String serie;
    @JsonProperty("total_money")
    Double totalMoney;
    ATMBillsInfo[] bills;
    @JsonProperty("statistics")
    ATMStadisticsInfo stadistics;

    public ATMInfo(){}

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public ATMBillsInfo[] getBills() {
        return bills;
    }

    public void setBills(ATMBillsInfo[] bills) {
        this.bills = bills;
    }

    public ATMStadisticsInfo getStadistics() {
        return stadistics;
    }

    public void setStadistics(ATMStadisticsInfo stadistics) {
        this.stadistics = stadistics;
    }
}
