package nl.novi.autogarage_roy_kersten.model;

import nl.novi.autogarage_roy_kersten.model.Customer;

public abstract class Invoice {


    //attributes
    private int idInvoice;
    private String invoiceStatus;
    private Customer customer;
    private float lineTotal;
    private float invoiceSubtotal;
    private float vatRate;
    private float vatAmount;
    private float invoiceTotal;

    //Constructors
    public Invoice(int idInvoice, String invoiceStatus, Customer customer, float lineTotal, float invoiceSubtotal, float vatRate, float vatAmount, float invoiceTotal) {
        this.idInvoice = idInvoice;
        this.invoiceStatus = invoiceStatus;
        this.customer = customer;
        this.lineTotal = lineTotal;
        this.invoiceSubtotal = invoiceSubtotal;
        this.vatRate = vatRate;
        this.vatAmount = vatAmount;
        this.invoiceTotal = invoiceTotal;
    }


    //Getters and Setters


    public int getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(int idInvoice) {
        this.idInvoice = idInvoice;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public float getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(float lineTotal) {
        this.lineTotal = lineTotal;
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

    //Methods



}
