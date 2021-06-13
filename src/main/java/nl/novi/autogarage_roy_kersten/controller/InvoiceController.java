package nl.novi.autogarage_roy_kersten.controller;

import nl.novi.autogarage_roy_kersten.model.Invoice;
import nl.novi.autogarage_roy_kersten.model.Item;
import nl.novi.autogarage_roy_kersten.model.Part;
import nl.novi.autogarage_roy_kersten.service.InvoiceService;
import nl.novi.autogarage_roy_kersten.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public abstract class InvoiceController {

    private InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    //Methods

    //Create a new Invoice
    @PostMapping(value = "")
    public ResponseEntity<Object> addInvoice(@RequestBody Invoice invoice) {
        long newId = invoiceService.addInvoice(invoice);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idInvoice}")
                .buildAndExpand(newId).toUri();
        return ResponseEntity.created(location).body(location);
    }

    //Get all Invoices
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }


    //Get Invoice by idInvoices
    @GetMapping("/{idInvoice}")
    public ResponseEntity<Object> getInvoiceById(@PathVariable("idInvoice") long idInvoice) {
        Invoice invoice = invoiceService.getInvoiceById(idInvoice);
        return ResponseEntity.ok(invoice);
    }


    //Delete Invoice by idInvoice
    @DeleteMapping("/{idInvoice}")
    public ResponseEntity<Object> deleteInvoiceById(@PathVariable("idInvoice") long idInvoice) {
        invoiceService.deleteInvoiceById(idInvoice);
        return ResponseEntity.ok("Invoice successfully deleted");
    }

    //Update Invoice by idInvoice
    @PutMapping("/{idInvoice}")
    public ResponseEntity<Object> updateInvoiceById(@PathVariable("idInvoice") long idInvoice, @RequestBody Invoice updateInvoice) {
        invoiceService.updateInvoiceById(idInvoice, updateInvoice);
        return ResponseEntity.ok("update Invoice successfully");
    }

}
