package org.solvd.atm.domain.files;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TransferInfo.class, name = "transfer"),
        @JsonSubTypes.Type(value = WithdrawInfo.class, name = "withdraw"),
        @JsonSubTypes.Type(value = DepositInfo.class, name = "deposit"),
})
public class TransactionInfo {
    String referenceNumber;
    String origin;
    Double amount;
    String currency;
    String type;

    public TransactionInfo(){}

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
