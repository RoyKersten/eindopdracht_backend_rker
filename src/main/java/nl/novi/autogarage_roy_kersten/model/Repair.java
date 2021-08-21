package nl.novi.autogarage_roy_kersten.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("repair")
public class Repair extends Service {

    //attributes
    @Column (name = "issues_to_repair")
    private String issuesToRepair;

    //constructors
    public Repair () {}

    public Repair(Long idService, ServiceStatus serviceStatus) {
        super(idService, serviceStatus);
    }

    public Repair(Long idService, LocalDate serviceDate, ServiceStatus serviceStatus, Customer customer, String issuesToRepair, List<ServiceLine> serviceLine, Car car) {
        super(idService, serviceDate, serviceStatus, customer, serviceLine, car);
        this.issuesToRepair = issuesToRepair;
    }


    //getters and setters
    public String getIssuesToRepair() {
        return issuesToRepair;
    }

    public void setIssuesToRepair(String issuesToRepair) {
        this.issuesToRepair = issuesToRepair;
    }
}
