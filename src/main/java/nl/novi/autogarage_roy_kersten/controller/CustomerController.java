package nl.novi.autogarage_roy_kersten.controller;

import nl.novi.autogarage_roy_kersten.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * De CustomerController Class zorgt ervoor dat HTTP Requests en Responses worden aangenomen en afgehandeld.
 *
 * GET request kan voor alle klanten tegelijk of per idCustomer worden opgevraagd.
 * path: "/customers" of  path:"/customers/{idCustomer}"
 *
 * POST request wordt per Customer object aangenomen, voorwaarde JSON moet gelijk zijn aan Customer Object.
 * path: "/customers"
 *
 * DELETE request moet per idCustomer worden gedefinieerd.
 * path: "/customers/{idCustomer}"
 *
 * PUT request moet per idCustomer worden gedefinieerd.
 * path: "/customers/{idCustomer}"
 *
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


    List<Customer> customers = new ArrayList<>();                                       // waarschijnlijk niet nodig als info in de database staat


    //Get all Customers
    @GetMapping()
    public ResponseEntity<Object> getAllCustomers() {
        return ResponseEntity.ok(customers);
    }


    //Get customer by idCustomer
    @GetMapping("/{idCustomer}")
    public ResponseEntity<Object> getCustomerById(@PathVariable("idCustomer") int idCustomer) {
        Customer customer = null;
        for (int i = 0; i < customers.size(); i++) {
            if (idCustomer == customers.get(i).getIdCustomer()) {
                customer = customers.get(i);
            }
        }
        if (customer == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(customer);
        }
    }

    //Add a new Customer
    @PostMapping()
    public ResponseEntity<Object> addCustomer(@RequestBody Customer customer) {
        customers.add(customer);
        return ResponseEntity.ok("customer added");
    }


    //Delete Customer by idCustomer
    @DeleteMapping("/{idCustomer}")
    public ResponseEntity<Object> deleteCustomerById(@PathVariable("idCustomer") int idCustomer) {
        Customer customer = null;
        for (int i = 0; i < customers.size(); i++) {
            if (idCustomer == customers.get(i).getIdCustomer()) {
                customers.remove(i);
                return ResponseEntity.ok("customer removed");
            }
        }
        if (customer == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    //Update customer by idCustomer
    @PutMapping("/{idCustomer}")
    public ResponseEntity<Object> updateCustomerById(@PathVariable("idCustomer") int idCustomer,@RequestBody Customer updateCustomer) {
        Customer customer = null;
        for (int i = 0; i < customers.size(); i++) {
            if (idCustomer == customers.get(i).getIdCustomer()) {
                customer = customers.get(i);
            }
        }
        if (customer == null) {
            return ResponseEntity.notFound().build();
        } else {
            customer.setFirstName(updateCustomer.getFirstName());
            customer.setLastName(updateCustomer.getLastName());
            customer.setPhoneNumber(updateCustomer.getPhoneNumber());
            customer.setEmail(updateCustomer.getEmail());
            return ResponseEntity.ok(customer);
        }
    }
}

