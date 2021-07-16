package nl.novi.autogarage_roy_kersten.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@DiscriminatorValue("inspection_invoice")
public class InspectionInvoice extends Invoice {


    //constructor
    public InspectionInvoice() {
    }

    public InspectionInvoice(Long idInvoice, InvoiceStatus invoiceStatus, float invoiceSubtotal, float vatRate, float vatAmount, float invoiceTotal, String pathName, List<ServiceLine> serviceLine, Customer customer, Service service) {
        super(idInvoice, invoiceStatus, invoiceSubtotal, vatRate, vatAmount, invoiceTotal, pathName, serviceLine, customer, service);
    }

    //Getters and Setters

}
