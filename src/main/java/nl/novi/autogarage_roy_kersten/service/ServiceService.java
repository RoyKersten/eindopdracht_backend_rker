package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Customer;
import nl.novi.autogarage_roy_kersten.model.Service;

import java.util.List;

public interface ServiceService {

    //Methods
    Service getServiceById (long idService);
    long addService (Service service);
    void deleteServiceById(long idService);

}
