package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.model.Inspection;
import nl.novi.autogarage_roy_kersten.model.ServiceStatus;
import nl.novi.autogarage_roy_kersten.repository.InspectionRepository;
import nl.novi.autogarage_roy_kersten.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The InspectionServiceImpl class implements the methods defined in the InspectionService Interface.
 * The InspectionServiceImpl class receives information via this interface from the InspectionController class, adds business logic and
 * communicates with the InspectionRepository interface.
 * */

@Service
public class InspectionServiceImpl extends ServiceServiceImpl implements InspectionService {

    //Attributes
    private InspectionRepository inspectionRepository;

    //Constructors
    @Autowired
    public InspectionServiceImpl(ServiceRepository serviceRepository, InspectionRepository inspectionRepository) {
        super(serviceRepository);
        this.inspectionRepository = inspectionRepository;
    }

    //Methods

    //Get all Services
    @Override
    public List<Inspection> getAllInspections() {
        return inspectionRepository.findAll();
    }

    //Get all Inspections with status "NIET_UITVOEREN"
    @Override
    @Transactional
    public List<Inspection> getInspectionByStatus(ServiceStatus serviceStatus) {
         return inspectionRepository.findByServiceStatus(serviceStatus);
    }

    //Update Inspection by idService
    //Update Inspection is a specific subclass to ensure the field IssuesFoundInspection is updated and differs from Repair
    @Override
    public void updateInspectionById(long idService, Inspection updateService) {
        if (!inspectionRepository.existsById(idService)) {
            throw new BadRequestException();
        }
        Inspection storedService = inspectionRepository.findById(idService);
        storedService.setServiceDate(updateService.getServiceDate());
        storedService.setServiceStatus(updateService.getServiceStatus());
        storedService.setIssuesFoundInspection(updateService.getIssuesFoundInspection());                               //addIssuesFoundInspection
        storedService.setCustomer(updateService.getCustomer());
        storedService.setCar(updateService.getCar());
        storedService.setServiceLine((updateService.getServiceLine()));
        inspectionRepository.save(updateService);
    }


}
