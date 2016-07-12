package savings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import savings.model.AccountIncome;
import savings.model.Payback;
import savings.model.PaybackConfirmation;
import savings.model.Purchase;

public interface PaybackRepositoryCustom {

    List<PaybackConfirmation> findByAccountNumber(String accountNumber);

    PaybackConfirmation save(AccountIncome income, Purchase purchase);

}
