package org.solvd.atm.interfaces.businessObjects;

import org.solvd.atm.domain.atm.ATM;
import org.solvd.atm.interfaces.data.IATMDAO;

public interface IATMService {
    ATM findOneATMSerie();
    void setATMDAO(IATMDAO atmdao);
}
