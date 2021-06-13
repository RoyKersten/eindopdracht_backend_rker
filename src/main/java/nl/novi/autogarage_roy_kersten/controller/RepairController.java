package nl.novi.autogarage_roy_kersten.controller;

import nl.novi.autogarage_roy_kersten.model.Repair;
import nl.novi.autogarage_roy_kersten.model.Service;
import nl.novi.autogarage_roy_kersten.service.RepairService;
import nl.novi.autogarage_roy_kersten.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * PUT repair
 * localhost:8080/services/repairs/2
 *
 * {
 *     "idService": 2,
 *     "@type": "repair",
 *     "serviceDate": "2021-06-12",
 *     "serviceStatus": "uitvoeren",
 *     "issuesToRepair": "ruit vervangen",
 *     "car": {
 *         "idCar": 1
 *     },
 *     "customer": {
 *         "idCustomer": 1
 *     }
 *  }
 *
 * */


@RestController
@CrossOrigin
@RequestMapping(value = "/services/repairs")
public class RepairController extends ServiceController {


    private RepairService repairService;


    @Autowired
    public RepairController(@Qualifier("repairService") ServiceService serviceService, RepairService repairService) {
        super(serviceService);
        this.repairService = repairService;
    }

    //Update Service by idService
    @PutMapping("/{idService}")
    public ResponseEntity<Object> updateRepairById(@PathVariable("idService") long idService, @RequestBody Repair updateService) {
        repairService.updateRepairById(idService, updateService);
        return ResponseEntity.ok("update Repair successfully");
    }


}
