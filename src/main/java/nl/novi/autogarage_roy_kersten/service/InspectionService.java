package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Inspection;
import nl.novi.autogarage_roy_kersten.model.Service;

import java.util.List;

public interface InspectionService extends ServiceService{


    List<Inspection> getAllInspections();
    void updateInspectionById(long idService, Inspection inspection);

}
