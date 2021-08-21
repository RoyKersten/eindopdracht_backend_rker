package nl.novi.autogarage_roy_kersten.controller;

import nl.novi.autogarage_roy_kersten.model.Service;
import nl.novi.autogarage_roy_kersten.service.ServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
/**
 * The ServiceController class ensures that HTTP Requests en Responses are handled and processed further to the ServiceService interface.
 **/


public abstract class ServiceController {

    //Attributes
    private ServiceService serviceService;

    //Constructors
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    //Methods

    //Create a new Service
    @PostMapping(value = "")
    public ResponseEntity<Object> addService(@RequestBody Service service) {
        long newId = serviceService.addService(service);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idService}")
                .buildAndExpand(newId).toUri();
        return ResponseEntity.created(location).body(location);
    }

    //Get Service by idService
    @GetMapping("/{idService}")
    public ResponseEntity<Object> getServiceById(@PathVariable("idService") long idService) {
        Service service = serviceService.getServiceById(idService);
        return ResponseEntity.ok(service);
    }

    //Delete Service by idService
    @DeleteMapping("/{idService}")
    public ResponseEntity<Object> deleteServiceById(@PathVariable("idService") long idService) {
        serviceService.deleteServiceById(idService);
        return ResponseEntity.ok("service successfully deleted");
    }

    //Update Service by idService
    @PatchMapping("/status/{idService}")
    public ResponseEntity<Object> updateServiceStatusById(@PathVariable("idService") long idService, @RequestBody Service updateService) {
        serviceService.updateServiceStatusById(idService, updateService);
        return ResponseEntity.ok("update Service successfully");
    }

}
