package nl.novi.autogarage_roy_kersten.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("inspection")
public class Inspection extends Service {

    //attributen
    @Column(name = "issues_found_inspection")
    private String issuesFoundInspection;

    //constructor
    public Inspection () {}

    public Inspection(int idService, Date serviceDate, String serviceStatus, Customer customer, List<ServiceLine> serviceLine, String issuesFoundInspection, Car car) {
        super(idService, serviceDate, serviceStatus, customer, serviceLine, car);
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
