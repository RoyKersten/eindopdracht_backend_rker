package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Invoice;

public interface InvoiceService {

    //Methods
    Invoice getInvoiceById (long idService);
    long createInvoice (Invoice invoice);
    void deleteInvoiceById(long idInvoice);
    void updateInvoiceById(long idInvoice, Invoice invoice);
    void updateInvoiceStatusById(long idInvoice, Invoice invoice);
}
