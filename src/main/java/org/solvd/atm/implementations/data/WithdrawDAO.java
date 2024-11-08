package org.solvd.atm.implementations.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.atm.Account;
import org.solvd.atm.domain.atm.Currency;
import org.solvd.atm.domain.atm.Withdraw;
import org.solvd.atm.interfaces.data.IWithdrawDAO;
import org.solvd.atm.utils.database.implementations.HikariCPDataSource;
import org.solvd.atm.utils.exceptions.DataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class WithdrawDAO implements IWithdrawDAO {
    private static final Logger logger = LogManager.getLogger(WithdrawDAO.class);
    private final HikariCPDataSource dataSource;


    private static final String INSERT_WITHDRAW_USD =
            "INSERT INTO withdraws (reference_number, money, origin_account_id, currency_id, atm_id) " +
            "SELECT ?, ?, a.id, c.id " +
            "FROM accounts a, currencies c, atms x" +
            "WHERE a.number = ? AND c.name = ? AND x.serie_number = ?";

    private static final String UPDATE_BALANCE_CURRENCY =
            "UPDATE account_currencies ac " +
            "JOIN accounts a ON ac.account_id = a.id " +
            "JOIN currencies c ON ac.currency_id = c.id " +
            "SET ac.money = ac.money - ? " +
            "WHERE a.number = ? AND c.name = ?";

    private static final String SELECT_WITHDRAW =
            "SELECT w.id, w.reference_number, w.money, w.created_at, " +
            "c.id as currency_id, c.name as currency_name, c.equivalent_to_dollar, " +
            "a.id as account_id, a.number as account_number " +
            "FROM withdraws w " +
            "JOIN currencies c ON w.currency_id = c.id " +
            "JOIN accounts a ON w.origin_account_id = a.id " +
            "WHERE w.reference_number = ?";

    public WithdrawDAO(){
        this.dataSource = HikariCPDataSource.getInstance();
    }
    @Override
    public Withdraw doWithdraw(String accountOrigin, Double withdrawAmount, String currency,String atmSerial) {
        try (Connection conn = dataSource.getDataSource().getConnection()) {
            conn.setAutoCommit(false); // begin trans
            String referenceNumber = generateReferenceNumber("WHD");
            try {
                try (PreparedStatement statement = conn.prepareStatement(INSERT_WITHDRAW_USD)) {
                    statement.setString(1, referenceNumber);
                    statement.setDouble(2, withdrawAmount);
                    statement.setString(3, accountOrigin);
                    statement.setString(4, currency);
                    statement.setString(5, atmSerial);
                    int updatedRows = statement.executeUpdate();
                    if (updatedRows != 1) {
                        throw new DataException("Failed to insert withdraw record");
                    }
                }

                // Update account balance in original currency
                try (PreparedStatement statement = conn.prepareStatement(UPDATE_BALANCE_CURRENCY)) {
                    statement.setDouble(1, withdrawAmount);
                    statement.setString(2, accountOrigin);
                    statement.setString(3, currency);
                    int updatedRows = statement.executeUpdate();
                    if (updatedRows != 1) {
                        throw new DataException("Failed to update account balance");
                    }
                }

                Withdraw withdraw = getWithdrawByReference(conn, referenceNumber);

                conn.commit();
                return withdraw;

            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    logger.error("Error rolling back transaction", rollbackEx);
                }
                throw e;
            }

        } catch (SQLException e) {
            logger.error("Error storing withdraw in database - Account: {}, Amount: {}, Currency: {}",
                    accountOrigin, withdrawAmount, currency, e);
            throw new DataException("Failed to store withdraw in database "+ e);
        }
    }

    private String generateReferenceNumber(String code) {
        return code + System.currentTimeMillis() +
                String.format("%04d", ThreadLocalRandom.current().nextInt(10000));
    }

    private Withdraw getWithdrawByReference(Connection conn, String referenceNumber) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_WITHDRAW)) {
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

                    Withdraw withdraw = new Withdraw();
                    withdraw.setId(rs.getInt("id"));
                    withdraw.setReferenceNumber(rs.getString("reference_number"));
                    withdraw.setMoney(rs.getDouble("money"));
                    withdraw.setCreatedAt(rs.getDate("created_at"));
                    withdraw.setCurrency(currency);
                    withdraw.setOrigin(account);

                    return withdraw;
                }
                throw new SQLException("Could not find created deposit");
            }
        }
    }
}
