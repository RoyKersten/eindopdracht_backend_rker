package nl.novi.autogarage_roy_kersten.repository;


import nl.novi.autogarage_roy_kersten.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Activity findById(long idItem);
}
