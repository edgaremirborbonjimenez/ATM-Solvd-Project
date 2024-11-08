package org.solvd.atm.implementations.businessobject;

import org.solvd.atm.domain.atm.ATM;
import org.solvd.atm.interfaces.businessObjects.IATMService;
import org.solvd.atm.interfaces.data.IATMDAO;

public class ATMService implements IATMService {
    IATMDAO atmdao;

    public ATMService(){}
    @Override
    public ATM findOneATMSerie() {
        return atmdao.findOneATMSerieWhichIsNotInTheList();
    }

    @Override
    public void setATMDAO(IATMDAO atmdao) {
        this.atmdao = atmdao;
    }
}
