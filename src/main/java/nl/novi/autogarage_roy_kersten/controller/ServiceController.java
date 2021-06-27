package nl.novi.autogarage_roy_kersten.controller;

import nl.novi.autogarage_roy_kersten.model.Repair;
import nl.novi.autogarage_roy_kersten.model.Service;
import nl.novi.autogarage_roy_kersten.service.ServiceServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * POST Inspection:
 * {
 * "@type": "inspection",
 * "serviceDate": "2021-06-12",
 * "serviceStatus": "uitvoeren",
 * "issuesFoundInspection": "ruit vervangen",
 * "car": {
 * "idCar": 1
 * },
 * <p>
 * "customer": {
 * "idCustomer": 1
 * }
 * <p>
 * }
 * <p>
 * POST Repair
 * {
 * "@type": "repair",
 * "serviceDate": "2021-06-12",
 * "serviceStatus": "uitvoeren",
 * "issuesToRepair": "ruit vervangen",
 * "car": {
 * "idCar": 1
 * },
 * "customer": {
 * "idCustomer": 1
 * }
 * }
 */

public abstract class ServiceController {


    private ServiceServiceImpl serviceServiceImpl;


    public ServiceController(ServiceServiceImpl serviceServiceImpl) {
        this.serviceServiceImpl = serviceServiceImpl;
    }

    //Methods

    //Create a new Service
    @PostMapping(value = "")
    public ResponseEntity<Object> addService(@RequestBody Service service) {
        long newId = serviceServiceImpl.addService(service);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idService}")
                .buildAndExpand(newId).toUri();
        return ResponseEntity.created(location).body(location);
    }


    //Get Service by idService
    @GetMapping("/{idService}")
    public ResponseEntity<Object> getServiceById(@PathVariable("idService") long idService) {
        Service service = serviceServiceImpl.getServiceById(idService);
        return ResponseEntity.ok(service);
    }


    //Delete Service by idService
    @DeleteMapping("/{idService}")
    public ResponseEntity<Object> deleteActivityById(@PathVariable("idService") long idService) {
        serviceServiceImpl.deleteServiceById(idService);
        return ResponseEntity.ok("service successfully deleted");
    }




    //Update Service by idService
    @PutMapping("/status/{idService}")
    public ResponseEntity<Object> updateServiceStatusById(@PathVariable("idService") long idService, @RequestBody Service updateService) {
        serviceServiceImpl.updateServiceStatusById(idService, updateService);
        return ResponseEntity.ok("update Service successfully");
    }

}
