package org.solvd.atm.dtos;

public class WithdrawDTO {
    private String referenceNumber;
    private AccountDTO originAccount;

    public WithdrawDTO(){}

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
        return "WithdrawDTO{" +
                "referenceNumber='" + referenceNumber + '\'' +
                ", originAccount=" + originAccount +
                '}';
    }
}
