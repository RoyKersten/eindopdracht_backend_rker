package nl.novi.autogarage_roy_kersten.controller;

import nl.novi.autogarage_roy_kersten.service.InspectionInvoiceService;
import nl.novi.autogarage_roy_kersten.service.InvoiceService;
import nl.novi.autogarage_roy_kersten.service.RepairInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value = "/invoices/repairs")
public class RepairInvoiceController extends InvoiceController{

    private RepairInvoiceService repairInvoiceService;

    @Autowired
    public RepairInvoiceController(@Qualifier("repairInvoiceService")InvoiceService invoiceService, RepairInvoiceService repairInvoiceService) {
        super(invoiceService);
        this.repairInvoiceService = repairInvoiceService;
    }
}
