package nl.novi.autogarage_roy_kersten.model;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)                   //Set Strategy SINGLE_TABLE => Create one table for all subclasses with a subclass type column to differentiate between subclasses
@DiscriminatorColumn(name = "service_type")                             //Table generation
@Table(name = "service")                                                //Table generation

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Inspection.class, name = "inspection"),

        @JsonSubTypes.Type(value = Repair.class, name = "repair") }
)

public abstract class Service {

    //attributen
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idService;

    @Column (name = "service_date")
    private LocalDate serviceDate;

    @Column (name = "service_status")
    @Enumerated(EnumType.STRING)                            //ensure that enum is presented as a string in the database
    private ServiceStatus serviceStatus;

    @OneToMany (mappedBy = "service")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JsonIgnore
    private List<ServiceLine> serviceLine;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)    // 	In this cascade operation, if the parent entity is merged then all its related entity will also be merged.
    private Customer customer;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)    // 	In this cascade operation, if the parent entity is merged then all its related entity will also be merged.
    private Car car;

     //constructor
    public Service() {}

    public Service(Long idService, ServiceStatus serviceStatus){
        this.idService = idService;
        this.serviceStatus = serviceStatus;
    }

    public Service(Long idService, LocalDate serviceDate, ServiceStatus serviceStatus, Customer customer, List<ServiceLine> serviceLine,Car car) {
        this.idService = idService;
        this.serviceDate = serviceDate;
        this.serviceStatus = serviceStatus;
        this.customer = customer;
        this.serviceLine = serviceLine;
        this.car = car;
    }


    //getters and setters

    public Long getIdService() {
        return idService;
    }

    public void setIdService(Long idService) {
        this.idService = idService;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
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
