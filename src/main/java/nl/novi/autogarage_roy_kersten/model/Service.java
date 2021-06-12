package nl.novi.autogarage_roy_kersten.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)                   // Set Strategy SINGLE_TABLE => Create one table for all subclasses with a subclass type column to differentiate between subclasses
@DiscriminatorColumn(name = "service_type")
@Table(name = "service")
public abstract class Service {

    //attributen
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idService;

    @Column (name = "service_date")
    @Temporal(TemporalType.DATE)
    private Date serviceDate;             // Date format ????????

    @Column (name = "service_status")
    private String serviceStatus;           //zou ook uit een list geselecteerd kunnen worden, dan is het altijd een vaste omschrijving ??? !!!

    @OneToMany (mappedBy = "service")
    private List<ServiceLine> serviceLine;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Car car;

    @OneToOne
    private Invoice invoice;

    //constructor
    public Service() {}

    public Service(int idService, Date serviceDate, String serviceStatus, Customer customer, List<ServiceLine> serviceLine,Car car) {
        this.idService = idService;
        this.serviceDate = serviceDate;
        this.serviceStatus = serviceStatus;
        this.customer = customer;
        this.serviceLine = serviceLine;
        this.car = car;
    }


    //getters and setters

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<ServiceLine> getServiceLine() {
        return serviceLine;
    }

    public void setServiceLine(List <ServiceLine> serviceLine) {
        this.serviceLine = serviceLine;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
