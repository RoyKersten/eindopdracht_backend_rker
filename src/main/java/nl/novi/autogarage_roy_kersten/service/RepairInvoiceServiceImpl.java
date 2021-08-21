package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.RepairInvoice;
import nl.novi.autogarage_roy_kersten.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The RepairInvoiceServiceImpl class implements the methods defined in the RepairInvoiceService Interface.
 * The RepairInvoiceServiceImpl class receives information via this interface from the RepairInvoiceController class, adds business logic and
 * communicates with the RepairInvoiceRepository interface.
 * */


@Service
public class RepairInvoiceServiceImpl extends InvoiceServiceImpl implements RepairInvoiceService{

    //Attributes
    private RepairInvoiceRepository repairInvoiceRepository;

    //Constructores
    public RepairInvoiceServiceImpl(InvoiceRepository invoiceRepository, ServiceRepository serviceRepository, ServiceLineRepository serviceLineRepository, CustomerRepository customerRepository, RepairInvoiceRepository repairInvoiceRepository) {
        super(invoiceRepository, serviceRepository, serviceLineRepository, customerRepository);
        this.repairInvoiceRepository = repairInvoiceRepository;
    }

    //Methods
    //Get all Repair Invoices
    @Override
    public List<RepairInvoice> getAllRepairInvoices() {
        return repairInvoiceRepository.findAll();
    }

}
