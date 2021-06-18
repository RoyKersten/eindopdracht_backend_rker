package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.InspectionInvoice;
import nl.novi.autogarage_roy_kersten.model.RepairInvoice;

import java.util.List;

public interface RepairInvoiceService extends InvoiceService {

    List<RepairInvoice> getAllRepairInvoices();

}
