package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.model.Repair;
import nl.novi.autogarage_roy_kersten.model.ServiceStatus;
import nl.novi.autogarage_roy_kersten.repository.RepairRepository;
import nl.novi.autogarage_roy_kersten.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairServiceImpl extends ServiceServiceImpl implements RepairService {

    private RepairRepository repairRepository;

    //Constructors
    @Autowired
    public RepairServiceImpl(ServiceRepository serviceRepository, RepairRepository repairRepository) {
        super(serviceRepository);
        this.repairRepository = repairRepository;
    }

    //Get all Repairs
    @Override
    public List<Repair> getAllRepairs() {
        return repairRepository.findAll();
    }

    //Get all Inspections with status "voltooid"
    @Override
    public List<Repair> getRepairByStatus(ServiceStatus serviceStatus) {
        return repairRepository.findByServiceStatus(ServiceStatus.VOLTOOID);
    }

    //Update Repair by idService
    //Update Repair is a specific subclass to ensure the field IssuesToRepair is updated and differs from Inspection
    @Override
    public void updateRepairById(long idService, Repair updateService) {

        if (!repairRepository.existsById(idService)) {
            throw new BadRequestException();

        }
        Repair storedService = repairRepository.findById(idService);
        storedService.setServiceDate(updateService.getServiceDate());
        storedService.setServiceStatus(updateService.getServiceStatus());
        storedService.setIssuesToRepair(updateService.getIssuesToRepair());                                             //Specific for Repair
        storedService.setCustomer(updateService.getCustomer());
        storedService.setCar(updateService.getCar());
        storedService.setServiceLine((updateService.getServiceLine()));
        repairRepository.save(updateService);
    }

}
