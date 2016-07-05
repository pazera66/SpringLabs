package savings.repository.impl;

import static org.joda.money.CurrencyUnit.EUR;
import static org.joda.time.DateTime.now;

import java.sql.*;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

import savings.model.AccountIncome;
import savings.model.PaybackConfirmation;
import savings.model.Purchase;
import savings.repository.PaybackRepository;

@Repository
public class JdbcPaybackRepository implements PaybackRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcPaybackRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PaybackConfirmation> findAllByAccountNumber(String accountNumber) {
        String sql =
                "select * " +
                "from PAYBACK " +
                "where ACCOUNT_NUMBER = ?";
        return jdbcTemplate.query(sql, paybackConfirmationMapper, accountNumber);
    }

    private static final RowMapper<PaybackConfirmation> paybackConfirmationMapper = new RowMapper<PaybackConfirmation>() {
        @Override
        public PaybackConfirmation mapRow(ResultSet resultSet, int i) throws SQLException {
            return new PaybackConfirmation(
                    resultSet.getString("NUMBER"),
                    new AccountIncome(
                            resultSet.getString("ACCOUNT_NUMBER"),
                            Money.of(EUR, resultSet.getBigDecimal("AMOUNT")),
                            Collections.<AccountIncome.Distribution>emptySet()));
        }
    };

    @Override
    public PaybackConfirmation save(AccountIncome income, Purchase purchase) {
        String sql =
                "insert into PAYBACK (NUMBER, AMOUNT, DATE, ACCOUNT_NUMBER, MERCHANT_NUMBER, TRANSACTION_AMOUNT, TRANSACTION_DATE) " +
                "values (?, ?, ?, ?, ?, ?, ?)";

        String number = nextConfirmationNumber();
        jdbcTemplate.update(sql,
                number,
                income.getAmount().getAmount(),
                new Date(now().toDate().getTime()),
                income.getAccountNumber(),
                purchase.getMerchantNumber(),
                purchase.getAmount().getAmount(),
                new Date(purchase.getDate().toDate().getTime()));

        return new PaybackConfirmation(number, income);
    }

    private String nextConfirmationNumber() {
        String sql = "select NEXTVAL('SEQ_PAYBACK_CONFIRMATION_NUMBER')";
        return jdbcTemplate.queryForObject(sql, String.class);
    }
}
