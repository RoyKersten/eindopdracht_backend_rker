package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.model.Customer;
import nl.novi.autogarage_roy_kersten.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The CustomerServiceImpl class implements the methods defined in the CustomerService Interface.
 * The CustomerServiceImpl class receives information via this interface from the CustomerController class, adds business logic and
 * communicates with the CustomerRepository interface.
 * */

@Service
public class CustomerServiceImpl implements CustomerService {

    //Attributes
    private CustomerRepository customerRepository;

    //Constructors
    @Autowired
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
            throw new RecordNotFoundException("customer id does not exists");
        }
        return customerRepository.findById(idCustomer);
    }

    //Delete Customer by idCustomer
    @Override
    public void deleteCustomerById(long idCustomer) {
        if (!customerRepository.existsById(idCustomer)) {
            throw new BadRequestException("customer id does not exists");
        }
        try {
            customerRepository.deleteById(idCustomer);
        } catch (Exception exception) {
                throw new BadRequestException("customer cannot be deleted, most likely customer is used in earlier inspection and/or repair service");
         }
    }

    //Update customer by idCustomer
    @Override
    public void updateCustomerById(long idCustomer, Customer updateCustomer) {
        if (!customerRepository.existsById(idCustomer)) {
            throw new BadRequestException("customer id does not exists");
        }
        Customer storedCustomer = customerRepository.findById(idCustomer);
        storedCustomer.setFirstName(updateCustomer.getFirstName());
        storedCustomer.setLastName(updateCustomer.getLastName());
        storedCustomer.setPhoneNumber(updateCustomer.getPhoneNumber());
        storedCustomer.setEmail(updateCustomer.getEmail());
        customerRepository.save(updateCustomer);
    }
}
