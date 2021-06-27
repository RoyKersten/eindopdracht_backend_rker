package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.*;
import nl.novi.autogarage_roy_kersten.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InspectionInvoiceServiceImpl extends InvoiceServiceImpl implements InspectionInvoiceService {

    private InspectionInvoiceRepository inspectionInvoiceRepository;


    @Autowired
    public InspectionInvoiceServiceImpl(InvoiceRepository invoiceRepository, ServiceRepository serviceRepository, ServiceLineRepository serviceLineRepository, CustomerRepository customerRepository, InspectionInvoiceRepository inspectionInvoiceRepository) {
        super(invoiceRepository, serviceRepository, serviceLineRepository, customerRepository);
        this.inspectionInvoiceRepository = inspectionInvoiceRepository;
    }


    //Get all Inspection Invoices
    @Override
    public List<InspectionInvoice> getAllInspectionInvoices() {
        return inspectionInvoiceRepository.findAll();
    }


}


