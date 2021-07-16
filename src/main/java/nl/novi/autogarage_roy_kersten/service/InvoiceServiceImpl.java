package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.model.*;
import nl.novi.autogarage_roy_kersten.repository.CustomerRepository;
import nl.novi.autogarage_roy_kersten.repository.InvoiceRepository;
import nl.novi.autogarage_roy_kersten.repository.ServiceLineRepository;
import nl.novi.autogarage_roy_kersten.repository.ServiceRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class InvoiceServiceImpl implements InvoiceService {

    //Attributes
    private InvoiceRepository invoiceRepository;
    private ServiceRepository serviceRepository;
    private ServiceLineRepository serviceLineRepository;
    private CustomerRepository customerRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, ServiceRepository serviceRepository, ServiceLineRepository serviceLineRepository, CustomerRepository customerRepository) {
        this.invoiceRepository = invoiceRepository;
        this.serviceRepository = serviceRepository;
        this.serviceLineRepository = serviceLineRepository;
        this.customerRepository = customerRepository;
    }


    //Methods
    @Override
    public long createInvoice(Invoice invoice) {
        Invoice storedInvoice = invoiceRepository.save(invoice);

        //set invoice status
        storedInvoice.setInvoiceStatus(InvoiceStatus.OPEN);

        getCustomerInformation(storedInvoice);
        calculateInvoiceSubtotal(storedInvoice);
        calculateInvoiceVatAmount(storedInvoice);
        calculateInvoiceTotal(storedInvoice);
        printInvoice(storedInvoice);

        return storedInvoice.getIdInvoice();
    }


    //Get Invoice by idInvoice
    @Override
    public Invoice getInvoiceById(long idInvoice) {
        if (!invoiceRepository.existsById(idInvoice)) {
            throw new RecordNotFoundException();
        }
        return invoiceRepository.findById(idInvoice);
    }


    //Delete Invoice by idInvoice
    @Override
    public void deleteInvoiceById(long idInvoice) {
        if (!invoiceRepository.existsById(idInvoice)) {
            throw new BadRequestException();
        }
        invoiceRepository.deleteById(idInvoice);
    }


    //Update Invoice by idInvoice
    @Override
    public void updateInvoiceById(long idInvoice, Invoice updateInvoice) {
        if (!invoiceRepository.existsById(idInvoice)) {
            throw new BadRequestException();
        }

        Invoice storedInvoice = invoiceRepository.findById(idInvoice);
        storedInvoice.setService(updateInvoice.getService());
        getCustomerInformation(storedInvoice);
        calculateInvoiceSubtotal(storedInvoice);
        calculateInvoiceVatAmount(storedInvoice);
        calculateInvoiceTotal(storedInvoice);
        invoiceRepository.save(storedInvoice);
    }

    //Update invoiceStatus by idInvoice
    @Override
    public void updateInvoiceStatusById(long idInvoice, Invoice updateInvoice) {
        if (!invoiceRepository.existsById(idInvoice)) {
            throw new BadRequestException();

        }
        Invoice storedInvoice = invoiceRepository.findById(idInvoice);
        if (!(updateInvoice.getInvoiceStatus().equals(InvoiceStatus.OPEN) || updateInvoice.getInvoiceStatus().equals(InvoiceStatus.BETAALD))) {     //Invoice can only have status "open" or "betaald"
            throw new BadRequestException();
        }
        storedInvoice.setInvoiceStatus(updateInvoice.getInvoiceStatus());
        invoiceRepository.save(storedInvoice);
    }


    public Invoice getCustomerInformation(Invoice invoice) {
        Invoice storedServiceInvoice = invoiceRepository.save(invoice);

        //lookup inspection information from database by idService
        long storedServiceId = storedServiceInvoice.getService().getIdService();
        Service storedService = serviceRepository.findById(storedServiceId);


        //get idCustomer related to inspection and connect customer to InspectionInvoice
        long storedCustomerId = storedService.getCustomer().getIdCustomer();
        Customer storedCustomer = customerRepository.findById(storedCustomerId);
        storedServiceInvoice.setCustomer(storedCustomer);
        return invoiceRepository.save(invoice);
    }

    public Invoice calculateInvoiceSubtotal(Invoice invoice) {

        Invoice storedServiceInvoice = invoiceRepository.save(invoice);
        long storedServiceId = storedServiceInvoice.getService().getIdService();
        Service storedService = serviceRepository.findById(storedServiceId);

        storedServiceInvoice.setInvoiceSubtotal(0.00f);                                                                 //set invoiceSubtotal to zero, to ensure recalculation is correct in case of PUT request

        long serviceLineCounter = 1;                                                                                                        //set serviceLineCounter to 1, loop should executed minimal 1 time before final counter is set based on number of ServiceLines to be Invoiced

        for (int i = 0; i < serviceLineCounter; i++) {                                                                                      //loop based on number of serviceLines which need to be invoiced per idService
            long storedServiceLineId = storedService.getServiceLine().get(i).getIdServiceLine();                                         //get serviceLineId corresponding with idService which needs to be invoiced (based on index)
            ServiceLine storedServiceLine = serviceLineRepository.findById(storedServiceLineId);                                            //store serviceLine
            serviceLineCounter = serviceLineRepository.countByServiceIdService(storedServiceLine.getService().getIdService());              //Set serviceLineCounter based on number of ServiceLines with corresponding idService

            BigDecimal invoiceSubtotalRounded = new BigDecimal(storedServiceInvoice.getInvoiceSubtotal() + storedServiceLine.getLineSubTotal());       //Get the vatAmount per serviceLine and make it BigDecimal
            storedServiceInvoice.setInvoiceSubtotal(invoiceSubtotalRounded.setScale(2, RoundingMode.HALF_EVEN).floatValue());

        }
        return invoiceRepository.save(invoice);
    }


    public Invoice calculateInvoiceVatAmount(Invoice invoice) {

        Invoice storedServiceInvoice = invoiceRepository.save(invoice);

        long storedServiceId = storedServiceInvoice.getService().getIdService();
        Service storedService = serviceRepository.findById(storedServiceId);

        storedServiceInvoice.setVatAmount(0.00f);                                                                 //set vatAmount to zero, to ensure recalculation is correct in case of PUT request

        long serviceLineCounter = 1;                                                                                                        //set serviceLineCounter to 1, loop should executed minimal 1 time before final counter is set based on number of ServiceLines to be Invoiced

        for (int i = 0; i < serviceLineCounter; i++) {                                                                                      //loop based on number of serviceLines which need to be invoiced per idService
            long storedServiceLineId = storedService.getServiceLine().get(i).getIdServiceLine();                                            //get serviceLineId corresponding with idService which needs to be invoiced (based on index)
            ServiceLine storedServiceLine = serviceLineRepository.findById(storedServiceLineId);                                            //store serviceLine
            serviceLineCounter = serviceLineRepository.countByServiceIdService(storedServiceLine.getService().getIdService());              //Set serviceLineCounter based on number of ServiceLines with corresponding idService


            BigDecimal vatAmountRounded = new BigDecimal(storedServiceInvoice.getVatAmount() + storedServiceLine.getVatAmount());       //Get the vatAmount per serviceLine and make it BigDecimal
            storedServiceInvoice.setVatAmount(vatAmountRounded.setScale(2, RoundingMode.HALF_EVEN).floatValue());                  //Set the vatAmount as float and round with 2 decimals


            if (storedServiceInvoice.getVatRate() == 0.00f) {
                storedServiceInvoice.setVatRate(storedServiceLine.getVatRate());                                                            //get vatRate at first cycle, VAT rate is for all items (parts and activities) the same rate
            }

            storedServiceLine.setInvoice(storedServiceInvoice);                                                                             //Update the idInvoice In the ServiceLine
        }

        return invoiceRepository.save(invoice);
    }


    public Invoice calculateInvoiceTotal(Invoice invoice) {
        Invoice storedServiceInvoice = invoiceRepository.save(invoice);

        storedServiceInvoice.setInvoiceTotal(0.00f);                        //set invoiceTotal to zero, to ensure recalculation is correct in case of PUT request

        //Calculate invoiceTotal
        BigDecimal invoiceTotalRounded = new BigDecimal(storedServiceInvoice.getInvoiceSubtotal() + storedServiceInvoice.getVatAmount());       //Invoice Total = invoice Subtotal and vatAmount
        storedServiceInvoice.setInvoiceTotal(invoiceTotalRounded.setScale(2, RoundingMode.HALF_EVEN).floatValue());

        return invoiceRepository.save(invoice);
    }


    public void printInvoice(Invoice invoice) {
        Invoice storedServiceInvoice = invoiceRepository.save(invoice);

        long storedServiceId = storedServiceInvoice.getService().getIdService();
        Service storedService = serviceRepository.findById(storedServiceId);

        try {
            //String path has to be defined in Json;
            File invoiceFile = new File(invoice.getPathName());
            FileWriter invoicePrintLine = new FileWriter(invoiceFile);

            long serviceLineCounter = 1;                                                                                                        //set serviceLineCounter to 1, loop should executed minimal 1 time before final counter is set based on number of ServiceLines to be Invoiced

            invoicePrintLine.write("\n");

            invoicePrintLine.write(String.format("%-10s \r\n", "\tFACTUUR"));

            invoicePrintLine.write("\n");

            invoicePrintLine.write(String.format("%-15s%-65s%-10s \r\n", "\tklantnummer:", invoice.getCustomer().getIdCustomer(), "AUTOGARAGE KERSTEN"));
            invoicePrintLine.write(String.format("%-15s%-65s%-10s \r\n", "\tvoornaam:", invoice.getCustomer().getFirstName(), "Autosnelweg A73"));
            invoicePrintLine.write(String.format("%-15s%-65s%-10s \r\n", "\tachternaam:", invoice.getCustomer().getLastName(), "Horst aan de Maas"));
            invoicePrintLine.write(String.format("%-15s%-65s%-10s \r\n", "\temail:", invoice.getCustomer().getEmail(), "mail: autograrage.kersten@gmail.com"));
            invoicePrintLine.write(String.format("%-15s%-65s%-10s \r\n", "\ttelefoon:", invoice.getCustomer().getPhoneNumber(), "tel: +31612345678"));

            invoicePrintLine.write("\n");
            invoicePrintLine.write("\n");

            invoicePrintLine.write(String.format("%-15s%-10s%-10s%-10s \r\n", "\tFactuurnummer: ", invoice.getIdInvoice(), "Servicenummer: ", invoice.getService().getIdService()));

            invoicePrintLine.write("\t");

            for (int i = 0; i < 115; i++) {
                invoicePrintLine.write("-");

            }

            invoicePrintLine.write("\n");
            invoicePrintLine.write("\t");
            invoicePrintLine.write(String.format("%-10s%-10s%-40s%-10s%-15s%-15s%-15s \r\n", "nummer", "qty.", "artikel naam", "prijs", "subtotaal", "BTW bedrag", "TOTAAL"));

            for (int i = 0; i < serviceLineCounter; i++) {                                                                                      //loop based on number of serviceLines which need to be invoiced per idService
                long storedServiceLineId = storedService.getServiceLine().get(i).getIdServiceLine();                                            //get serviceLineId corresponding with idService which needs to be invoiced (based on index)
                ServiceLine storedServiceLine = serviceLineRepository.findById(storedServiceLineId);                                            //store serviceLine
                serviceLineCounter = serviceLineRepository.countByServiceIdService(storedServiceLine.getService().getIdService());              //Set serviceLineCounter based on number of ServiceLines with corresponding idService

                invoicePrintLine.write("\t");
                invoicePrintLine.write(String.format("%-10s%-10s%-40s%-10s%-15s%-15s%-15s \r\n", storedServiceLine.getServiceLineNumber(), storedServiceLine.getQty(), storedServiceLine.getItemName(), storedServiceLine.getPrice(), storedServiceLine.getLineSubTotal(), storedServiceLine.getVatAmount(), storedServiceLine.getLineTotal()));
            }

            invoicePrintLine.write("\t");
            for (int i = 0; i < 115; i++) {
                invoicePrintLine.write("-");
            }

            invoicePrintLine.write("\n");
            invoicePrintLine.write("\t");

            invoicePrintLine.write(String.format("%-70s%-15s%-15s%-15s \r\n", "TOTAAL FACTUUR", invoice.getInvoiceSubtotal(), invoice.getVatAmount(), invoice.getInvoiceTotal()));

            invoicePrintLine.write("\n");
            invoicePrintLine.write("\t");
            invoicePrintLine.write("Toegepast BTW percentage: " + invoice.getVatRate() * 100 + "%");

            invoicePrintLine.close();


        } catch (IOException e) {
            System.out.println("No Access to folder!");
        }

    }

}


