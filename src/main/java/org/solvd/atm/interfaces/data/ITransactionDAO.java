package org.solvd.atm.interfaces.data;

import org.solvd.atm.domain.Transfer;

public interface ITransactionDAO {
    Transfer sendTransaction(String originAccount, String destinationAccount, String currency, Double amount);
}
