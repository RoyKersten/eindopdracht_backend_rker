package nl.novi.autogarage_roy_kersten;

import java.util.Date;

public class Repair extends Service{

    //attributes
    private String issuesToRepair;

    //constructor
    public Repair(int idService, Date serviceDate, String serviceStatus, Customer customer, ServiceLine serviceLine, String issuesToRepair) {
        super(idService, serviceDate, serviceStatus, customer, serviceLine);
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
