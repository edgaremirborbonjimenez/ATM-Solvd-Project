package org.solvd.atm.interfaces.data;

import org.solvd.atm.domain.atm.ATM;
import org.solvd.atm.domain.files.ATMInfo;

public interface IATMInfoDAO {
    ATMInfo createNewATM(String atmSerie);
    void updateATMBillsBySerie(String atmSerie, ATM atm);
    void updateATMStadisticsBySerie(String atmSerie);
    ATMInfo findATMBySerie(String atmSerie);

}
