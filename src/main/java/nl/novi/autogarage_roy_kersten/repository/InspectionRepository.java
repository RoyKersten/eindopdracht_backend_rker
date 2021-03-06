package nl.novi.autogarage_roy_kersten.repository;

import nl.novi.autogarage_roy_kersten.model.Inspection;
import nl.novi.autogarage_roy_kersten.model.ServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Long> {

    //Methods
    Inspection findById(long idService);
    List<Inspection> findByServiceStatus (ServiceStatus serviceStatus);

}
