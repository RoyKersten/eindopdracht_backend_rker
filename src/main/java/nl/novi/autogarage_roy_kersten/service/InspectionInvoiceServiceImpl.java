package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.InspectionInvoice;
import nl.novi.autogarage_roy_kersten.repository.InspectionInvoiceRepository;
import nl.novi.autogarage_roy_kersten.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InspectionInvoiceServiceImpl extends InvoiceServiceImpl implements InspectionInvoiceService {

    private InspectionInvoiceRepository inspectionInvoiceRepository;

    @Autowired
    public InspectionInvoiceServiceImpl(InvoiceRepository invoiceRepository, InspectionInvoiceRepository inspectionInvoiceRepository) {
        super(invoiceRepository);
        this.inspectionInvoiceRepository = inspectionInvoiceRepository;
    }

    //Get all Inspection Invoices
    public List<InspectionInvoice> getAllInspectionInvoices() {
        return inspectionInvoiceRepository.findAll();
    }

}
