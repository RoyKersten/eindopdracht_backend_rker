package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.model.Invoice;
import nl.novi.autogarage_roy_kersten.model.Repair;
import nl.novi.autogarage_roy_kersten.model.Service;
import nl.novi.autogarage_roy_kersten.model.ServiceStatus;
import nl.novi.autogarage_roy_kersten.repository.ServiceRepository;

import java.util.List;

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

        //if serviceStatus is not equal to "UITVOEREN", "VOLTOOID" or "NIET_UITVOEREN" throw bad exception
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
            throw new RecordNotFoundException();
        }
        return serviceRepository.findById(idService);
    }


    //Delete Service by idService
    @Override
    public void deleteServiceById(long idService) {
        if (!serviceRepository.existsById(idService)) {
            throw new BadRequestException();
        }
        serviceRepository.deleteById(idService);
    }


    @Override
    public void updateServiceStatusById(long idService, Service updateService) {

        if (!serviceRepository.existsById(idService)) {
            throw new BadRequestException();
        }

        Service storedService = serviceRepository.findById(idService);
        if (!(updateService.getServiceStatus().equals(ServiceStatus.UITVOEREN) || updateService.getServiceStatus().equals(ServiceStatus.VOLTOOID) || updateService.getServiceStatus().equals(ServiceStatus.NIET_UITVOEREN))) {     //Service can only have status "uitvoeren" , "voltooid" or "niet uitvoeren"
            throw new BadRequestException();
        }

        storedService.setServiceStatus(updateService.getServiceStatus());
        serviceRepository.save(storedService);
    }
}
