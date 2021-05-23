package nl.novi.autogarage_roy_kersten;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("customer")
public class CustomerController {


    Customer customer = new Customer();                                                 // waarschijnlijk wel nodig als de info in de database staat ipv de List
    List<Customer> customers = new ArrayList<>();                                       // waarschijnlijk niet nodig als info in de database staat


    @GetMapping()
    public List<Customer> getCustomer() {
        return customers;                                                               // return all customers in the List
    }

    @PostMapping()
    public void postCustomer(@RequestBody Customer customer) {
      customers.add(customer);
    }

    @DeleteMapping("{idCustomer}")
    public void deleteCustomer(@PathVariable("idCustomer") int idCustomer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getIdCustomer() == idCustomer) {
                this.customers.remove(i);
            }


        }
    }
}
