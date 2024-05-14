package ma.emsi.ebanking.repositories;

import ma.emsi.ebanking.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepos extends JpaRepository<Customer,Long>
{

}
