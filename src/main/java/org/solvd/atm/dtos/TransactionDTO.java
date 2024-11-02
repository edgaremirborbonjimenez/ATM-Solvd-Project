package org.solvd.atm.dtos;

public class TransactionDTO {
    private String referenceNumber;
    private AccountDTO originAccount;
    private AccountDTO destinationAccount;

    public TransactionDTO(){}

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public AccountDTO getOriginAccount() {
        return originAccount;
    }

    public void setOriginAccount(AccountDTO originAccount) {
        this.originAccount = originAccount;
    }

    public AccountDTO getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(AccountDTO destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "referenceNumber='" + referenceNumber + '\'' +
                ", originAccount=" + originAccount +
                ", destinationAccount=" + destinationAccount +
                '}';
    }
}
