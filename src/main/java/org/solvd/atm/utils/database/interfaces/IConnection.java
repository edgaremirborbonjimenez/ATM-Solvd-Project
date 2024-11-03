package org.solvd.atm.utils.database.interfaces;

import org.solvd.atm.utils.database.exceptions.ConnectionException;

public interface IConnection<T> {
     T getDataSource()throws ConnectionException;
    void setPoolSize(int size)throws ConnectionException;
}
