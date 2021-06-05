package nl.novi.autogarage_roy_kersten.repository;

import nl.novi.autogarage_roy_kersten.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The CustomerRepository interface takes care for the communication with the database and extends the JpaRepository Class
 * which contains standard repository methods. In this CustomerRepository class custom repository methods can be created /
 * defined which can be used in the CustomerServiceImpl class.
 */

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //Methods
    Customer findById(long idCustomer);

}
