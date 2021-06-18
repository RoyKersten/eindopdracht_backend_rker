package nl.novi.autogarage_roy_kersten.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("inspection_invoice")
public class InspectionInvoice extends Invoice {

    //attributes
    @OneToOne
    @JsonIgnore
    private Inspection inspection;


    //constructor
    public InspectionInvoice() {
    }

    public InspectionInvoice(Long idInvoice, String invoiceStatus, Customer customer, float lineTotal, float invoiceSubtotal, float vatRate, float vatAmount, float invoiceTotal, Inspection inspection) {
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


}
