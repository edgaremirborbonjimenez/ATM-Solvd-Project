package org.solvd.atm.implementations.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solvd.atm.domain.Account;
import org.solvd.atm.domain.Currency;
import org.solvd.atm.domain.Transfer;
import org.solvd.atm.interfaces.data.ITransactionDAO;
import org.solvd.atm.utils.database.implementations.HikariCPDataSource;
import org.solvd.atm.utils.exceptions.DataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class TransactionDAO implements ITransactionDAO {
    private static final Logger logger = LogManager.getLogger(TransactionDAO.class);
    private HikariCPDataSource dataSource;

    private final static String INSERT_TRANSFER =
            "INSERT INTO transfers (reference_number, money, origin_account_id, destination_account_id, currency_id) " +
            "SELECT ?, ?, o.id, d.id, c.id " +
            "FROM accounts o, accounts d, currencies c " +
            "WHERE o.number = ? AND d.number = ? AND c.name = ?";


    private final static String UPDATE_ACCOUNT_BALANCE_DESTINATION =
            "UPDATE account_currencies ac " +
            "JOIN accounts a ON ac.account_id = a.id " +
            "JOIN currencies c ON ac.currency_id = c.id " +
            "SET ac.money = ac.money + ? " +
            "WHERE a.number = ? AND c.name = ?";


    private final static String UPDATE_ACCOUNT_BALANCE_ORIGIN =
            "UPDATE account_currencies ac " +
            "JOIN accounts a ON ac.account_id = a.id " +
            "JOIN currencies c ON ac.currency_id = c.id " +
            "SET ac.money = ac.money - ? " +
            "WHERE a.number = ? AND c.name = ?";

    private final static String SELECT_TRANSFER =
            "SELECT t.*, " +
            "o.number as origin_number, " +
            "d.number as destination_number, " +
            "c.name as currency_name, c.equivalent_to_dollar " +
            "FROM transfers t " +
            "JOIN accounts o ON t.origin_account_id = o.id " +
            "JOIN accounts d ON t.destination_account_id = d.id " +
            "JOIN currencies c ON t.currency_id = c.id " +
            "WHERE t.reference_number = ?";

    private final static String GET_FX_RATE =
            "SELECT name, equivalent_to_dollar FROM currencies WHERE name IN (?, ?)";


    public TransactionDAO() {
        this.dataSource = HikariCPDataSource.getInstance();
    }

    @Override
    public Transfer sendTransaction(String originAccount, String destinationAccount, String senderCurrency,
                                    String receiverCurrency, Double amount) {
        try (Connection conn = dataSource.getDataSource().getConnection()) {

            Map<String, Double> exchangeRates = new HashMap<>();
            String referenceNumber = generateReferenceNumber("TRF");
            double originalAmount;
            conn.setAutoCommit(false); // begin trans

            try{ // get the CURRENCY:FX_RATE pairs
                try (PreparedStatement statement = conn.prepareStatement(GET_FX_RATE)) {
                    statement.setString(1, senderCurrency);
                    statement.setString(2, receiverCurrency);
                    try (ResultSet rs = statement.executeQuery()) {
                        while (rs.next()) {
                            exchangeRates.put(
                                    rs.getString("name"),
                                    rs.getDouble("equivalent_to_dollar")
                            );
                        }
                    originalAmount = convertCurrency(amount,senderCurrency,receiverCurrency,exchangeRates);
                    }
                }

                try (PreparedStatement statement = conn.prepareStatement(INSERT_TRANSFER)) {
                    statement.setString(1, referenceNumber);
                    statement.setDouble(2, amount);
                    statement.setString(3, originAccount);
                    statement.setString(4, destinationAccount);
                    statement.setString(5, receiverCurrency);
                    statement.executeUpdate();
                }
                //update origin
                try (PreparedStatement statement = conn.prepareStatement(UPDATE_ACCOUNT_BALANCE_ORIGIN)) {
                    statement.setDouble(1, originalAmount);
                    statement.setString(2, originAccount);
                    statement.setString(3, senderCurrency);
                    int updatedRows = statement.executeUpdate();
                    if (updatedRows == 0) {
                        throw new DataException("Failed to update origin account balance");
                    }
                }
                //now reuse for destination
                try (PreparedStatement statement = conn.prepareStatement(UPDATE_ACCOUNT_BALANCE_DESTINATION)) {
                    statement.setDouble(1, amount);
                    statement.setString(2, destinationAccount);
                    statement.setString(3, receiverCurrency);
                    int updatedRows = statement.executeUpdate();
                    if (updatedRows == 0) {
                        throw new DataException("Failed to update destination account balance");
                    }
                }

                Transfer transfer = null;
                try (PreparedStatement statement = conn.prepareStatement(SELECT_TRANSFER)) {
                    statement.setString(1, referenceNumber);
                    try (ResultSet rs = statement.executeQuery()) {
                        if (rs.next()) {
                            transfer = createTransfer(rs);
                        }
                    }
                }

                conn.commit(); // end trans
                return transfer;
            }catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    logger.error("Error rolling back transaction", rollbackEx);
                }
                throw e;
            }

        } catch (SQLException e) {
            logger.error("Error generating the transaction");
            throw new DataException("Failed to generate the transaction "+e);
        }
    }

    private String generateReferenceNumber(String code) {
        return code + System.currentTimeMillis() +
                String.format("%04d", ThreadLocalRandom.current().nextInt(10000));
    }

    private Transfer createTransfer(ResultSet rs) throws SQLException {
        Transfer transfer = new Transfer();
        transfer.setId(rs.getInt("id"));
        transfer.setMoney(rs.getDouble("money"));
        transfer.setReferenceNumber(rs.getString("reference_number"));
        transfer.setCreatedAt(rs.getTimestamp("created_at"));

        Currency currency = new Currency();
        currency.setName(rs.getString("currency_name"));
        currency.setEquivalentToDollar(rs.getDouble("equivalent_to_dollar"));
        transfer.setCurrency(currency);

        Account originAccount = new Account();
        originAccount.setNumber(rs.getString("origin_number"));
        transfer.setOrigin(originAccount);

        Account destinationAccount = new Account();
        destinationAccount.setNumber(rs.getString("destination_number"));
        transfer.setDestination(destinationAccount);

        return transfer;
    }

    private double convertCurrency(Double amount, String senderCurrency,String receiverCurrency,Map<String,Double> rates){
        if (senderCurrency.equals(receiverCurrency)) {
            return amount;
        } else {
            double amountInUSD = amount / rates.get(receiverCurrency);
            return amountInUSD * rates.get(senderCurrency);
        }
    }
}
