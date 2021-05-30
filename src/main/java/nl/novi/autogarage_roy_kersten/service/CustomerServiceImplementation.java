package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImplementation implements CustomerService {

    List<Customer> customers = new ArrayList<>();                                                                        //Later weghalen, info komt later uit de database

    @Override
    public List<Customer> getAllCustomer() {
        return customers;
    }

    @Override
    public Customer getCustomerById(int idCustomer) {
        Customer customer = null;
        for (int i = 0; i < customers.size(); i++) {
            if (idCustomer == customers.get(i).getIdCustomer()) {
                customer = customers.get(i);
            }
        }
        if (customer == null) {
            throw new RecordNotFoundException();


        }
        return customer;
    }


    @Override
    public Customer addCustomer(Customer customer) {
        customers.add(customer);
        return customer;
    }

    @Override
    public void deleteCustomerById(int idCustomer) {                                                                    //andere oplossing: customer is na removal altijd null, dus altijd throw new RecordException wordt aangesproken
        boolean customerRemoved = false;                                                                                //monitor if customer exists and removed
        for (int i = 0; i < customers.size(); i++) {
            if (idCustomer == customers.get(i).getIdCustomer()) {
                customers.remove(i);                                                                                    //customer exists and will be removed, custom message will be displayed
                customerRemoved = true;                                                                                 //customer existed and is removed
            }
        }
        if (customerRemoved == false) {
            throw new RecordNotFoundException();                                                                         //customer did not exist, HTTP 404 Not Found should be given as message
        }
    }

    @Override
    public void updateCustomerById(int idCustomer, Customer updateCustomer) {
        Customer customer = null;
        for (int i = 0; i < customers.size(); i++) {
            if (idCustomer == customers.get(i).getIdCustomer()) {
                customer = customers.get(i);
            }
        }
        if (customer == null) {
            throw new RecordNotFoundException();
        } else {
            customer.setFirstName(updateCustomer.getFirstName());
            customer.setLastName(updateCustomer.getLastName());
            customer.setPhoneNumber(updateCustomer.getPhoneNumber());
            customer.setEmail(updateCustomer.getEmail());

        }
    }
}
