package nl.novi.autogarage_roy_kersten.controller;

import nl.novi.autogarage_roy_kersten.model.Customer;
import nl.novi.autogarage_roy_kersten.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * De CustomerController Class zorgt ervoor dat HTTP Requests en Responses worden aangenomen en afgehandeld.
 * <p>
 * GET request kan voor alle klanten tegelijk of per idCustomer worden opgevraagd.
 * path: "/customers" of  path:"/customers/{idCustomer}"
 * <p>
 * POST request wordt per Customer object aangenomen, voorwaarde JSON moet gelijk zijn aan Customer Object.
 * path: "/customers"
 * <p>
 * DELETE request moet per idCustomer worden gedefinieerd.
 * path: "/customers/{idCustomer}"
 * <p>
 * PUT request moet per idCustomer worden gedefinieerd.
 * path: "/customers/{idCustomer}"
 * <p>
 * JSON:
 * {
 * "idCustomer": 1,
 * "firstName": "Roy",
 * "lastName": "Kersten",
 * "phoneNumber": "0612345678",
 * "email": "rkersten@gmail.nl"
 * }
 */


@RestController
@RequestMapping("/customers")                                                           //End point "/customer"
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    List<Customer> customers = new ArrayList<>(); //weghalen

    //Get all Customers
    @GetMapping()
    public ResponseEntity<Object> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomer());
    }


    //Get customer by idCustomer
    @GetMapping("/{idCustomer}")
    public ResponseEntity<Object> getCustomerById(@PathVariable("idCustomer") int idCustomer) {
        Customer customer = customerService.getCustomerById(idCustomer);
        return ResponseEntity.ok(customer);
    }

    //Add a new Customer
    @PostMapping()
    public ResponseEntity<Object> addCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
        return ResponseEntity.ok("customer added successfully");
    }


    //Delete Customer by idCustomer
    @DeleteMapping("/{idCustomer}")
    public ResponseEntity<Object> deleteCustomerById(@PathVariable("idCustomer") int idCustomer) {
        customerService.deleteCustomerById(idCustomer);
        return ResponseEntity.ok("delete customer successfully");
    }

    //Update customer by idCustomer
    @PutMapping("/{idCustomer}")
    public ResponseEntity<Object> updateCustomerById(@PathVariable("idCustomer") int idCustomer, @RequestBody Customer updateCustomer) {
        customerService.updateCustomerById(idCustomer, updateCustomer);
        return ResponseEntity.ok("update customer successfully");
    }
}


