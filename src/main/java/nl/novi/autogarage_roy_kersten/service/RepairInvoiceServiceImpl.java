package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.RepairInvoice;
import nl.novi.autogarage_roy_kersten.repository.InvoiceRepository;
import nl.novi.autogarage_roy_kersten.repository.RepairInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairInvoiceServiceImpl extends InvoiceServiceImpl implements RepairInvoiceService{


    private RepairInvoiceRepository repairInvoiceRepository;

    @Autowired
    public RepairInvoiceServiceImpl(InvoiceRepository invoiceRepository, RepairInvoiceRepository repairInvoiceRepository) {
        super(invoiceRepository);
        this.repairInvoiceRepository = repairInvoiceRepository;
    }

    //Get all Repair Invoices
    public List<RepairInvoice> getAllRepairInvoices() {
        return repairInvoiceRepository.findAll();
    }

}
