package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Inspection;
import nl.novi.autogarage_roy_kersten.model.ServiceStatus;

import java.util.List;

public interface InspectionService {

    //Methods
    List<Inspection> getAllInspections();
    List<Inspection> getInspectionByStatus(ServiceStatus serviceStatus);
    void updateInspectionById(long idService, Inspection inspection);

}
