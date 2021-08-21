package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.InspectionInvoice;
import nl.novi.autogarage_roy_kersten.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The InspectionInvoiceServiceImpl class implements the methods defined in the InspectionInvoiceService Interface.
 * The InspectionInvoiceServiceImpl class receives information via this interface from the InspectionInvoiceController class, adds business logic and
 * communicates with the InspectionInvoiceRepository interface.
 * */


@Service
public class InspectionInvoiceServiceImpl extends InvoiceServiceImpl implements InspectionInvoiceService {

    //Attributes
    private InspectionInvoiceRepository inspectionInvoiceRepository;

    //Constructors
    @Autowired
    public InspectionInvoiceServiceImpl(InvoiceRepository invoiceRepository, ServiceRepository serviceRepository, ServiceLineRepository serviceLineRepository, CustomerRepository customerRepository, InspectionInvoiceRepository inspectionInvoiceRepository) {
        super(invoiceRepository, serviceRepository, serviceLineRepository, customerRepository);
        this.inspectionInvoiceRepository = inspectionInvoiceRepository;
    }

    //Methods
    //Get all Inspection Invoices
    @Override
    public List<InspectionInvoice> getAllInspectionInvoices() {
        return inspectionInvoiceRepository.findAll();
    }


}


