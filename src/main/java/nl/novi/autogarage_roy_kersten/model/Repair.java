package nl.novi.autogarage_roy_kersten.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("repair")
public class Repair extends Service {

    //attributes
    @Column (name = "issues_to_repair")
    private String issuesToRepair;

    //constructor
    public Repair () {}

    public Repair(int idService, Date serviceDate, String serviceStatus, Customer customer, List<ServiceLine> serviceLine, String issuesToRepair, Car car) {
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
