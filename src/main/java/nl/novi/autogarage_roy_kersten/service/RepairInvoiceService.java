package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.repository.InvoiceRepository;
import nl.novi.autogarage_roy_kersten.repository.RepairInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepairInvoiceService extends InvoiceService {


    private RepairInvoiceRepository repairInvoiceRepository;

    @Autowired
    public RepairInvoiceService(InvoiceRepository invoiceRepository, RepairInvoiceRepository repairInvoiceRepository) {
        super(invoiceRepository);
        this.repairInvoiceRepository = repairInvoiceRepository;
    }
}
