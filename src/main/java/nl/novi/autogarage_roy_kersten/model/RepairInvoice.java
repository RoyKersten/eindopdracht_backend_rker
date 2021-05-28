package nl.novi.autogarage_roy_kersten.model;

import nl.novi.autogarage_roy_kersten.model.Customer;
import nl.novi.autogarage_roy_kersten.model.Invoice;
import nl.novi.autogarage_roy_kersten.model.Repair;

public class RepairInvoice extends Invoice {

    //attributes
    private Repair repair;


//constructor

    public RepairInvoice(int idInvoice, String invoiceStatus, Customer customer, float lineTotal, float invoiceSubtotal, float vatRate, float vatAmount, float invoiceTotal) {
        super(idInvoice, invoiceStatus, customer, lineTotal, invoiceSubtotal, vatRate, vatAmount, invoiceTotal);
        this.repair = repair;

    }

//getters and setters

    public Repair getRepair() {
        return repair;
    }

    public void setRepair(Repair repair) {
        this.repair = repair;
    }


    // @Override
   // public String toString() {
   //    return getInspection().getIdService() + " " + getInspection().getServiceDate() + " " + getInspection().getServiceStatus() + " " + getInspection().getCustomer() + " " + getInspection().getServiceLine().getPrice() + " " + getInspection().getIssuesFoundInspection();  }   // uitbreiden met andere parameters phoneNumber en email}

     /** informatie uit ServiceLine kan rechtstreeks worden opgehaald uit ServiceLine of via Inspection
      *  getInspection().getServiceLine().getPrice()                => indien inspectionInvoice communiceerd via Inspection met ServiceLine
      *  getServiceLine().getQty()                                  => indien inspectionInvoice rechstreeks communiceerd met serviceLine
      **/


}
