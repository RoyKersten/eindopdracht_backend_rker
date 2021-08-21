package nl.novi.autogarage_roy_kersten.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("repair_invoice")
public class RepairInvoice extends Invoice {


    //constructor
    public RepairInvoice() {
    }

    public RepairInvoice(Long idInvoice, InvoiceStatus invoiceStatus, float invoiceSubtotal, float vatRate, float vatAmount, float invoiceTotal, String pathName, List<ServiceLine> serviceLine, Customer customer, Service service) {
        super(idInvoice, invoiceStatus, invoiceSubtotal, vatRate, vatAmount, invoiceTotal, pathName, serviceLine, customer, service);
    }

}
