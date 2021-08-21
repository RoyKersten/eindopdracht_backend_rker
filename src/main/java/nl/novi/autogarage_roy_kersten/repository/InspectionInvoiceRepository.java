package nl.novi.autogarage_roy_kersten.repository;

import nl.novi.autogarage_roy_kersten.model.InspectionInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspectionInvoiceRepository extends JpaRepository<InspectionInvoice, Long> {

      InspectionInvoice findById(long idInvoice);
}
