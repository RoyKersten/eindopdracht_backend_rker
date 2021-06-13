package nl.novi.autogarage_roy_kersten.controller;

import nl.novi.autogarage_roy_kersten.model.Activity;
import nl.novi.autogarage_roy_kersten.service.ActivityService;
import nl.novi.autogarage_roy_kersten.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * The ActivityController class ensures that HTTP Requests en Responses are handled and processed further to the ActivityService class.
 * <p>
 * GET request is for all activities possible or by idItem.
 * GET all activities use path: "/items/activities"
 * GET activity by Id use path: "/items/activities/{idItem}"
 * <p>
 * POST request creates/adds a new Activity, prerequisite is that JSON has to be equal to the activity definition in the Activity class.
 * a new idItem will be generated automatically, the next sequence number will be taken.
 * POST (add new activity) use path: "items/activities"
 * <p>
 * DELETE request will delete an existing acticity, the DELETE request must be executed by idItem.
 * DELETE Activity use path: "/activities/{idItem}"
 * <p>
 * PUT request will update activity data, the PUT request must be executed by idItem.
 * path: "/items/activities/{idCar}"
 * <p>
 * <p>
 *
 *  JSON with GET (get all acticities and POST (add new Activity, automatically generate idItem)
 *  path: localhost:8080/items/acticities
 *  {
 *      "itemName": "vervangen remschijven voor",
 *      "qty": 1,
 *      "price": 40.00,
 *      "itemCategory": "vervangen remmen"
 *  }

 * <p>
 * JSON with GET (get by ID), PUT (update Activity) and DELETE (delete Activity) => idItem should be added in these cases!
 *
 * path: localhost:8080/items/activities/5
 *  {
 *      "idItem" : 5,
 *      "itemName": "vervangen remschijven achter",
 *      "qty": 1,
 *      "price": 50.00,
 *      "itemCategory": "vervangen remmen"
 *
 *  }
 */

@RestController
@CrossOrigin
@RequestMapping(value = "/items/activities")
public class ActivityController extends ItemController {

    private ActivityService activityService;

    @Autowired
    public ActivityController(@Qualifier("activityService") ItemService itemService, ActivityService activityService) {
        super(itemService);
        this.activityService = activityService;
    }

    //Methods

    //Update Activity by idItem
    @PutMapping("/{idItem}")
    public ResponseEntity<Object> updateActivityById(@PathVariable("idItem") long idItem, @RequestBody Activity updateActivity) {
        activityService.updateActivityById(idItem, updateActivity);
        return ResponseEntity.ok("update item successfully");
    }

}
