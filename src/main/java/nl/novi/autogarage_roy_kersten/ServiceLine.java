package nl.novi.autogarage_roy_kersten;

public class ServiceLine {

    //attributes
    private int idServiceLine;
    private int serviceLineNumber;
    private int qty;
    private String itemName;
    private float price;
    private float lineTotal;
    private Item item;
    private Service service;
    private Invoice invoice;

    //constructor

    public ServiceLine(int idServiceLine, int serviceLineNumber, int qty, String itemName, float price, float lineTotal, Item item, Service service, Invoice invoice) {
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


    public int getIdServiceLine() {
        return idServiceLine;
    }

    public void setIdServiceLine(int idServiceLine) {
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