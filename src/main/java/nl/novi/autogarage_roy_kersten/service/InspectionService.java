package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.model.Inspection;
import nl.novi.autogarage_roy_kersten.repository.InspectionRepository;
import nl.novi.autogarage_roy_kersten.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InspectionService extends ServiceService {

    private InspectionRepository inspectionRepository;

    //Constructors
    @Autowired
    public InspectionService(ServiceRepository serviceRepository, InspectionRepository inspectionRepository) {
        super(serviceRepository);
        this.inspectionRepository = inspectionRepository;
    }

    //Update Inspection by idService
    //Update Inspection is a specific subclass to ensure the field IssuesFoundInspection is updated and differs from Repair
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
