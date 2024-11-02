package org.solvd.atm.domain;

public class Deposit extends Transaction{
    public Deposit(){}

    @Override
    public String toString() {
        return "Deposit{" +
                "id=" + getId() +
                ", money=" + getMoney() +
                ", referenceNumber='" + getReferenceNumber() + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", currency=" + getCurrency() +
                ", origin=" + getOrigin() +
                '}';
    }
}
