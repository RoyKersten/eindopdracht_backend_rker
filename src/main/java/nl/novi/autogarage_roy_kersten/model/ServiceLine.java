package nl.novi.autogarage_roy_kersten.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import nl.novi.autogarage_roy_kersten.model.Invoice;
import nl.novi.autogarage_roy_kersten.model.Item;
import nl.novi.autogarage_roy_kersten.model.Service;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "service_line")

public class ServiceLine {

    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServiceLine;

    @Column(name = "service_line_number")
    private int serviceLineNumber;

    @Column(name = "qty")
    private int qty;

    @Column (name = "item_name")
    private String itemName;

    @Column (name = "price")
    private float price;

    @Column (name = "line_total")
    private float lineTotal;

    @ManyToOne
    //@JsonBackReference
    private Item item;

    @ManyToOne
   // @JsonBackReference ()
    private Service service;

    @ManyToOne
   // @JsonBackReference
    private Invoice invoice;

    //constructor
    public ServiceLine () {}


    public ServiceLine(Long idServiceLine, int serviceLineNumber, int qty, String itemName, float price, float lineTotal, Item item, Service service, Invoice invoice) {
        this.idServiceLine = idServiceLine;
        this.serviceLineNumber = serviceLineNumber;
        this.qty = qty;
        this.itemName = itemName;
        this.price = price;
        this.lineTotal = lineTotal;
        this.item = item;
        this.service = service;
        this.invoice = invoice;
    }



    //getters and setters


    public Long getIdServiceLine() {
        return idServiceLine;
    }

    public void setIdServiceLine(Long idServiceLine) {
        this.idServiceLine = idServiceLine;
    }

    public int getServiceLineNumber() {
        return serviceLineNumber;
    }

    public void setServiceLineNumber(int serviceLineNumber) {
        this.serviceLineNumber = serviceLineNumber;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(float lineTotal) {
        this.lineTotal = lineTotal;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }


}