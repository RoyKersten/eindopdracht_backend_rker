package nl.novi.autogarage_roy_kersten.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("inspection")
public class Inspection extends Service {

    //attributes
    @Column(name = "issues_found_inspection")
    private String issuesFoundInspection;

    //constructors
    public Inspection () {}

    public Inspection(Long idService, ServiceStatus serviceStatus) {
        super(idService, serviceStatus);
    }

    public Inspection(Long idService, LocalDate serviceDate, ServiceStatus serviceStatus, Customer customer, String issuesFoundInspection, List<ServiceLine> serviceLine, Car car) {
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
