package nl.novi.autogarage_roy_kersten.controller;

import nl.novi.autogarage_roy_kersten.model.Inspection;
import nl.novi.autogarage_roy_kersten.service.InspectionServiceImpl;
import nl.novi.autogarage_roy_kersten.service.ServiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**

 *   PUT inspection
 *   localhost:8080/services/inspections/1
 *   {
 *      "idService": 1,
 *      "@type": "inspection",
 *      "serviceDate": "2021-06-20",
 *      "serviceStatus": "uitvoeren",
 *      "issuesFoundInspection": "wielen vervangen, luchtfilter vervangen",
 *      "car": {
 *          "idCar": 1
 *      },
 *      "customer": {
 *          "idCustomer": 1
 *      }
 *   }

 *
 */


@RestController
@CrossOrigin
@RequestMapping(value = "/services/inspections")
public class InspectionController extends ServiceController {

    private InspectionServiceImpl inspectionService;


    @Autowired
    public InspectionController(@Qualifier("inspectionServiceImpl") ServiceServiceImpl serviceServiceImpl, InspectionServiceImpl inspectionService) {
        super(serviceServiceImpl);
        this.inspectionService = inspectionService;
    }

    //Methods
    //Get all Inspections, need to be defined in subclass Inspection, path: "/services/inspections" should only show inspections, not repairs
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllInspections() {
        return ResponseEntity.ok(inspectionService.getAllInspections());
    }


//    Update inspection by idService (issuesFoundInspection)
    @PutMapping("/{idService}")
    public ResponseEntity<Object> updateInspectionById(@PathVariable("idService") long idService, @RequestBody Inspection updateService) {
        inspectionService.updateInspectionById(idService, updateService);
        return ResponseEntity.ok("update Inspection successfully");
    }
}