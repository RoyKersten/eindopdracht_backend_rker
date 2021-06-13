package nl.novi.autogarage_roy_kersten.repository;


import nl.novi.autogarage_roy_kersten.model.Activity;
import nl.novi.autogarage_roy_kersten.model.Inspection;
import nl.novi.autogarage_roy_kersten.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Activity Repository interface takes care for the communication with the database and extends the JpaRepository Class
 * which contains standard repository methods. In this ActivityRepository class custom repository methods can be created /
 * defined which can be used in the ActivityServiceImpl class.
 */

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Activity findById(long idItem);
}
