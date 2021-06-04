package nl.novi.autogarage_roy_kersten.controller;

import nl.novi.autogarage_roy_kersten.model.Customer;
import nl.novi.autogarage_roy_kersten.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * De CustomerController Class zorgt ervoor dat HTTP Requests en Responses worden aangenomen en afgehandeld.
 *
 * GET request kan voor alle klanten tegelijk of per idCustomer worden opgevraagd.
 * path: "/customers" of  path:"/customers/{idCustomer}"
 *
 * POST request wordt per Customer object aangenomen, voorwaarde JSON moet gelijk zijn aan Customer Object, idcustomer wordt automatisch aangemaakt en met 1 opgehoogd
 *  * path: "/customers"
 *
 * DELETE request moet per idCustomer worden gedefinieerd.
 * path: "/customers/{idCustomer}"
 *
 * PUT request moet per idCustomer worden gedefinieerd.
 * path: "/customers/{idCustomer}"
 *
 *
 *  JSON with GET (all customers) and POST (add new customer)
 *  {
 *  "firstName": "Roy",
 *  "lastName": "Kersten",
 *  "phoneNumber": "0612345678",
 *  "email": "rkersten@gmail.nl"
 *  }
 *
 * JSON with GET (by ID), PUT (update Customer) and DELETE (delete Customer)
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
@RequestMapping(value = "/customers")                                                           //End point "/customer"
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping (value = "")
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
    public ResponseEntity<Object> getCustomerById(@PathVariable("idCustomer") int idCustomer) {
        Customer customer = customerService.getCustomerById(idCustomer);
        return ResponseEntity.ok(customer);
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


