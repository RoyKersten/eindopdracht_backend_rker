package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Activity;
import nl.novi.autogarage_roy_kersten.model.ServiceLine;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ServiceLineService {

    //Methods
    List<ServiceLine> getAllServiceLines();
    ServiceLine getServiceLineById(long idServiceLine);
    long addServiceLine(ServiceLine serviceLine);
    void deleteServiceLineById(long idServiceLine);
    void updateServiceLineById(long idServiceLine, ServiceLine serviceLine);

}
