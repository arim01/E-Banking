package ma.emsi.ebanking.repositories;

import ma.emsi.ebanking.entities.AccountOperations;
import ma.emsi.ebanking.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationsRepos extends JpaRepository<AccountOperations,Long>
{
  List<AccountOperations> findByBankAccountId(String id);

  Page<AccountOperations> findByBankAccountId(String id, Pageable pageable);

}
