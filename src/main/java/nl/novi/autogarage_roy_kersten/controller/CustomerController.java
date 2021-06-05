package nl.novi.autogarage_roy_kersten.controller;

import nl.novi.autogarage_roy_kersten.model.Customer;
import nl.novi.autogarage_roy_kersten.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * The CustomerController class ensures that HTTP Requests en Responses are handled and processed further to the CustomerService class.
 * <p>
 * GET request is for all customers possible or by idCustomer.
 * GET all customers use path: "/customers"
 * GET customer by Id use path: "/customers/{idCustomer}"
 * <p>
 * POST request creates/adds a new Customer, prerequisite is that JSON has to be equal to the Customer definition in the Customer class.
 * a new idCustomer will be generated automatically, the next sequence number will be taken.
 * POST (add new customer) use path: "/customers"
 * <p>
 * DELETE request will delete an existing customer, the DELETE request must be executed by idCustomer.
 * DELETE custmer use path: "/customers/{idCustomer}"
 * <p>
 * PUT request will update Customer data, the PUT request must be executed by idCustomer.
 * path: "/customers/{idCustomer}"
 * <p>
 * <p>
 * JSON with GET (get all customers and POST (add new customer)
 * {
 * "firstName": "Roy",
 * "lastName": "Kersten",
 * "phoneNumber": "0612345678",
 * "email": "rkersten@gmail.nl"
 * }
 * <p>
 * JSON with GET (get by ID), PUT (update Customer) and DELETE (delete Customer) => idCustomer should be added !
 * {
 * "idCustomer": 1,
 * "firstName": "Roy",
 * "lastName": "Kersten",
 * "phoneNumber": "0612345678",
 * "email": "rkersten@gmail.nl"
 * }
 */


@RestController
@CrossOrigin
@RequestMapping(value = "/customers")
//End point "/customer"
public class CustomerController {

    //Attributes
    private CustomerService customerService;

    @Autowired
    //Constructors
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //Methods

    //Create a new Customer
    @PostMapping(value = "")
    public ResponseEntity<Object> addCustomer(@RequestBody Customer customer) {
        long newId = customerService.addCustomer(customer);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idCustomer}")
                .buildAndExpand(newId).toUri();
        return ResponseEntity.created(location).body(location);
    }

    //Get all Customers
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }


    //Get customer by idCustomer
    @GetMapping("/{idCustomer}")
    public ResponseEntity<Object> getCustomerById(@PathVariable("idCustomer") long idCustomer) {
        Customer customer = customerService.getCustomerById(idCustomer);
        return ResponseEntity.ok(customer);
    }


    //Delete Customer by idCustomer
    @DeleteMapping("/{idCustomer}")
    public ResponseEntity<Object> deleteCustomerById(@PathVariable("idCustomer") long idCustomer) {
        customerService.deleteCustomerById(idCustomer);
        return ResponseEntity.ok("customer successfully deleted");
    }

    //Update customer by idCustomer
    @PutMapping("/{idCustomer}")
    public ResponseEntity<Object> updateCustomerById(@PathVariable("idCustomer") long idCustomer, @RequestBody Customer updateCustomer) {
        customerService.updateCustomerById(idCustomer, updateCustomer);
        return ResponseEntity.ok("update customer successfully");
    }


}


