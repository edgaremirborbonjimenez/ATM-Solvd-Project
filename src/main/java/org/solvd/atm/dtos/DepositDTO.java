package org.solvd.atm.dtos;

public class DepositDTO {
    private String referenceNumber;
    AccountDTO originAccount;

    public DepositDTO(){}

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

    @Override
    public String toString() {
        return "DepositDTO{" +
                "referenceNumber='" + referenceNumber + '\'' +
                ", originAccount=" + originAccount +
                '}';
    }
}
