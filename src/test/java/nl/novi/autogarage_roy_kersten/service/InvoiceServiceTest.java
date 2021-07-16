package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.repository.InvoiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
abstract class InvoiceServiceTest {

    @Mock
    InvoiceRepository invoiceRepository;

    @Test
    void createInvoice() {}

    @Test
    void getInvoiceById() {}

    @Test
    void deleteInvoiceById() {}

    @Test
    void updateInvoiceStatusById() {}

}
