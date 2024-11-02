package org.solvd.atm.domain;

public class Transfer extends Transaction{

    private Account destination;

    public Transfer(){}

    public Account getDestination() {
        return destination;
    }

    public void setDestination(Account destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + getId() +
                ", money=" + getMoney() +
                ", referenceNumber='" + getReferenceNumber() + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", currency=" + getCurrency() +
                ", origin=" + getOrigin() +
                ", destination=" + destination +
                '}';
    }
}
