package nl.novi.autogarage_roy_kersten.model;

import nl.novi.autogarage_roy_kersten.model.Customer;
import nl.novi.autogarage_roy_kersten.model.Invoice;
import nl.novi.autogarage_roy_kersten.model.Repair;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("repair_invoice")
public class RepairInvoice extends Invoice {

    //attributes
    @OneToOne
    private Service service;


//constructor
    public RepairInvoice() {}

    public RepairInvoice(int idInvoice, String invoiceStatus, Customer customer, float lineTotal, float invoiceSubtotal, float vatRate, float vatAmount, float invoiceTotal, Service service) {
        super(idInvoice, invoiceStatus, customer, lineTotal, invoiceSubtotal, vatRate, vatAmount, invoiceTotal);
        this.service = service;

    }

//getters and setters

    public Service getRepair() {
        return service;
    }

    public void setRepair(Service repair) {
        this.service = service;
    }


    // @Override
   // public String toString() {
   //    return getInspection().getIdService() + " " + getInspection().getServiceDate() + " " + getInspection().getServiceStatus() + " " + getInspection().getCustomer() + " " + getInspection().getServiceLine().getPrice() + " " + getInspection().getIssuesFoundInspection();  }   // uitbreiden met andere parameters phoneNumber en email}

     /** informatie uit ServiceLine kan rechtstreeks worden opgehaald uit ServiceLine of via Inspection
      *  getInspection().getServiceLine().getPrice()                => indien inspectionInvoice communiceerd via Inspection met ServiceLine
      *  getServiceLine().getQty()                                  => indien inspectionInvoice rechstreeks communiceerd met serviceLine
      **/


}
