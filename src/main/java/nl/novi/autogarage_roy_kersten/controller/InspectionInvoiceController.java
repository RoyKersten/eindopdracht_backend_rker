package nl.novi.autogarage_roy_kersten.controller;

import nl.novi.autogarage_roy_kersten.service.InspectionInvoiceService;
import nl.novi.autogarage_roy_kersten.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value = "/invoices/inspections")

public class InspectionInvoiceController extends InvoiceController {

    private InspectionInvoiceService inspectionInvoiceService;

    @Autowired
    public InspectionInvoiceController(@Qualifier("inspectionInvoiceService")InvoiceService invoiceService, InspectionInvoiceService inspectionInvoiceService) {
        super(invoiceService);
        this.inspectionInvoiceService = inspectionInvoiceService;
    }
}
