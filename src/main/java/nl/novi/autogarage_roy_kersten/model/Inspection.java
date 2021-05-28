package nl.novi.autogarage_roy_kersten.model;

import java.util.Date;

public class Inspection extends Service {

    //attributen
    private String issuesFoundInspection;

    //constructor

     public Inspection(int idService, Date serviceDate, String serviceStatus, Customer customer, ServiceLine serviceLine, String issuesFoundInspection) {
        super(idService, serviceDate, serviceStatus, customer, serviceLine);
        this.issuesFoundInspection = issuesFoundInspection;
    }

    //getters and setters


    public String getIssuesFoundInspection() {
        return issuesFoundInspection;
    }

    public void setIssuesFoundInspection(String issuesFoundInspection) {
        this.issuesFoundInspection = issuesFoundInspection;
    }
}
