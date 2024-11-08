package org.solvd.atm.domain.atm;

public class Withdraw extends Transaction{
    public Withdraw(){}

    @Override
    public String toString() {
        return "Withdraw{" +
                "id=" + getId() +
                ", money=" + getMoney() +
                ", referenceNumber='" + getReferenceNumber() + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", currency=" + getCurrency() +
                ", origin=" + getOrigin() +
                '}';
    }
}
