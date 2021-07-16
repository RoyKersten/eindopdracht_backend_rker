package nl.novi.autogarage_roy_kersten.repository;

import nl.novi.autogarage_roy_kersten.model.Inspection;
import nl.novi.autogarage_roy_kersten.model.Repair;
import nl.novi.autogarage_roy_kersten.model.Service;
import nl.novi.autogarage_roy_kersten.model.ServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Long> {

    //Methods
    Repair findById(long idService);
    List<Repair> findByServiceStatus(ServiceStatus serviceStatus);
}
