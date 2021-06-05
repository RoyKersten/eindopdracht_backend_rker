package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Customer;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * The CustomerService Interface defines the methods which should be implemented by the CustomerServiceImpl class
* */

@Service
public interface CustomerService {

    //Methods
    List<Customer> getAllCustomers();
    Customer getCustomerById (long idCustomer);
    long addCustomer (Customer customer);
    void deleteCustomerById(long idCustomer);
    void updateCustomerById(long idCustomer, Customer customer);



}
