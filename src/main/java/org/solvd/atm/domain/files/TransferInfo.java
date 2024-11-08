package org.solvd.atm.domain.files;

public class TransferInfo extends TransactionInfo {
    String destination;

    public TransferInfo(){}

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
