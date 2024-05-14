package ma.emsi.ebanking.repositories;

import ma.emsi.ebanking.entities.AccountOperations;
import ma.emsi.ebanking.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationsRepos extends JpaRepository<AccountOperations,Long>
{

}
