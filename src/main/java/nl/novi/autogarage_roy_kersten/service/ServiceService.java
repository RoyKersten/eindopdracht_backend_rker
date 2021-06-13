package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.model.Service;
import nl.novi.autogarage_roy_kersten.repository.ServiceRepository;

import java.util.List;

public abstract class ServiceService {

    //Attributes
    private ServiceRepository serviceRepository;


    //Constructors
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    //Methods

    //Create a new Service
    public long addService(Service service) {
        Service storedService = serviceRepository.save(service);
        return storedService.getIdService();
    }

    //Get all Services
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    //Get Service by idService
    public Service getServiceById(long idService) {
        if (!serviceRepository.existsById(idService)) {
            throw new RecordNotFoundException();
        }
        return serviceRepository.findById(idService);
    }


    //Delete Service by idService
    public void deleteServiceById(long idService) {
        if (!serviceRepository.existsById(idService)) {
            throw new BadRequestException();
        }
        serviceRepository.deleteById(idService);
    }
}
