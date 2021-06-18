package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Car;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The CarService Interface defines the methods which should be implemented by the CarServiceImpl class
 * */

public interface CarService {

    //Methods
    List<Car> getAllCars();
    Car getCarById(long idCar);
    long addCar(Car car);
    void deleteCarById(long idCar);
    void updateCarById(long idCar, Car car);
}
