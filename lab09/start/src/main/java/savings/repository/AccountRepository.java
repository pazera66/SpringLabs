package savings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import savings.model.Account;

// TODO #1: implement this repository to support basic CRUD operations as well as those defined below
public interface AccountRepository extends JpaRepository<Account, Long>{

    //void save(Account account);

    Account findByCreditCardsNumber(String creditCardNumber);

}
