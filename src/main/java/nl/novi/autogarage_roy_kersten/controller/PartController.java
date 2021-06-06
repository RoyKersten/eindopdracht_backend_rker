package nl.novi.autogarage_roy_kersten.controller;

import nl.novi.autogarage_roy_kersten.model.Part;
import nl.novi.autogarage_roy_kersten.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * The PartController class ensures that HTTP Requests en Responses are handled and processed further to the PartService class.
 * <p>
 * GET request is for all parts possible or by idItem.
 * GET all parts use path: "/items/parts"
 * GET part by Id use path: "/items/parts/{idItem}"
 * <p>
 * POST request creates/adds a new Part, prerequisite is that JSON has to be equal to the Part definition in the Part class.
 * a new idItem will be generated automatically, the next sequence number will be taken.
 * POST (add new part) use path: "items/parts"
 * <p>
 * DELETE request will delete an existing part, the DELETE request must be executed by idItem.
 * DELETE part use path: "/parts/{idItem}"
 * <p>
 * PUT request will update part data, the PUT request must be executed by idItem.
 * path: "/items/parts/{idCar}"
 * <p>
 * <p>
 *
 *  JSON with GET (get all parts and POST (add new Part, automatically generate idItem)
 *  path: localhost:8080/items/parts
 *  {
 *      "itemName": "lichtmetalen velg 205/55/R16",
 *      "qty": 1,
 *      "price": 525.99,
 *      "itemCategory": "velgen",
 *      "brand": "volkswagen"
 *  }

 * <p>
 * JSON with GET (get by ID), PUT (update Part) and DELETE (delete Part) => idItem should be added in these cases!
 *
 * path: localhost:8080/items/parts/5
 *  {
 *      "idItem" : 5,
 *      "itemName": "lichtmetalen velg 205/55/R16",
 *      "qty": 1,
 *      "price": 525.99,
 *      "itemCategory": "velgen",
 *      "brand": "volkswagen"
 *  }
 *
 */


@RestController
@CrossOrigin
@RequestMapping(value = "/items/parts")
public class PartController {
    private PartService partService;

    @Autowired
    public PartController(PartService partService) {
        this.partService = partService;
    }

    //Methods

    //Create a new Part
    @PostMapping(value = "")
    public ResponseEntity<Object> addPart(@RequestBody Part part) {
        long newId = partService.addPart(part);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idItem}")
                .buildAndExpand(newId).toUri();
        return ResponseEntity.created(location).body(location);
    }

    //Get all Parts
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllParts() {
        return ResponseEntity.ok(partService.getAllParts());
    }


    //Get Part by idItem
    @GetMapping("/{idItem}")
    public ResponseEntity<Object> getPartById(@PathVariable("idItem") long idItem) {
        Part part = partService.getPartById(idItem);
        return ResponseEntity.ok(part);
    }


    //Delete Part by idItem
    @DeleteMapping("/{idItem}")
    public ResponseEntity<Object> deletePartById(@PathVariable("idItem") long idItem) {
        partService.deletePartById(idItem);
        return ResponseEntity.ok("item successfully deleted");
    }

    //Update Part by idItem
    @PutMapping("/{idItem}")
    public ResponseEntity<Object> updatePartById(@PathVariable("idItem") long idItem, @RequestBody Part updatePart) {
        partService.updatePartById(idItem, updatePart);
        return ResponseEntity.ok("update item successfully");
    }

}
