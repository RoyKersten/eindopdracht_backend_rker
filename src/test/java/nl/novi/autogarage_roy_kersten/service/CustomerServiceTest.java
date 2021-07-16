package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Customer;
import nl.novi.autogarage_roy_kersten.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;


    @InjectMocks
    CustomerServiceImpl customerService;


    @Captor
    ArgumentCaptor<Customer> customerCaptor;


    @Test
    void addCustomerTest() {
        //Arrange => create customer as input for test
        Customer customer = new Customer(1L, "Karel", "Hoekstra", "+31612345678", "karel.hoekstra@mail.com");
        when(customerRepository.save(customer)).thenReturn(customer);

        //Act => call method addCustomer
        customerService.addCustomer(customer);

        //Assert => capture argument and perform assertions
        verify(customerRepository).save(customerCaptor.capture());
        Customer validateCustomer = customerCaptor.getValue();

        assertThat(validateCustomer.getIdCustomer()).isEqualTo(1L);
        assertThat(validateCustomer.getFirstName()).isEqualTo("Karel");
        assertThat(validateCustomer.getLastName()).isEqualTo("Hoekstra");
        assertThat(validateCustomer.getPhoneNumber()).isEqualTo("+31612345678");
        assertThat(validateCustomer.getEmail()).isEqualTo("karel.hoekstra@mail.com");
    }

    @Test
    void getAllCustomers() {
        //Arrange => create 3 customers as input for the test
        Customer customer1 = new Customer(1L, "Karel", "Hoekstra", "+31612345678", "karel.hoekstra@mail.com");
        Customer customer2 = new Customer(2L, "Henk", "Waal", "+31687654321", "henk.waal@mail.com");
        Customer customer3 = new Customer(3L, "Joep", "Janssen", "+31615345678", "joep.janssen@mail.com");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        when(customerRepository.findAll()).thenReturn(customers);


        //Act => call method getAllCustomers
        List<Customer> validateCustomers = customerService.getAllCustomers();

        //Assert => validate the return of method getAllCustomers and perform assertions
        assertThat(validateCustomers.size()).isEqualTo(3);                                                              //check if the size of the List is 3
        assertThat(validateCustomers.get(0).getFirstName()).isEqualTo("Karel");                                         //check if customer 1 has been added to the List correctly
        assertThat(validateCustomers.get(0).getLastName()).isEqualTo("Hoekstra");
        assertThat(validateCustomers.get(0).getPhoneNumber()).isEqualTo("+31612345678");
        assertThat(validateCustomers.get(0).getEmail()).isEqualTo("karel.hoekstra@mail.com");
    }


    @Test
    void getCustomerByIdTest() {
        //Arrange => create mock customer object as input for test
        Customer customer = new Customer(1L, "Karel", "Hoekstra", "+31612345678", "karel.hoekstra@mail.com");
        when(customerRepository.existsById(1L)).thenReturn(true);
        when(customerRepository.findById(1L)).thenReturn(customer);

        //Act => call method getCustomerById
        Customer validateCustomer = customerService.getCustomerById(1L);

        //Assert => validate return of method getCustomerById and perform assertions
        assertThat(validateCustomer.getFirstName()).isEqualTo("Karel");
        assertThat(validateCustomer.getLastName()).isEqualTo("Hoekstra");
        assertThat(validateCustomer.getPhoneNumber()).isEqualTo("+31612345678");
        assertThat(validateCustomer.getEmail()).isEqualTo("karel.hoekstra@mail.com");
    }

    @Test
    void deleteCustomerByIdTest() {
        //Arrange => check if idCustomer exists and return boolean true to pass BadRequestException check
        when(customerRepository.existsById(1L)).thenReturn(true);

        //Act => call method deleteCustomerById
        customerService.deleteCustomerById(1L);

        //Assert => verify if mock customerRepository.deleteById has been called one time
        verify(customerRepository, times(1)).deleteById(1L);

    }


    @Test
    void updateCustomerById() {

        //Arrange => create updateCustomer and storedCustomer object as input for test
        Customer updateCustomer = new Customer(1L, "Karel", "Hoekstra", "+31612345678", "karel.hoekstra@mail.com");
        Customer storedCustomer = new Customer(1L, "Henk", "Waal", "+31687654321", "henk.waal@mail.com");

        when(customerRepository.existsById(1L)).thenReturn(true);
        when(customerRepository.findById(1L)).thenReturn(storedCustomer);

        //Act => call method updateCustomerById
        customerService.updateCustomerById(1L, updateCustomer);

        //Assert => check if storedCustomer has been updated with values of updateCustomer
        assertThat(storedCustomer.getFirstName()).isEqualTo("Karel");
        assertThat(storedCustomer.getLastName()).isEqualTo("Hoekstra");
        assertThat(storedCustomer.getPhoneNumber()).isEqualTo("+31612345678");
        assertThat(storedCustomer.getEmail()).isEqualTo("karel.hoekstra@mail.com");

    }

}


