package nl.novi.autogarage_roy_kersten.controller;

import nl.novi.autogarage_roy_kersten.service.InvoiceServiceImpl;
import nl.novi.autogarage_roy_kersten.service.RepairInvoiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value = "/invoices/repairs")
public class RepairInvoiceController extends InvoiceController {

    private RepairInvoiceServiceImpl repairInvoiceService;

    @Autowired
    public RepairInvoiceController(@Qualifier("repairInvoiceServiceImpl") InvoiceServiceImpl invoiceServiceImpl, RepairInvoiceServiceImpl repairInvoiceService) {
        super(invoiceServiceImpl);
        this.repairInvoiceService = repairInvoiceService;
    }


    //Methods
    //Get all repair invoices, need to be defined in subclass RepairInvoice, path: "/invoices/repairs" should only show repair invoices, not inspection invoices
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllRepairInvoices() {
        return ResponseEntity.ok(repairInvoiceService.getAllRepairInvoices());
    }
}
