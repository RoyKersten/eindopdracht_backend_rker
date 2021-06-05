package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.model.Customer;
import nl.novi.autogarage_roy_kersten.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * The CustomerServiceImpl class implements the methods defined in the CustomerService Interface and is an intermediate
 * class between the CustomerController class and CustomerRepository class.
 * The CustomerServiceImpl class receives information from the CustomerController class, adds business logic and
 * communicates with / provides information for the CustomerRepository class.
 * <p>
 * In the CustomerServiceImpl class the business logic code is written.
 * Business Logic:
 */

@Service
public class CustomerServiceImpl implements CustomerService {

    //Attributes
    private CustomerRepository customerRepository;

    @Autowired
    //Constructors
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //Methods

    //Create a new Customer
    @Override
    public long addCustomer(Customer customer) {
        Customer storedCustomer = customerRepository.save(customer);
        return storedCustomer.getIdCustomer();
    }

    //Get all Customers
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    //Get customer by idCustomer
    @Override
    public Customer getCustomerById(long idCustomer) {
        if (!customerRepository.existsById(idCustomer)) {
            throw new RecordNotFoundException();
        }
        return customerRepository.findById(idCustomer);
    }


    //Delete Customer by idCustomer
    @Override
    public void deleteCustomerById(long idCustomer) {
        if (!customerRepository.existsById(idCustomer)) {
            throw new BadRequestException();
        }
        customerRepository.deleteById(idCustomer);
    }


    //Update customer by idCustomer
    @Override
    public void updateCustomerById(long idCustomer, Customer updateCustomer) {

        if (!customerRepository.existsById(idCustomer)) {
            throw new BadRequestException();

        }
        Customer storedCustomer = customerRepository.findById(idCustomer);
        storedCustomer.setFirstName(updateCustomer.getFirstName());
        storedCustomer.setLastName(updateCustomer.getLastName());
        storedCustomer.setPhoneNumber(updateCustomer.getPhoneNumber());
        storedCustomer.setEmail(updateCustomer.getEmail());
        customerRepository.save(updateCustomer);
    }
}
