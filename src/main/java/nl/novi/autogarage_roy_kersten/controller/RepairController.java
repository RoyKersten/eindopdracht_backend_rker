package nl.novi.autogarage_roy_kersten.controller;

import nl.novi.autogarage_roy_kersten.model.Repair;
import nl.novi.autogarage_roy_kersten.service.RepairServiceImpl;
import nl.novi.autogarage_roy_kersten.service.ServiceServiceImpl;
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


    private RepairServiceImpl repairService;


    @Autowired
    public RepairController(@Qualifier("repairServiceImpl") ServiceServiceImpl serviceServiceImpl, RepairServiceImpl repairService) {
        super(serviceServiceImpl);
        this.repairService = repairService;
    }

    //Methods
    //Get all Repairs, need to be defined in subclass Repair, path: "/services/repairs" should only show repairs, not inspections
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllRepairs() {
        return ResponseEntity.ok(repairService.getAllRepairs());
    }


    //Update Service by idService (issuesToRepair), specific for Repair needs to be defined in subclass
    @PutMapping("/{idService}")
    public ResponseEntity<Object> updateRepairById(@PathVariable("idService") long idService, @RequestBody Repair updateService) {
        repairService.updateRepairById(idService, updateService);
        return ResponseEntity.ok("update Repair successfully");
    }


}
