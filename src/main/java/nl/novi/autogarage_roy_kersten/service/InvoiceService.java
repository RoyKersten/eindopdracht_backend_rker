package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.model.Invoice;
import nl.novi.autogarage_roy_kersten.model.Item;
import nl.novi.autogarage_roy_kersten.repository.InvoiceRepository;
import nl.novi.autogarage_roy_kersten.repository.ItemRepository;

import java.util.List;

public abstract class InvoiceService {

    //Attributes
    private InvoiceRepository invoiceRepository;


    //Constructors
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    //Methods

    //Create a new Invoice
    public long addInvoice(Invoice invoice) {
        Invoice storedInvoice = invoiceRepository.save(invoice);
        return storedInvoice.getIdInvoice();
    }

    //Get all Invoice
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    //Get Invoice by idInvoice
    public Invoice getInvoiceById(long idInvoice) {
        if (!invoiceRepository.existsById(idInvoice)) {
            throw new RecordNotFoundException();
        }
        return invoiceRepository.findById(idInvoice);
    }


    //Delete Invoice by idInvoice
    public void deleteInvoiceById(long idInvoice) {
        if (!invoiceRepository.existsById(idInvoice)) {
            throw new BadRequestException();
        }
        invoiceRepository.deleteById(idInvoice);
    }


    //Update Invoice by idInvoice
    public void updateInvoiceById(long idInvoice, Invoice updateInvoice) {
        if (!invoiceRepository.existsById(idInvoice)) {
            throw new BadRequestException();
        }
        Invoice storedInvoice = invoiceRepository.findById(idInvoice);
        storedInvoice.setInvoiceStatus(updateInvoice.getInvoiceStatus());
        storedInvoice.setLineTotal(updateInvoice.getLineTotal());
        storedInvoice.setInvoiceSubtotal(updateInvoice.getInvoiceSubtotal());
        storedInvoice.setInvoiceTotal(updateInvoice.getInvoiceTotal());
        storedInvoice.setVatRate(updateInvoice.getVatRate());
        storedInvoice.setVatAmount(updateInvoice.getVatAmount());
        storedInvoice.setCustomer(updateInvoice.getCustomer());
        invoiceRepository.save(updateInvoice);
    }


}
