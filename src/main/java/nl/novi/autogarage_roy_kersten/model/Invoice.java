package nl.novi.autogarage_roy_kersten.model;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)           //Set Strategy SINGLE_TABLE => Create one table for all subclasses with a subclass type column to differentiate between subclasses
@DiscriminatorColumn(name = "invoice_type")                     //Subclasses of class Invoice can be identified in the table by invoice_type
@Table(name = "invoice")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = InspectionInvoice.class, name = "inspection_invoice"),
        @JsonSubTypes.Type(value = RepairInvoice.class, name = "repair_invoice"),
})

public abstract class Invoice {


    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInvoice;

    @Column(name = "invoice_status")
    @Enumerated(EnumType.STRING)                            //ensure that enum is presented as a string in the database
    private InvoiceStatus invoiceStatus;

    @Column(name = "invoice_subtotal")
    private float invoiceSubtotal;

    @Column(name = "vat_rate")
    private float vatRate;

    @Column(name = "vat_amount")
    private float vatAmount;

    @Column(name = "invoice_total")
    private float invoiceTotal;

    @Column(name = "path_name")
    private String pathName;

    @OneToMany (mappedBy = "invoice")
    @JsonIgnore
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<ServiceLine> serviceLine;

    @ManyToOne
    @JsonIncludeProperties("idCustomer")
    private Customer customer;

    @OneToOne
    @JsonIncludeProperties("idService")
    private Service service;

    //Constructors
    public Invoice() {
    }

    public Invoice(Long idInvoice, InvoiceStatus invoiceStatus, float invoiceSubtotal, float vatRate, float vatAmount, float invoiceTotal, String pathName, List<ServiceLine> serviceLine, Customer customer, Service service) {
        this.idInvoice = idInvoice;
        this.invoiceStatus = invoiceStatus;
        this.invoiceSubtotal = invoiceSubtotal;
        this.vatRate = vatRate;
        this.vatAmount = vatAmount;
        this.invoiceTotal = invoiceTotal;
        this.pathName = pathName;
        this.serviceLine = serviceLine;
        this.customer = customer;
        this.service = service;
    }

    //Getters and Setters
    public Long getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(Long idInvoice) {
        this.idInvoice = idInvoice;
    }

    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public float getInvoiceSubtotal() {
        return invoiceSubtotal;
    }

    public void setInvoiceSubtotal(float invoiceSubtotal) {
        this.invoiceSubtotal = invoiceSubtotal;
    }

    public float getVatRate() {
        return vatRate;
    }

    public void setVatRate(float vatRate) {
        this.vatRate = vatRate;
    }

    public float getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(float vatAmount) {
        this.vatAmount = vatAmount;
    }

    public float getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(float invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public List<ServiceLine> getServiceLine() {
        return serviceLine;
    }

    public void setServiceLine(List<ServiceLine> serviceLine) {
        this.serviceLine = serviceLine;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

}






