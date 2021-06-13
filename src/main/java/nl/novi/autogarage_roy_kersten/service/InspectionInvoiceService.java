package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.repository.InspectionInvoiceRepository;
import nl.novi.autogarage_roy_kersten.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InspectionInvoiceService extends InvoiceService {

    private InspectionInvoiceRepository inspectionInvoiceRepository;

    @Autowired
    public InspectionInvoiceService(InvoiceRepository invoiceRepository, InspectionInvoiceRepository inspectionInvoiceRepository) {
        super(invoiceRepository);
        this.inspectionInvoiceRepository = inspectionInvoiceRepository;
    }


}
