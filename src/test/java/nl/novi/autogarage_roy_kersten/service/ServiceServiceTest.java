package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.repository.ItemRepository;
import nl.novi.autogarage_roy_kersten.repository.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
abstract class ServiceServiceTest {

    @Mock
    ServiceRepository serviceRepository;

    @Test
    void addService() {}

    @Test
    void getServiceById() {}

    @Test
    void deleteServiceById() {}

    @Test
    void updateServiceStatusById() {}

}
