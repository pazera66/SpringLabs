package savings.repository;

import java.util.List;

import savings.model.AccountIncome;
import savings.model.PaybackConfirmation;
import savings.model.Purchase;

public interface PaybackRepository {

    List<PaybackConfirmation> findAllByAccountNumber(String accountNumber);

    PaybackConfirmation save(AccountIncome income, Purchase purchase);
}
