package org.solvd.atm.implementations.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.atm.AccountCurrency;
import org.solvd.atm.domain.atm.Currency;
import org.solvd.atm.interfaces.data.ICurrencyDAO;
import org.solvd.atm.utils.database.implementations.HikariCPDataSource;
import org.solvd.atm.utils.exceptions.DataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO implements ICurrencyDAO {
    private static final Logger logger = LogManager.getLogger(CurrencyDAO.class);
    private final HikariCPDataSource dataSource;

    public CurrencyDAO() {
        this.dataSource = HikariCPDataSource.getInstance();
    }

    private static final String SELECT_ACC_CURRENCIES =
            "SELECT c.id, c.name, c.equivalent_to_dollar, ac.money " +
                "FROM currencies c " +
                "JOIN account_currencies ac ON c.id = ac.currency_id " +
                "JOIN accounts a ON a.id = ac.account_id " +
                "WHERE a.number = ?";

    private static final String SELECT_ACC_CURRENCIES_NAME =
            SELECT_ACC_CURRENCIES + "AND c.name = ?";

    private static final String SELECT_CURRENCY_NAME =
            "SELECT id, name, equivalent_to_dollar FROM currencies WHERE name = ?";

    @Override
    public List<Currency> getAccountCurrenciesByAccountNum(String accountNumber) {
        List<Currency> currencies = new ArrayList<>();

        try (Connection conn = dataSource.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT_ACC_CURRENCIES)) {

            statement.setString(1, accountNumber);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Currency currency = new Currency();
                    currency.setId(rs.getInt("id"));
                    currency.setName(rs.getString("name"));
                    currency.setEquivalentToDollar(rs.getDouble("equivalent_to_dollar"));
                    currencies.add(currency);
                }
            }

        } catch (SQLException e) {
            logger.error("Error getting account {} currencies from Database ", accountNumber);
            throw new DataException("Error getting account currencies from Database"+ e);
        }

        return currencies;
    }

    @Override
    public Currency findCurrencyByName(String name) {
        try (Connection conn = dataSource.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT_CURRENCY_NAME)) {

            statement.setString(1, name);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Currency currency = new Currency();
                    currency.setId(rs.getInt("id"));
                    currency.setName(rs.getString("name"));
                    currency.setEquivalentToDollar(rs.getDouble("equivalent_to_dollar"));
                    return currency;
                }
            }

        } catch (SQLException e) {
            logger.error("Error retrieving currency {} from database ", name);
            throw new DataException("Error getting currency from database"+ e);
        }
        return null;
    }

    @Override
    public AccountCurrency getAccountCurrencyWithBalanceByCurrencyName(String accountNumber, String currency) {

        try (Connection conn = dataSource.getDataSource().getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT_ACC_CURRENCIES_NAME)) {

            statement.setString(1, accountNumber);
            statement.setString(2, currency);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    AccountCurrency accCurrency = new AccountCurrency();
                    Currency currency_ = new Currency();

                    currency_.setId(rs.getInt("id"));
                    currency_.setName(rs.getString("name"));
                    currency_.setEquivalentToDollar(rs.getDouble("equivalent_to_dollar"));

                    accCurrency.setCurrency(currency_);
                    accCurrency.setAmount(rs.getDouble("money"));
                    return accCurrency;
                }
            }


        }catch (SQLException e) {
            logger.error("Error retrieving Account Currency {} with balance in {} from database ", accountNumber,currency);
            throw new DataException("Error getting currency from database"+ e);
        }

        return null;
    }
}
