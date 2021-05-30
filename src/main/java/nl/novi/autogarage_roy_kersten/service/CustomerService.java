package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Customer;

import java.util.List;

public interface CustomerService {


    List<Customer> getAllCustomer ();
    Customer getCustomerById (int idCustomer);
    Customer addCustomer (Customer customer);
    void deleteCustomerById(int idCustomer);
    void updateCustomerById(int idCustomer, Customer customer);



}
