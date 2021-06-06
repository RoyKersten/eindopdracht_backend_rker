package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.model.Car;
import nl.novi.autogarage_roy_kersten.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The CarServiceImpl class implements the methods defined in the CarService Interface and is an intermediate
 * class between the CarController class and CarRepository class.
 * The CarServiceImpl class receives information from the CarController class, adds business logic and
 * communicates with / provides information for the CarRepository class.
 * <p>
 * In the CarServiceImpl class the business logic code is written.
 * Business Logic:
 */

@Service
public class CarServiceImpl implements CarService{
    //Attributes
    private CarRepository carRepository;

    @Autowired
    //Constructors
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    //Methods

    //Create a new Car
    @Override
    public long addCar(Car car) {
        Car storedCar = carRepository.save(car);
        return storedCar.getIdCar();
    }

    //Get all Cars
    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    //Get car by idCar
    @Override
    public Car getCarById(long idCar) {
        if (!carRepository.existsById(idCar)) {
            throw new RecordNotFoundException();
        }
        return carRepository.findById(idCar);
    }


    //Delete Car by idCar
    @Override
    public void deleteCarById(long idCar) {
        if (!carRepository.existsById(idCar)) {
            throw new BadRequestException();
        }
        carRepository.deleteById(idCar);
    }


    //Update car by idCar
    @Override
    public void updateCarById(long idCar, Car updateCar) {

        if (!carRepository.existsById(idCar)) {
            throw new BadRequestException();

        }
        Car storedCar = carRepository.findById(idCar);
        storedCar.setBrand(updateCar.getBrand());
        storedCar.setModel(updateCar.getModel());
        storedCar.setYearOfConstruction(updateCar.getYearOfConstruction());
        storedCar.setLicensePlateNumber(updateCar.getLicensePlateNumber());
        storedCar.setCustomer(updateCar.getCustomer());                                         //TO BE CHECKED !!!!!!!!
        carRepository.saveAndFlush(updateCar);
    }

}
