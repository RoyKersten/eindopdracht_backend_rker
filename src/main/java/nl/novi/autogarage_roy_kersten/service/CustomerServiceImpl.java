package nl.novi.autogarage_roy_kersten.service;


import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.model.Customer;
import nl.novi.autogarage_roy_kersten.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(long idCustomer) {
        if (!customerRepository.existsById(idCustomer)) {
            throw new RecordNotFoundException();
        }
        return customerRepository.findById(idCustomer);
    }

    @Override
    public long addCustomer(Customer customer) {
        Customer storedCustomer = customerRepository.save(customer);
        return storedCustomer.getIdCustomer();
    }


    @Override
    public void deleteCustomerById(long idCustomer) {
        if (!customerRepository.existsById(idCustomer)) {
            throw new BadRequestException();
        }
        customerRepository.deleteById(idCustomer);
    }


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
