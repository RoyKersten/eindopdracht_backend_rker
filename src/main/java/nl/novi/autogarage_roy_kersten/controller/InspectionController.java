package nl.novi.autogarage_roy_kersten.controller;

import nl.novi.autogarage_roy_kersten.model.Inspection;
import nl.novi.autogarage_roy_kersten.model.Service;
import nl.novi.autogarage_roy_kersten.service.InspectionService;
import nl.novi.autogarage_roy_kersten.service.ServiceService;
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

    private InspectionService inspectionService;


    @Autowired
    public InspectionController(@Qualifier("inspectionService") ServiceService serviceService, InspectionService inspectionService) {
        super(serviceService);
        this.inspectionService = inspectionService;
    }


//    Update inspection by idService (issuesFoundInspection)
    @PutMapping("/{idService}")
    public ResponseEntity<Object> updateInspectionById(@PathVariable("idService") long idService, @RequestBody Inspection updateService) {
        inspectionService.updateInspectionById(idService, updateService);
        return ResponseEntity.ok("update Inspection successfully");
    }
}
