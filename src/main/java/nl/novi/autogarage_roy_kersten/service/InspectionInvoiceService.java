package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.InspectionInvoice;
import nl.novi.autogarage_roy_kersten.model.Repair;

import java.util.List;

public interface InspectionInvoiceService extends InvoiceService {

    List<InspectionInvoice> getAllInspectionInvoices();

}
