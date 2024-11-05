package org.solvd.atm.implementations.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.Account;
import org.solvd.atm.domain.AccountCurrency;
import org.solvd.atm.domain.Currency;
import org.solvd.atm.interfaces.data.IAccountDAO;
import org.solvd.atm.utils.database.implementations.HikariCPDataSource;
import org.solvd.atm.utils.exceptions.DataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements IAccountDAO {
    private static final Logger logger = LogManager.getLogger(AccountDAO.class);
    private final HikariCPDataSource dataSource;

    public AccountDAO() {
        this.dataSource = HikariCPDataSource.getInstance();
    }

    private static final String SELECT_ACCOUNT_NAME =
            "SELECT a.id, a.number, a.pin, " +
                "ac.id as ac_id, ac.money, ac.currency_id, " +
                "c.name as currency_name, c.equivalent_to_dollar " +
                "FROM accounts a " +
                "LEFT JOIN account_currencies ac ON a.id = ac.account_id " +
                "LEFT JOIN currencies c ON ac.currency_id = c.id " +
                "WHERE a.number = ?";

    private static final String SELECT_ACCOUNT_PIN =
            SELECT_ACCOUNT_NAME + " AND a.pin = ?";


    @Override
    public Account findAccountByNumberAndPin(String accountNumber, String PIN) {
        try (Connection conn = dataSource.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT_ACCOUNT_PIN)) {

            statement.setString(1, accountNumber);
            statement.setString(2, PIN);

            try (ResultSet rs = statement.executeQuery()) {
                return extractAccountFromResultSet(rs);
            }

        } catch (SQLException e) {
            logger.error("Error finding account in the database  - AccountNumber: {}",
                    accountNumber);
            throw new DataException("Error finding account by number and PIN "+ e);
        }
    }


    @Override
    public Account findAccountByNumber(String accountNumber) {
        try (Connection conn = dataSource.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT_ACCOUNT_NAME)) {

            statement.setString(1, accountNumber);

            try (ResultSet rs = statement.executeQuery()) {
                return extractAccountFromResultSet(rs);
            }

        } catch (SQLException e) {
            logger.error("Error finding account in the database - AccountNumber: {}",
                    accountNumber);
            throw new DataException("Error finding account by number  "+ e);
        }
    }

    private Account extractAccountFromResultSet(ResultSet rs) throws SQLException {
        if (!rs.next()) {
            return null;
        }

        Account account = new Account();
        account.setId(rs.getInt("id"));
        account.setNumber(rs.getString("number"));
        account.setPIN(rs.getString("pin"));

        List<AccountCurrency> accountCurrencies = new ArrayList<>();
        do {
            if (rs.getObject("ac_id") != null) {
                AccountCurrency accountCurrency = new AccountCurrency();
                Currency currency = new Currency();

                currency.setId(rs.getInt("currency_id"));
                currency.setName(rs.getString("currency_name"));
                currency.setEquivalentToDollar(rs.getDouble("equivalent_to_dollar"));

                accountCurrency.setCurrency(currency);
                accountCurrency.setAmount(rs.getDouble("money"));

                accountCurrencies.add(accountCurrency);
            }
        } while (rs.next());

        account.setAccountCurrency(accountCurrencies);
        return account;
    }
}
