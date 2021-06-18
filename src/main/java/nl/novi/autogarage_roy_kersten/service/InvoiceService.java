package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Invoice;
import nl.novi.autogarage_roy_kersten.model.Service;

public interface InvoiceService {

    //Methods
    Invoice getInvoiceById (long idService);
    long addInvoice (Invoice invoice);
    void deleteInvoiceById(long idInvoice);
}
