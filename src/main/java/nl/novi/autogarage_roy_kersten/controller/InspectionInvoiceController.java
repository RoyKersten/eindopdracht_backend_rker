package nl.novi.autogarage_roy_kersten.controller;

import nl.novi.autogarage_roy_kersten.service.InspectionInvoiceService;
import nl.novi.autogarage_roy_kersten.service.InspectionInvoiceServiceImpl;
import nl.novi.autogarage_roy_kersten.service.InvoiceService;
import nl.novi.autogarage_roy_kersten.service.InvoiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value = "/invoices/inspections")

public class InspectionInvoiceController extends InvoiceController {

    private InspectionInvoiceService inspectionInvoiceService;

    @Autowired
    public InspectionInvoiceController(@Qualifier("inspectionInvoiceServiceImpl") InvoiceService invoiceService, InspectionInvoiceService inspectionInvoiceService) {
        super(invoiceService);
        this.inspectionInvoiceService = inspectionInvoiceService;
    }


    //Methods
    //Get all inspection invoices, need to be defined in subclass InspectionInvoice, path: "/invoices/inspections" should only show inspection invoices, not repair invoices
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllInspectionInvoices() {
        return ResponseEntity.ok(inspectionInvoiceService.getAllInspectionInvoices());
    }

}
