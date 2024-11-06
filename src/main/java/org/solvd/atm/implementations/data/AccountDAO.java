package org.solvd.atm.implementations.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.Account;
import org.solvd.atm.interfaces.data.IAccountDAO;
import org.solvd.atm.utils.database.implementations.HikariCPDataSource;

import javax.xml.transform.Result;
import java.sql.*;

public class AccountDAO implements IAccountDAO {

    private static final Logger logger = LogManager.getLogger();
    private HikariCPDataSource  h;

    public AccountDAO(){
        HikariCPDataSource.getInstance().setPoolSize(5);
        h = HikariCPDataSource.getInstance();
    }
    String query = "SELECT * FROM accounts where number = ? AND pin = ?";

    @Override
    public Account findAccountByNumberAndPin(String accountNumber, String PIN){


        Account account = new Account();
        try(Connection connection = h.getDataSource().getConnection()){

            PreparedStatement getStatement = null;
            getStatement = connection.prepareStatement(query);
            getStatement.setString(1,accountNumber);
            getStatement.setString(2, PIN);

            //Statement st = connection.createStatement();
            ResultSet resultSet = getStatement.executeQuery();

            if(resultSet.next()){
                account.setNumber(resultSet.getString("number"));
                account.setPIN(resultSet.getString("pin"));
            } else {
                logger.info("Invalid account number or pin");
            }
        }catch (SQLException e){
            logger.error(e.getMessage());
        }
        return account;
    }

    @Override
    public Account findAccountByNumber(String accountNumber){
        return null;
    }
}
