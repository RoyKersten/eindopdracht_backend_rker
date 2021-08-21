package nl.novi.autogarage_roy_kersten.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.novi.autogarage_roy_kersten.model.Service;
import nl.novi.autogarage_roy_kersten.model.ServiceStatus;

public class CallListDto {

    @JsonSerialize
    private long idCustomer;

    @JsonSerialize
    private long idService;

    @JsonSerialize
    private ServiceStatus serviceStatus;

    @JsonSerialize
    private String firstName;

    @JsonSerialize
    private String lastName;

    @JsonSerialize
    private String phoneNumber;

    public static CallListDto fromService(Service service) {
        var dto = new CallListDto();
        dto.idCustomer = service.getCustomer().getIdCustomer();
        dto.idService = service.getIdService();
        dto.serviceStatus = service.getServiceStatus();
        dto.firstName = service.getCustomer().getFirstName();
        dto.lastName = service.getCustomer().getLastName();
        dto.phoneNumber = service.getCustomer().getPhoneNumber();
        return dto;
    }

}
