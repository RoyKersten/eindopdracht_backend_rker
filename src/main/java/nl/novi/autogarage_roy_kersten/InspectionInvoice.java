package nl.novi.autogarage_roy_kersten;

public class InspectionInvoice extends Invoice {

    //attributes
    private Inspection inspection;


//constructor

    public InspectionInvoice(int idInvoice, String invoiceStatus, Customer customer, float lineTotal, float invoiceSubtotal, float vatRate, float vatAmount, float invoiceTotal) {
        super(idInvoice, invoiceStatus, customer, lineTotal, invoiceSubtotal, vatRate, vatAmount, invoiceTotal);
        this.inspection = inspection;

    }

//getters and setters

    public Inspection getInspection() {
        return inspection;
    }

    public void setInspection(Inspection inspection) {
        this.inspection = inspection;
    }


   // @Override
   // public String toString() {
   //    return getInspection().getIdService() + " " + getInspection().getServiceDate() + " " + getInspection().getServiceStatus() + " " + getInspection().getCustomer() + " " + getInspection().getServiceLine().getPrice() + " " + getInspection().getIssuesFoundInspection();  }   // uitbreiden met andere parameters phoneNumber en email}

     /** informatie uit ServiceLine kan rechtstreeks worden opgehaald uit ServiceLine of via Inspection
      *  getInspection().getServiceLine().getPrice()                => indien inspectionInvoice communiceerd via Inspection met ServiceLine
      *  getServiceLine().getQty()                                  => indien inspectionInvoice rechstreeks communiceerd met serviceLine
      **/


}
