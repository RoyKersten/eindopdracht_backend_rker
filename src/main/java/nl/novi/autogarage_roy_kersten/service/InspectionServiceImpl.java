package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.model.Inspection;
import nl.novi.autogarage_roy_kersten.model.ServiceStatus;
import nl.novi.autogarage_roy_kersten.repository.InspectionRepository;
import nl.novi.autogarage_roy_kersten.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InspectionServiceImpl extends ServiceServiceImpl implements InspectionService {

    private InspectionRepository inspectionRepository;

    //Constructors
    @Autowired
    public InspectionServiceImpl(ServiceRepository serviceRepository, InspectionRepository inspectionRepository) {
        super(serviceRepository);
        this.inspectionRepository = inspectionRepository;
    }

    //Get all Services
    @Override
    public List<Inspection> getAllInspections() {
        return inspectionRepository.findAll();
    }

    //Get all Inspections with status "niet uitvoeren"
    @Override
    public List<Inspection> getInspectionByStatus(ServiceStatus serviceStatus) {
        return inspectionRepository.findByServiceStatus(ServiceStatus.NIET_UITVOEREN);
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
        storedService.setIssuesFoundInspection(updateService.getIssuesFoundInspection());    //Specific for Inspection
        storedService.setCustomer(updateService.getCustomer());
        storedService.setCar(updateService.getCar());
        storedService.setServiceLine((updateService.getServiceLine()));
        inspectionRepository.save(updateService);
    }


}
