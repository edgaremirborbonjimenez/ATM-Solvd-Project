package org.solvd.atm.implementations.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.Account;
import org.solvd.atm.domain.Currency;
import org.solvd.atm.domain.Deposit;
import org.solvd.atm.interfaces.data.IDepositDAO;
import org.solvd.atm.utils.database.exceptions.ConnectionException;
import org.solvd.atm.utils.database.implementations.HikariCPDataSource;
import org.solvd.atm.utils.exceptions.DataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class DepositDAO implements IDepositDAO {
    private static final Logger logger = LogManager.getLogger(DepositDAO.class);
    private final HikariCPDataSource dataSource;


    private static final String INSERT_DEPOSIT =
            "INSERT INTO deposits (reference_number, money, origin_account_id, currency_id) " +
                    "VALUES (?, ?, (SELECT id FROM accounts WHERE number = ?), " +
                    "(SELECT id FROM currencies WHERE name = ?))";

    private static final String UPDATE_ACCOUNT_BALANCE =
            "UPDATE account_currencies SET money = money + ? " +
                    "WHERE account_id = (SELECT id FROM accounts WHERE number = ?) " +
                    "AND currency_id = (SELECT id FROM currencies WHERE name = ?)";

    private static final String SELECT_DEPOSIT =
            "SELECT d.id, d.reference_number, d.money, d.created_at, " +
                    "c.id as currency_id, c.name as currency_name, c.equivalent_to_dollar, " +
                    "a.id as account_id, a.number as account_number " +
                    "FROM deposits d " +
                    "JOIN currencies c ON d.currency_id = c.id " +
                    "JOIN accounts a ON d.origin_account_id = a.id " +
                    "WHERE d.reference_number = ?";


    public DepositDAO() {
        this.dataSource = HikariCPDataSource.getInstance();
        //try {
        //    this.dataSource.setPoolSize(1);
        //} catch (ConnectionException e) {
        //    logger.error("Failed to set connection pool size", e);
        //    throw new DataException("Failed to initialize DAO "+ e);
        //}
    }


    @Override
    public Deposit deposit(String accountNumber, Double amount, String currency) {

        String referenceNumber = generateReferenceNumber(); // for storing & retrieving

        try(Connection conn = dataSource.getDataSource().getConnection()) {
            PreparedStatement insertStmt = null;
            PreparedStatement updateStmt = null;

            conn.setAutoCommit(false); // begin transaction

            insertStmt = conn.prepareStatement(INSERT_DEPOSIT);
            insertStmt.setString(1, referenceNumber);
            insertStmt.setDouble(2, amount);
            insertStmt.setString(3, accountNumber);
            insertStmt.setString(4, currency);

            int insertResult = insertStmt.executeUpdate();
            if (insertResult != 1) {
                throw new SQLException("Failed to insert deposit record");
            }

            updateStmt = conn.prepareStatement(UPDATE_ACCOUNT_BALANCE);
            updateStmt.setDouble(1, amount);
            updateStmt.setString(2, accountNumber);
            updateStmt.setString(3, currency);

            int updateResult = updateStmt.executeUpdate();
            if (updateResult != 1) {
                throw new SQLException("Failed to update account balance");
            }

            conn.commit(); // commit the trans
            return getDepositByReference(conn, referenceNumber);

        } catch (SQLException e) {
            logger.error("Error storing deposit in database - Account: {}, Amount: {}, Currency: {}",
                    accountNumber, amount, currency, e);
            throw new DataException("Failed to store deposit in database " + e);
        }

    }

    private String generateReferenceNumber() {
        return "DEP" + System.currentTimeMillis() +
                String.format("%04d", ThreadLocalRandom.current().nextInt(10000));
    }

    private Deposit getDepositByReference(Connection conn, String referenceNumber) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_DEPOSIT)) {
            stmt.setString(1, referenceNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    Currency currency = new Currency();
                    currency.setId(rs.getInt("currency_id"));
                    currency.setName(rs.getString("currency_name"));
                    currency.setEquivalentToDollar(rs.getDouble("equivalent_to_dollar"));

                    Account account = new Account();
                    account.setId(rs.getInt("account_id"));
                    account.setNumber(rs.getString("account_number"));

                    Deposit deposit = new Deposit();
                    deposit.setId(rs.getInt("id"));
                    deposit.setReferenceNumber(rs.getString("reference_number"));
                    deposit.setMoney(rs.getDouble("money"));
                    deposit.setCreatedAt(rs.getDate("created_at"));
                    deposit.setCurrency(currency);
                    deposit.setOrigin(account);

                    return deposit;

                }
                throw new SQLException("Could not find created deposit");
            }
        }
    }
}
