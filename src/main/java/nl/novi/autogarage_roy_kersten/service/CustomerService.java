package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Customer;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CustomerService {

    List<Customer> getAllCustomers();
    Customer getCustomerById (long idCustomer);
    long addCustomer (Customer customer);
    void deleteCustomerById(long idCustomer);
    void updateCustomerById(long idCustomer, Customer customer);



}
