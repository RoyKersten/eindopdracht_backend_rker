package nl.novi.autogarage_roy_kersten.repository;


import nl.novi.autogarage_roy_kersten.model.Inspection;
import nl.novi.autogarage_roy_kersten.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The PartRepository interface takes care for the communication with the database and extends the JpaRepository Class
 * which contains standard repository methods. In this PartRepository class custom repository methods can be created /
 * defined which can be used in the PartServiceImpl class.
 */


@Repository
public interface PartRepository extends JpaRepository<Part, Long>{

    Part findById(long idItem);
}
