package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.ServiceLine;

import java.util.List;

public interface ServiceLineService {

    //Methods
    List<ServiceLine> getAllServiceLines();
    ServiceLine getServiceLineById(long idServiceLine);
    long addServiceLine(ServiceLine serviceLine);
    void deleteServiceLineById(long idServiceLine);
    void updateServiceLineById(long idServiceLine, ServiceLine serviceLine);

}
