package org.solvd.atm;

import org.solvd.atm.domain.atm.ATM;
import org.solvd.atm.domain.files.DepositInfo;
import org.solvd.atm.dtos.AccountDTO;
import org.solvd.atm.dtos.DepositDTO;
import org.solvd.atm.dtos.TransactionDTO;
import org.solvd.atm.dtos.WithdrawDTO;
import org.solvd.atm.implementations.businessobject.SessionInfoService;
import org.solvd.atm.implementations.data.ATMDAO;
import org.solvd.atm.implementations.data.ATMInfoDAO;
import org.solvd.atm.implementations.data.SessionInfoDAO;
import org.solvd.atm.interfaces.businessObjects.IATMInfoService;
import org.solvd.atm.interfaces.data.IATMDAO;
import org.solvd.atm.interfaces.data.IATMInfoDAO;
import org.solvd.atm.utils.DollarDenomination;
import org.solvd.atm.utils.database.implementations.HikariCPDataSource;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

    }
}
