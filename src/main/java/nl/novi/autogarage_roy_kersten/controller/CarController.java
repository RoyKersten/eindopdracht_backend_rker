package nl.novi.autogarage_roy_kersten.controller;


import nl.novi.autogarage_roy_kersten.model.Car;
import nl.novi.autogarage_roy_kersten.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * The CarController class ensures that HTTP Requests en Responses are handled and processed further to the CarService class.
 * <p>
 * GET request is for all cars possible or by idCar.
 * GET all cars use path: "/cars"
 * GET car by Id use path: "/cars/{idCar}"
 * <p>
 * POST request creates/adds a new Car, prerequisite is that JSON has to be equal to the Car definition in the Car class.
 * a new idCar will be generated automatically, the next sequence number will be taken.
 * POST (add new car) use path: "/cars"
 * <p>
 * DELETE request will delete an existing car, the DELETE request must be executed by idCar.
 * DELETE car use path: "/cars/{idCar}"
 * <p>
 * PUT request will update Car data, the PUT request must be executed by idCar.
 * path: "/cars/{idCar}"
 * <p>
 * <p>
 * JSON with GET (get all cars and POST (add new car)
 *
 *  Automatically generate idCar:
 *  localhost:8080/cars
 * {
 *      "brand": "Seat",
 *      "model": "Leon",
 *      "yearOfConstruction": "2021",
 *      "licensePlateNumber": "36-PTP-AR",
 *          "customer": {
 *          "idCustomer": 3
 *         }
 *  }

 * <p>
 * JSON with GET (get by ID), PUT (update Car) and DELETE (delete Car) => idCar should be added in these cases!
 *
 * localhost:8080/cars/5
 *  {
 *      "idCar": 5,
 *      "brand": "Seat",
 *      "model": "Altea",
 *      "yearOfConstruction": "2021",
 *      "licensePlateNumber": "36-PTP-AR",
 *      "customer": {
 *           "idCustomer": 4
 *       }
 *  }
 */

@RestController
@CrossOrigin
@RequestMapping(value = "/cars")                                    //End point "/car"
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    //Methods

    //Create a new Car
    @PostMapping(value = "")
    public ResponseEntity<Object> addCar(@RequestBody Car car) {
        long newId = carService.addCar(car);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idCar}")
                .buildAndExpand(newId).toUri();
        return ResponseEntity.created(location).body(location);
    }

    //Get all Cars
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }


    //Get customer by idCar
    @GetMapping("/{idCar}")
    public ResponseEntity<Object> getCarById(@PathVariable("idCar") long idCar) {
        Car car = carService.getCarById(idCar);
        return ResponseEntity.ok(car);
    }


    //Delete Customer by idCar
    @DeleteMapping("/{idCar}")
    public ResponseEntity<Object> deleteCarById(@PathVariable("idCar") long idCar) {
        carService.deleteCarById(idCar);
        return ResponseEntity.ok("car successfully deleted");
    }

    //Update car by idCar
    @PutMapping("/{idCar}")
    public ResponseEntity<Object> updateCarById(@PathVariable("idCar") long idCar, @RequestBody Car updateCar) {
        carService.updateCarById(idCar, updateCar);
        return ResponseEntity.ok("update car successfully");
    }


}
