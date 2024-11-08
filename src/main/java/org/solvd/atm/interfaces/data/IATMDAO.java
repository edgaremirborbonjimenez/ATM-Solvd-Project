package org.solvd.atm.interfaces.data;

import org.solvd.atm.domain.atm.ATM;

import java.util.List;

public interface IATMDAO {
    ATM findOneATMSerieWhichIsNotInTheList(List<String> seriesList);
}
