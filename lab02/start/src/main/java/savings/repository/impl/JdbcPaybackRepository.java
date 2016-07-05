package savings.repository.impl;

import static org.joda.time.DateTime.now;

import java.sql.*;

import javax.sql.DataSource;

import savings.model.AccountIncome;
import savings.model.PaybackConfirmation;
import savings.model.Purchase;
import savings.repository.PaybackRepository;

public class JdbcPaybackRepository implements PaybackRepository {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public PaybackConfirmation save(AccountIncome income, Purchase purchase) {
        String sql =
                "insert into PAYBACK (NUMBER, AMOUNT, DATE, ACCOUNT_NUMBER, MERCHANT_NUMBER, TRANSACTION_AMOUNT, TRANSACTION_DATE) " +
                "values (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            String number = nextConfirmationNumber(connection);

            statement.setString(1, number);
            statement.setBigDecimal(2, income.getAmount().getAmount());
            statement.setDate(3, new Date(now().toDate().getTime()));
            statement.setString(4, income.getAccountNumber());
            statement.setString(5, purchase.getMerchantNumber());
            statement.setBigDecimal(6, purchase.getAmount().getAmount());
            statement.setDate(7, new Date(purchase.getDate().toDate().getTime()));

            return new PaybackConfirmation(number, income);
        } catch (SQLException e) {
            throw new RuntimeException("Error in save!", e);
        }
    }

    private String nextConfirmationNumber(Connection connection) throws SQLException {
        String sql = "select NEXTVAL('SEQ_PAYBACK_CONFIRMATION_NUMBER')";
        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getString(1);
        }
    }
}
