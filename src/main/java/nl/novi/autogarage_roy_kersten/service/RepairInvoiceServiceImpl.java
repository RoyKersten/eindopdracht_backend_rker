package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.*;
import nl.novi.autogarage_roy_kersten.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairInvoiceServiceImpl extends InvoiceServiceImpl implements RepairInvoiceService{


    private RepairInvoiceRepository repairInvoiceRepository;


    public RepairInvoiceServiceImpl(InvoiceRepository invoiceRepository, ServiceRepository serviceRepository, ServiceLineRepository serviceLineRepository, CustomerRepository customerRepository, RepairInvoiceRepository repairInvoiceRepository) {
        super(invoiceRepository, serviceRepository, serviceLineRepository, customerRepository);
        this.repairInvoiceRepository = repairInvoiceRepository;
    }


    //Get all Repair Invoices
    @Override
    public List<RepairInvoice> getAllRepairInvoices() {
        return repairInvoiceRepository.findAll();
    }

}
