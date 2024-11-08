package org.solvd.atm.interfaces.businessObjects;

import org.solvd.atm.domain.atm.ATM;
import org.solvd.atm.implementations.data.ATMInfoDAO;
import org.solvd.atm.interfaces.data.IATMInfoDAO;

public interface IATMInfoService {
    ATM createNewATM(String atmSerie);
    void updateATMStadisticsBySerie(String atmSerie);
    void updateATMBillBySerie(String atmSerie,ATM atm);
    ATM FinalcreateNewATM(String atmSerie);
    void FinalupdateATMStadisticsBySerie(String atmSerie);
    void FinalupdateATMBillBySerie(String atmSerie,ATM atm);
    void setATMInfoDAO(IATMInfoDAO atmInfoDAO);
}
