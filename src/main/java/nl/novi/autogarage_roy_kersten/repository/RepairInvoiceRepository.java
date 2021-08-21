package nl.novi.autogarage_roy_kersten.repository;

import nl.novi.autogarage_roy_kersten.model.RepairInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairInvoiceRepository extends JpaRepository<RepairInvoice, Long> {

    //Methods
    RepairInvoice findById(long idInvoice);
}
