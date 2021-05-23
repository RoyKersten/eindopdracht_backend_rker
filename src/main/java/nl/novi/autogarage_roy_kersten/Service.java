package nl.novi.autogarage_roy_kersten;

import javax.print.DocFlavor;
import java.util.Date;

public abstract class Service {

    //attributen
    private int idService;
    private Date serviceDate;             // Date format ????????
    private String serviceStatus;           //zou ook uit een list geselecteerd kunnen worden, dan is het altijd een vaste omschrijving ??? !!!
    private Customer customer;
    private ServiceLine serviceLine;


    //constructor
    public Service(int idService, Date serviceDate, String serviceStatus, Customer customer, ServiceLine serviceLine) {
        this.idService = idService;
        this.serviceDate = serviceDate;
        this.serviceStatus = serviceStatus;
        this.customer = customer;
        this.serviceLine = serviceLine;
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

    public ServiceLine getServiceLine() {
        return serviceLine;
    }

    public void setServiceLine(ServiceLine serviceLine) {
        this.serviceLine = serviceLine;
    }
}
