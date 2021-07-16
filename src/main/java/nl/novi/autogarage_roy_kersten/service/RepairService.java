package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Inspection;
import nl.novi.autogarage_roy_kersten.model.Repair;
import nl.novi.autogarage_roy_kersten.model.Service;
import nl.novi.autogarage_roy_kersten.model.ServiceStatus;

import java.util.List;

public interface RepairService extends ServiceService {

    List<Repair> getAllRepairs();
    List<Repair> getRepairByStatus(ServiceStatus serviceStatus);
    void updateRepairById(long idService, Repair repair);

}
