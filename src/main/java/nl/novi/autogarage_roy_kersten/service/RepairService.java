package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Inspection;
import nl.novi.autogarage_roy_kersten.model.Repair;

import java.util.List;

public interface RepairService extends ServiceService {

    List<Repair> getAllRepairs();
    void updateRepairById(long idService, Repair repair);
}
