package nl.novi.autogarage_roy_kersten.model;

import javax.persistence.*;

@Entity
@Table(name = "service_line")

public class ServiceLine {

    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServiceLine;

    @Column(name = "service_line_number")
    private Long serviceLineNumber;

    @Column(name = "qty")
    private int qty;

    @Column (name = "item_name")
    private String itemName;

    @Column (name = "price")
    private float price;

    @Column (name = "line_subtotal")
    private float lineSubTotal;

    @Column (name = "vat_rate")
    private float vatRate;

    @Column (name = "vat_amount")
    private float vatAmount;

    @Column (name = "line_total")
    private float lineTotal;


    @ManyToOne
    private Item item;

    @ManyToOne
    private Service service;

    @ManyToOne
    private Invoice invoice;

    //constructor
    public ServiceLine () {}

    public ServiceLine(Long idServiceLine, Long serviceLineNumber, int qty, String itemName, float price, float lineSubTotal, float vatRate, float vatAmount, float lineTotal, Item item, Service service, Invoice invoice) {
        this.idServiceLine = idServiceLine;
        this.serviceLineNumber = serviceLineNumber;
        this.qty = qty;
        this.itemName = itemName;
        this.price = price;
        this.lineSubTotal = lineSubTotal;
        this.vatRate = vatRate;
        this.vatAmount = vatAmount;
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

    public Long getServiceLineNumber() {
        return serviceLineNumber;
    }

    public void setServiceLineNumber(Long serviceLineNumber) {
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

    public float getLineSubTotal() {
        return lineSubTotal;
    }

    public void setLineSubTotal(float lineSubTotal) {
        this.lineSubTotal = lineSubTotal;
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