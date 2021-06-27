package nl.novi.autogarage_roy_kersten.controller;


import nl.novi.autogarage_roy_kersten.model.ServiceLine;
import nl.novi.autogarage_roy_kersten.service.ServiceLineService;
import nl.novi.autogarage_roy_kersten.service.ServiceLineServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 *POST: localhost:8080/servicelines/
 *  {
 * "serviceLineNumber": 10,
 * "qty": 1,
 * "price": 45.0,
 * "lineTotal": 45.0,
 * "itemName": "Roy",
 * "invoice": {
 *     "@type": "inspection_invoice",
 *     "idInvoice": 2
 * },
 * "item": {
 *     "@type": "activity",
 *     "idItem": 1
 * },
 * "service": {
 *     "@type": "inspection",
 *     "idService": 1
 * }
 *
 *
 *
 */



@RestController
@CrossOrigin
@RequestMapping(value = "/servicelines")
public class ServiceLineController {

    private ServiceLineService serviceLineService;


    public ServiceLineController(ServiceLineServiceImpl serviceLineService) {
        this.serviceLineService = serviceLineService;
    }

    //Methods

    //Create a new ServiceLine
    @PostMapping(value = "")
    public ResponseEntity<Object> addServiceLine(@RequestBody ServiceLine serviceLine) {
        long newId = serviceLineService.addServiceLine(serviceLine);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idServiceLine}")
                .buildAndExpand(newId).toUri();
        return ResponseEntity.created(location).body(location);
    }

    //Get all ServiceLines
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllServiceLines() {
        return ResponseEntity.ok(serviceLineService.getAllServiceLines());
    }


    //Get ServiceLine by idServiceLine
    @GetMapping("/{idServiceLine}")
    public ResponseEntity<Object> getServiceLineById(@PathVariable("idServiceLine") long idServiceLine) {
        ServiceLine serviceLine = serviceLineService.getServiceLineById(idServiceLine);
        return ResponseEntity.ok(serviceLine);
    }


    //Delete ServiceLine by idServiceLine
    @DeleteMapping("/{idServiceLine}")
    public ResponseEntity<Object> deleteServiceLineById(@PathVariable("idServiceLine") long idServiceLine) {
        serviceLineService.deleteServiceLineById(idServiceLine);
        return ResponseEntity.ok("serviceLine successfully deleted");
    }


    //   Update Inspection by idService
    @PutMapping("/{idServiceLine}")
    public ResponseEntity<Object> updateRepairById(@PathVariable("idServiceLine") long idServiceLine, @RequestBody ServiceLine updateServiceLine) {
        serviceLineService.updateServiceLineById(idServiceLine, updateServiceLine);
        return ResponseEntity.ok("update serviceLine successfully");
    }


    //calculate lineTotal ServiceLine
//    @PostMapping(value = "")
//    public ResponseEntity<Object> calculateLineTotal(@RequestBody ServiceLine serviceLine) {
//        long newId = serviceLineService.addServiceLine(serviceLine);
//        serviceLineService.calculateLineTotal(serviceLine);
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idServiceLine}")
//                .buildAndExpand(newId).toUri();
//        return ResponseEntity.created(location).body(location);
//    }


}
