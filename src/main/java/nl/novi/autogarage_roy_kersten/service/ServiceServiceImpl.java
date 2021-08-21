package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.model.Service;
import nl.novi.autogarage_roy_kersten.model.ServiceStatus;
import nl.novi.autogarage_roy_kersten.repository.ServiceRepository;

/**
 * The ServiceServiceImpl class implements the methods defined in the ServiceService Interface.
 * The ServiceServiceImpl class receives information via this interface from the ServiceController class, adds business logic and
 * communicates with the ServiceRepository interface.
 * */

public abstract class ServiceServiceImpl implements ServiceService {

    //Attributes
    private ServiceRepository serviceRepository;

    //Constructors
    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    //Methods

    //Create a new Service
    @Override
    public long addService(Service service) {
        if (!(service.getServiceStatus().equals(ServiceStatus.UITVOEREN) || service.getServiceStatus().equals(ServiceStatus.VOLTOOID) || service.getServiceStatus().equals(ServiceStatus.NIET_UITVOEREN))) {     //Service can only have status "uitvoeren" , "voltooid" or "niet uitvoeren"
            throw new BadRequestException();
        }
        Service storedService = serviceRepository.save(service);
        return storedService.getIdService();
    }

    //Get Service by idService
    @Override
    public Service getServiceById(long idService) {
        if (!serviceRepository.existsById(idService)) {
            throw new RecordNotFoundException("service id does not exists");
        }
        return serviceRepository.findById(idService);
    }

    //Delete Service by idService
    @Override
    public void deleteServiceById(long idService) {
        if (!serviceRepository.existsById(idService)) {
            throw new BadRequestException("service id does not exists");
        }
        try {
            serviceRepository.deleteById(idService);
        } catch (Exception exception){
            throw new BadRequestException("service cannot be deleted, most likely service is invoiced already");
        }
    }

    //Update ServiceStatus by Id
    @Override
    public void updateServiceStatusById(long idService, Service updateService) {
        if (!serviceRepository.existsById(idService)) {
            throw new BadRequestException();
        }
        Service storedService = serviceRepository.findById(idService);
        if (!(updateService.getServiceStatus().equals(ServiceStatus.UITVOEREN) || updateService.getServiceStatus().equals(ServiceStatus.VOLTOOID) || updateService.getServiceStatus().equals(ServiceStatus.NIET_UITVOEREN))) {     //Service can only have status "uitvoeren" , "voltooid" or "niet uitvoeren"
            throw new BadRequestException("serviceStatus is not valid");
        }
        storedService.setServiceStatus(updateService.getServiceStatus());
        serviceRepository.save(storedService);
    }
}
