package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.*;
import nl.novi.autogarage_roy_kersten.repository.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RepairInvoiceServiceTest extends InvoiceServiceTest {

    @Mock
    InvoiceRepository repairInvoiceRepository;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    ServiceRepository serviceRepository;

    @Mock
    ServiceLineRepository serviceLineRepository;

    @InjectMocks
    RepairInvoiceServiceImpl repairInvoiceService;

    @Captor
    ArgumentCaptor<Invoice> invoiceCaptor;


    @Test
    @Override
    void createInvoice() {
        //Arrange create mock objects for test
        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.BETAALD,0,0,0,0,null,null,null,null);

        when(invoiceRepository.save(repairInvoice)).thenReturn(repairInvoice);

        //Act & Assert => call method repairInvoiceService which will throw a NullPointerException, Test of subMethods will be done separately
        assertThrows(NullPointerException.class, () ->  repairInvoiceService.createInvoice(repairInvoice));

        verify(invoiceRepository, times(2)).save(repairInvoice);
        assertThat(repairInvoice.getInvoiceStatus()).isEqualTo(InvoiceStatus.OPEN);                                      //check if invoice status has been set to OPEN

        //Rest of the methods invoked in createInvoice() are tested separately by other unit tests in this class

    }

    @Test
    @Override
    void getInvoiceById() {
        //Arrange create mock objects for test
        Repair repair = new Repair();
        List<ServiceLine> serviceLines = new ArrayList<>();
        Customer customer = new Customer();

        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.BETAALD,100.0f,0.21f,21.0f,121,"D:/test/invoice",serviceLines,customer,repair);

        when(invoiceRepository.existsById(1L)).thenReturn(true);
        when(invoiceRepository.findById(1L)).thenReturn(repairInvoice);

        //Act => call method getInvoiceById
        RepairInvoice validateInvoice = (RepairInvoice) repairInvoiceService.getInvoiceById(1L);

        //Assert => validate return of method getInvoiceById and perform assertions
        assertThat(validateInvoice.getIdInvoice()).isEqualTo(1L);
        assertThat(validateInvoice.getInvoiceStatus()).isEqualTo(InvoiceStatus.BETAALD);
        assertThat(validateInvoice.getInvoiceSubtotal()).isEqualTo(100.0f);
        assertThat(validateInvoice.getVatRate()).isEqualTo(0.21f);
        assertThat(validateInvoice.getVatAmount()).isEqualTo(21.0f);
        assertThat(validateInvoice.getInvoiceTotal()).isEqualTo(121.0f);
        assertThat(validateInvoice.getPathName()).isEqualTo("D:/test/invoice");
        assertThat(validateInvoice.getService()).isEqualTo(repair);
        assertThat(validateInvoice.getCustomer()).isEqualTo(customer);
        assertThat(validateInvoice.getServiceLine()).isEqualTo(serviceLines);


    }

    @Test
    @Override
    void deleteInvoiceById() {
        //Arrange => check if idInvoice exists and return boolean true to pass BadRequestException check
        when(invoiceRepository.existsById(1L)).thenReturn(true);

        //Act => call method deleteInvoiceById
        repairInvoiceService.deleteInvoiceById(1L);

        //Assert => verify if mock invoiceRepository.deleteById has been called one time
        verify(invoiceRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateInvoiceById() {
        //Arrange create mock objects for test
        Repair repair = new Repair();
        List<ServiceLine> serviceLines = new ArrayList<>();
        Customer customer = new Customer();

        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.BETAALD,100.0f,0.21f,21.0f,121,"D:/test/invoice",serviceLines,customer,repair);

        when(invoiceRepository.existsById(1L)).thenReturn(true);
        when(invoiceRepository.findById(1L)).thenReturn(repairInvoice);


        //Assert
        //Ensure NullPointerException is Thrown and tested by setting Qty to 0
        assertThrows(NullPointerException.class, () -> repairInvoiceService.updateInvoiceById(1L, repairInvoice));
        verify(invoiceRepository, times(1)).existsById(1L);
        verify(invoiceRepository, times(1)).findById(1L);

        //Rest of the methods invoked in updateInvoiceById() are tested separately by other unit tests in this class
    }



    @Test
    @Override
    void updateInvoiceStatusById() {
        //Arrange create mock objects for test
        Repair repair = new Repair();
        List<ServiceLine> serviceLines = new ArrayList<>();
        Customer customer = new Customer();

        //initial invoice with status "OPEN"
        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.OPEN,100.0f,0.21f,21.0f,121,"D:/test/invoice",serviceLines,customer,repair);

        //update invoice with status "BETAALD"
        RepairInvoice updateRepairInvoice = new RepairInvoice(1L, InvoiceStatus.BETAALD,100.0f,0.21f,21.0f,121,"D:/test/invoice",serviceLines,customer,repair);

        when(invoiceRepository.existsById(1L)).thenReturn(true);
        when(invoiceRepository.findById(1L)).thenReturn(repairInvoice);
        when(invoiceRepository.save(repairInvoice)).thenReturn(repairInvoice);

        //Act
        repairInvoiceService.updateInvoiceStatusById(1L, updateRepairInvoice);

        //Assert
        verify(invoiceRepository, times(1)).existsById(1L);
        verify(invoiceRepository, times(1)).findById(1L);
        verify(invoiceRepository, times(1)).save(repairInvoice);

        assertThat(repairInvoice.getInvoiceStatus()).isEqualTo(InvoiceStatus.BETAALD);                              //Check if status has been updated to "BETAALD"
    }

    @Test
    void getCustomerInformationTest() {
        //Arrange create mock objects for test
        List<ServiceLine> serviceLines = new ArrayList<>();
        Customer customer = new Customer(1L,"Voornaam", "Achternaam","06","voornaam.achternaam@mail");
        LocalDate date = LocalDate.of(2020, 6, 8);
        Repair repair = new Repair (1L, date , ServiceStatus.UITVOEREN ,customer,"banden profiel te laag < 2mm, remmen achter vervangen", null, null);


        //initial invoice with customer null"
        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.OPEN,100.0f,0.21f,21.0f,121,"D:/test/invoice",serviceLines,null,repair);

        when(serviceRepository.findById(1L)).thenReturn(repair);
        when(customerRepository.findById(1L)).thenReturn(customer);
        when(invoiceRepository.save(repairInvoice)).thenReturn(repairInvoice);

        //Act
        repairInvoiceService.getCustomerInformation(repairInvoice);

        //Assert
        verify(serviceRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).findById(1L);
        verify(invoiceRepository, times(2)).save(repairInvoice);

        assertThat(repairInvoice.getCustomer()).isEqualTo(customer);                                                //check if inspection has been updated with customer

    }

    @Test
    void calculateInvoiceSubtotalTest() {
        //Arrange create mock objects for test
        List<ServiceLine> serviceLines = new ArrayList<>();
        Customer customer = new Customer(1L,"Voornaam", "Achternaam","06","voornaam.achternaam@mail");
        LocalDate date = LocalDate.of(2020, 6, 8);

        Repair repair = new Repair (1L, date , ServiceStatus.UITVOEREN ,customer,"banden profiel te laag < 2mm, remmen achter vervangen", serviceLines, null);
        ServiceLine serviceLine = new ServiceLine(1L,1L,2,"Test Item",100.0f,200.0f,0.21f,42.0f,0.0f,null,repair,null);
        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.OPEN,0.0f,0.21f,0.0f,0.0f,"D:/test/invoice",serviceLines,null,repair);

        serviceLines.add(serviceLine);

        when(invoiceRepository.save(repairInvoice)).thenReturn(repairInvoice);
        when(serviceRepository.findById(1L)).thenReturn(repair);
        when(serviceLineRepository.findById(1L)).thenReturn(serviceLines.get(0));
        when(serviceLineRepository.countByServiceIdService(1L)).thenReturn(serviceLines.get(0).getIdServiceLine());

        //Act
        repairInvoiceService.calculateInvoiceSubtotal(repairInvoice);

        verify(invoiceRepository, times(2)).save(repairInvoice);
        verify(serviceRepository, times(1)).findById(1L);
        verify(serviceLineRepository, times(1)).findById(1L);
        verify(serviceLineRepository, times(1)).countByServiceIdService(1L);

        assertThat(repairInvoice.getInvoiceSubtotal()).isEqualTo(200.0f);
    }

    @Test
    void calculateInvoiceVatAmountTest() {
        //Arrange create mock objects for test
        List<ServiceLine> serviceLines = new ArrayList<>();
        Customer customer = new Customer(1L,"Voornaam", "Achternaam","06","voornaam.achternaam@mail");
        LocalDate date = LocalDate.of(2020, 6, 8);

        Repair repair = new Repair (1L, date , ServiceStatus.UITVOEREN ,customer,"banden profiel te laag < 2mm, remmen achter vervangen", serviceLines, null);
        ServiceLine serviceLine = new ServiceLine(1L,1L,2,"Test Item",100.0f,200.0f,0.21f,42.0f,0.0f,null,repair,null);
        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.OPEN,0.0f,0.21f,0.0f,0.0f,"D:/test/invoice",serviceLines,null,repair);

        serviceLines.add(serviceLine);

        when(invoiceRepository.save(repairInvoice)).thenReturn(repairInvoice);
        when(serviceRepository.findById(1L)).thenReturn(repair);
        when(serviceLineRepository.findById(1L)).thenReturn(serviceLines.get(0));
        when(serviceLineRepository.countByServiceIdService(1L)).thenReturn(serviceLines.get(0).getIdServiceLine());

        //Act
        repairInvoiceService.calculateInvoiceVatAmount(repairInvoice);

        verify(invoiceRepository, times(2)).save(repairInvoice);
        verify(serviceRepository, times(1)).findById(1L);
        verify(serviceLineRepository, times(1)).findById(1L);
        verify(serviceLineRepository, times(1)).countByServiceIdService(1L);

        assertThat(repairInvoice.getVatAmount()).isEqualTo(42.0f);
    }


    @Test
    void calculateInvoiceTotalTest() {
        //Arrange create mock objects for test, invoiceTotal is null
        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.OPEN,200.0f,0.21f,42.0f,0.0f,"D:/test/invoice",null,null,null);

        when(invoiceRepository.save(repairInvoice)).thenReturn(repairInvoice);

        //Act
        Invoice validateInvoice = repairInvoiceService.calculateInvoiceTotal(repairInvoice);

        //Assert
        verify(invoiceRepository, times(2)).save(repairInvoice);
        assertThat(validateInvoice.getInvoiceTotal()).isEqualTo(242.0f);

    }

    @Test
    void printInvoiceTest() {
        //Arrange create mock objects for test
        List<ServiceLine> serviceLines = new ArrayList<>();
        Customer customer = new Customer(1L,"Voornaam", "Achternaam","06","voornaam.achternaam@mail");
        LocalDate date = LocalDate.of(2020, 6, 8);

        Repair repair = new Repair (1L, date , ServiceStatus.UITVOEREN ,customer,"banden profiel te laag < 2mm, remmen achter vervangen", serviceLines, null);
        ServiceLine serviceLine = new ServiceLine(1L,1L,2,"Test Item",100.0f,200.0f,0.21f,42.0f,242.0f,null,repair,null);
        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.OPEN,200.0f,0.21f,42.0f,242.0f, "src/test/resources/repairInvoiceTest.txt",serviceLines,customer,repair);

        serviceLines.add(serviceLine);

        when(invoiceRepository.save(repairInvoice)).thenReturn(repairInvoice);
        when(serviceRepository.findById(1L)).thenReturn(repair);
        when(serviceLineRepository.findById(1L)).thenReturn(serviceLines.get(0));
        when(serviceLineRepository.countByServiceIdService(1L)).thenReturn(serviceLines.get(0).getIdServiceLine());

        //Act
        repairInvoiceService.printInvoice(repairInvoice);

        //Assert
        verify(serviceRepository, times(1)).findById(1L);
        verify(serviceLineRepository, times(1)).findById(1L);
        verify(serviceLineRepository, times(1)).countByServiceIdService(1L);

        verify(invoiceRepository).save(invoiceCaptor.capture());
        Invoice validateInvoice = invoiceCaptor.getValue();

        assertThat(validateInvoice.getIdInvoice()).isEqualTo(1L);
        assertThat(validateInvoice.getInvoiceStatus()).isEqualTo(InvoiceStatus.OPEN);
        assertThat(validateInvoice.getInvoiceSubtotal()).isEqualTo(200.0f);
        assertThat(validateInvoice.getVatAmount()).isEqualTo(42.0f);
        assertThat(validateInvoice.getInvoiceTotal()).isEqualTo(242.0f);
        assertThat(validateInvoice.getPathName()).isEqualTo("src/test/resources/repairInvoiceTest.txt");
    }


}
