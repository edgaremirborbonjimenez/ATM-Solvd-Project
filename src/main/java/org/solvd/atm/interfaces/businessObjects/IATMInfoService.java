package org.solvd.atm.interfaces.businessObjects;

import org.solvd.atm.domain.atm.ATM;
import org.solvd.atm.implementations.data.ATMInfoDAO;

public interface IATMInfoService {
    ATM createNewATM(String atmSerie,String accountNumber);
    void updateATMStadisticsBySerie(String atmSerie);
    void updateATMBillBySerie(String atmSerie,ATM atm);
    void setATMInfoDAO(ATMInfoDAO atmInfoDAO);
}
