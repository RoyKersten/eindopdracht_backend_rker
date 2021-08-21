package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Customer;

import java.util.List;

public interface CustomerService {

    //Methods
    List<Customer> getAllCustomers();
    Customer getCustomerById (long idCustomer);
    long addCustomer (Customer customer);
    void deleteCustomerById(long idCustomer);
    void updateCustomerById(long idCustomer, Customer customer);



}
