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

    public InspectionInvoice(Long idInvoice, String invoiceStatus, float lineTotal, float invoiceSubtotal, float vatRate, float vatAmount, float invoiceTotal, List<ServiceLine> serviceLine, Customer customer, Service service) {
        super(idInvoice, invoiceStatus, invoiceSubtotal, vatRate, vatAmount, invoiceTotal, serviceLine, customer, service);
    }

    //Getters and Setters

}
