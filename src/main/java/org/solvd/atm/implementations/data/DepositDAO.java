package org.solvd.atm.implementations.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.atm.Account;
import org.solvd.atm.domain.atm.Currency;
import org.solvd.atm.domain.atm.Deposit;
import org.solvd.atm.interfaces.data.IDepositDAO;
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
            "INSERT INTO deposits (reference_number, money, origin_account_id, currency_id, atm_id) " +
                    "VALUES (?, ?, (SELECT id FROM accounts WHERE number = ?), " +
                    "(SELECT id FROM currencies WHERE name = ?)," +
                    "(SELECT id FROM atms WHERE serie_number = ?)";

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


    String selectCurrencyByName = "select id,name from currencies where name = ?";
    String selectATMBySere = "select id,serie_number from atms where serie_number = ?";
    String insertW = "INSERT INTO deposits (reference_number, money, origin_account_id, currency_id, atm_id) VALUES (?,?,?,?,?)";
    String findAccountByNumberQuery = "select id,number from accounts where number = ?";

    public DepositDAO() {
        this.dataSource = HikariCPDataSource.getInstance();
    }


    @Override
    public Deposit deposit(String accountNumber, Double amount, String currency,String atmSerial) {

        String referenceNumber = generateReferenceNumber();

        try (Connection conn = dataSource.getDataSource().getConnection()) {
            conn.setAutoCommit(false); // begin trans

            try (PreparedStatement insertStmt = conn.prepareStatement(insertW);
                 PreparedStatement findCurrency = conn.prepareStatement(selectCurrencyByName);
                 PreparedStatement findATM = conn.prepareStatement(selectATMBySere);
                 PreparedStatement findAccount = conn.prepareStatement(findAccountByNumberQuery)) {

                findCurrency.setString(1,currency);
                findATM.setString(1,atmSerial);
                findAccount.setString(1,accountNumber);

                ResultSet r = findCurrency.executeQuery();

                int cur_id = 0,atm_id=0,acc1=0;

                if(r.next()){
                    cur_id = r.getInt("id");
                }

                ResultSet r1 = findATM.executeQuery();

                if(r1.next()){
                    atm_id = r1.getInt("id");
                }
                ResultSet r2 = findAccount.executeQuery();
                if(r2.next()){
                    acc1 = r2.getInt("id");
                }

                insertStmt.setString(1, referenceNumber);
                insertStmt.setDouble(2, amount);
                insertStmt.setInt(3, acc1);
                insertStmt.setInt(4, cur_id);
                insertStmt.setInt(5, atm_id);


                int insertResult = insertStmt.executeUpdate();
                if (insertResult != 1) {
                    throw new SQLException("Failed to insert deposit record");
                }

                PreparedStatement updateStmt = conn.prepareStatement(UPDATE_ACCOUNT_BALANCE);
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
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    logger.error("Error rolling back transaction", rollbackEx);
                }
                throw e;
            }

        } catch (SQLException e) {
            logger.error("Error storing deposit in database - Account: {}, Amount: {}, Currency: {}",
                    accountNumber, amount, currency, e);
            throw new DataException("Failed to store deposit in database "+ e);
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
