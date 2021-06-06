package nl.novi.autogarage_roy_kersten.repository;

import nl.novi.autogarage_roy_kersten.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The CarRepository interface takes care for the communication with the database and extends the JpaRepository Class
 * which contains standard repository methods. In this CarRepository class custom repository methods can be created /
 * defined which can be used in the CarServiceImpl class.
 */

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    //Methods
    Car findById(long idCar);
}
