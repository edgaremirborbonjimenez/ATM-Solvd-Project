package org.solvd.atm.domain.files;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.solvd.atm.utils.DollarDenomination;

public class ATMBillsInfo {

    DollarDenomination bill;
    Integer amount;

    public ATMBillsInfo(){}

    public DollarDenomination getBill() {
        return bill;
    }

    public void setBill(DollarDenomination bill) {
        this.bill = bill;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
