package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.model.*;
import nl.novi.autogarage_roy_kersten.repository.CustomerRepository;
import nl.novi.autogarage_roy_kersten.repository.InvoiceRepository;
import nl.novi.autogarage_roy_kersten.repository.ServiceLineRepository;
import nl.novi.autogarage_roy_kersten.repository.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RepairInvoiceServiceTest {

    @Mock
    InvoiceRepository invoiceRepository;

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
    void checkIfServiceIsInvoicedAlreadyWhenYesThrowBadRequestException() {
        //Arrange create mock objects for test
        Repair repair = new Repair();
        List<ServiceLine> serviceLines = new ArrayList<>();
        Customer customer = new Customer();
        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.BETAALD,100.0f,0.21f,21.0f,121,"D:/test/invoice",serviceLines,customer,repair);

        when(invoiceRepository.findInvoiceByService(repair)).thenReturn(repairInvoice);

        //Act
        assertThrows(BadRequestException.class, () -> repairInvoiceService.checkIfServiceIsInvoicedAlready(repairInvoice));

        //Assert
        verify(invoiceRepository, times(1)).findInvoiceByService(repair);
    }

    @Test
    void checkIfServiceLineIsAvailableForInvoicingWhenNotAvailableThrowBadRequestException() {
        //Arrange create mock objects for test
        Repair repair = new Repair (1L, null , ServiceStatus.UITVOEREN ,null,"keuring auto", null, null);
        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.BETAALD,100.0f,0.21f,21.0f,121,"D:/test/invoice",null,null,repair);

        //Arrange create mock objects for test
        when(serviceRepository.findById(1L)).thenReturn(repair);

        //Act
        assertThrows(NullPointerException.class, () -> repairInvoiceService.checkIfServiceLineIsAvailableForInvoicing(repairInvoice));

        //Assert
        verify(serviceRepository, times(1)).findById(1L);
    }


    @Test
    void getInvoiceByIdTest() {
        //Arrange create mock objects for test
        Repair repair = new Repair();
        List<ServiceLine> serviceLines = new ArrayList<>();
        Customer customer = new Customer();

        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.BETAALD, 100.0f, 0.21f, 21.0f, 121, "D:/test/invoice", serviceLines, customer, repair);

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
    void deleteInvoiceByIdWhenInvoiceStatusOpenThenExecute() {
        //Arrange => check if idInvoice exists and return boolean true to pass BadRequestException check
        Repair repair = new Repair();
        List<ServiceLine> serviceLines = new ArrayList<>();
        Customer customer = new Customer();

        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.OPEN, 100.0f, 0.21f, 21.0f, 121, "D:/test/invoice", serviceLines, customer, repair);

        when(invoiceRepository.existsById(1L)).thenReturn(true);
        when(invoiceRepository.findById(1L)).thenReturn(repairInvoice);

        //Act => call method deleteInvoiceById
        repairInvoiceService.deleteInvoiceById(1L);

        //Assert => verify if mock invoiceRepository.deleteById has been called one time
        verify(invoiceRepository, times(1)).existsById(1L);
        verify(invoiceRepository, times(1)).deleteById(1L);
        verify(invoiceRepository, times(1)).findById(1L);
    }

    @Test
    void deleteInvoiceByIdWhenInvoiceStatusBetaaldThenThrowBadRequest() {
        //Arrange => check if idInvoice exists and return boolean true to pass BadRequestException check
        Repair repair = new Repair();
        List<ServiceLine> serviceLines = new ArrayList<>();
        Customer customer = new Customer();

        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.BETAALD, 100.0f, 0.21f, 21.0f, 121, "D:/test/invoice", serviceLines, customer, repair);

        when(invoiceRepository.existsById(1L)).thenReturn(true);
        when(invoiceRepository.findById(1L)).thenReturn(repairInvoice);

        //Act => call method deleteInvoiceById
        assertThrows(BadRequestException.class, () -> repairInvoiceService.deleteInvoiceById(1L));

        //Assert => verify if mock invoiceRepository.deleteById has been called one time
        verify(invoiceRepository, times(1)).existsById(1L);
        verify(invoiceRepository, times(1)).findById(1L);
    }

    @Test
    void updateInvoiceById() {
        //Arrange create mock objects for test
        Repair repair = new Repair();
        List<ServiceLine> serviceLines = new ArrayList<>();
        Customer customer = new Customer();

        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.BETAALD, 100.0f, 0.21f, 21.0f, 121, "D:/test/invoice", serviceLines, customer, repair);

        when(invoiceRepository.existsById(1L)).thenReturn(true);
        when(invoiceRepository.findById(1L)).thenReturn(repairInvoice);


        //Assert
        //Ensure NullPointerException is Thrown and tested by setting Qty to 0
        assertThrows(BadRequestException.class, () -> repairInvoiceService.updateInvoiceById(1L, repairInvoice));
        verify(invoiceRepository, times(1)).existsById(1L);
        verify(invoiceRepository, times(1)).findById(1L);

        //Rest of the methods invoked in updateInvoiceById() are tested separately by other unit tests in this class
    }


    @Test
    void updateInvoiceStatusByIdTest() {
        //Arrange create mock objects for test
        Repair repair = new Repair();
        List<ServiceLine> serviceLines = new ArrayList<>();
        Customer customer = new Customer();

        //initial invoice with status "OPEN"
        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.OPEN, 100.0f, 0.21f, 21.0f, 121, "D:/test/invoice", serviceLines, customer, repair);

        //update invoice with status "BETAALD"
        RepairInvoice updateRepairInvoice = new RepairInvoice(1L, InvoiceStatus.BETAALD, 100.0f, 0.21f, 21.0f, 121, "D:/test/invoice", serviceLines, customer, repair);

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
        Customer customer = new Customer(1L, "Voornaam", "Achternaam", "06", "voornaam.achternaam@mail");
        LocalDate date = LocalDate.of(2020, 6, 8);
        Repair repair = new Repair(1L, date, ServiceStatus.UITVOEREN, customer, "banden profiel te laag < 2mm, remmen achter vervangen", null, null);


        //initial invoice with customer null"
        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.OPEN, 100.0f, 0.21f, 21.0f, 121, "D:/test/invoice", serviceLines, null, repair);

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
        Customer customer = new Customer(1L, "Voornaam", "Achternaam", "06", "voornaam.achternaam@mail");
        LocalDate date = LocalDate.of(2020, 6, 8);

        Repair repair = new Repair(1L, date, ServiceStatus.UITVOEREN, customer, "banden profiel te laag < 2mm, remmen achter vervangen", serviceLines, null);
        ServiceLine serviceLine = new ServiceLine(1L, 1L, 2, "Test Item", 100.0f, 200.0f, 0.21f, 42.0f, 0.0f, null, repair, null);
        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.OPEN, 0.0f, 0.21f, 0.0f, 0.0f, "D:/test/invoice", serviceLines, null, repair);

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
        Customer customer = new Customer(1L, "Voornaam", "Achternaam", "06", "voornaam.achternaam@mail");
        LocalDate date = LocalDate.of(2020, 6, 8);

        Repair repair = new Repair(1L, date, ServiceStatus.UITVOEREN, customer, "banden profiel te laag < 2mm, remmen achter vervangen", serviceLines, null);
        ServiceLine serviceLine = new ServiceLine(1L, 1L, 2, "Test Item", 100.0f, 200.0f, 0.21f, 42.0f, 0.0f, null, repair, null);
        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.OPEN, 0.0f, 0.21f, 0.0f, 0.0f, "D:/test/invoice", serviceLines, null, repair);

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
        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.OPEN, 200.0f, 0.21f, 42.0f, 0.0f, "D:/test/invoice", null, null, null);

        when(invoiceRepository.save(repairInvoice)).thenReturn(repairInvoice);

        //Act
        Invoice validateInvoice = repairInvoiceService.calculateInvoiceTotal(repairInvoice);

        //Assert
        verify(invoiceRepository, times(2)).save(repairInvoice);
        assertThat(validateInvoice.getInvoiceTotal()).isEqualTo(242.0f);
    }


    @Test
    void printInvoiceHeaderTest() throws IOException {
        //Arrange create mock objects for test
        List<ServiceLine> serviceLines = new ArrayList<>();
        Customer customer = new Customer(1L,"Voornaam", "Achternaam","06","voornaam.achternaam@mail");
        LocalDate date = LocalDate.of(2020, 6, 8);
        Files.createDirectories(Paths.get("src/test/resources/"));

        Repair repair = new Repair (1L, date , ServiceStatus.UITVOEREN ,customer,"banden profiel te laag < 2mm, remmen achter vervangen", serviceLines, null);
        ServiceLine serviceLine = new ServiceLine(1L,1L,2,"Test Item",100.0f,200.0f,0.21f,42.0f,242.0f,null,repair,null);
        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.OPEN,200.0f,0.21f,42.0f,242.0f, "src/test/resources/repairInvoiceTest.txt",serviceLines,customer,repair);

        serviceLines.add(serviceLine);

        when(invoiceRepository.save(repairInvoice)).thenReturn(repairInvoice);
        when(serviceRepository.findById(1L)).thenReturn(repair);

        //Act
        repairInvoiceService.printInvoiceHeader(repairInvoice);

        //Assert
        verify(serviceRepository, times(1)).findById(1L);
        verify(invoiceRepository,times(1)).save(invoiceCaptor.capture());
        Invoice validateInvoice = invoiceCaptor.getValue();

        assertThat(validateInvoice.getPathName()).isEqualTo("src/test/resources/repairInvoiceTest.txt");
    }


    @Test
    void printInvoiceLinesTest() throws IOException {
        //Arrange create mock objects for test
        List<ServiceLine> serviceLines = new ArrayList<>();
        Customer customer = new Customer(1L,"Voornaam", "Achternaam","06","voornaam.achternaam@mail");
        LocalDate date = LocalDate.of(2020, 6, 8);
        Files.createDirectories(Paths.get("src/test/resources/"));

        Repair repair = new Repair (1L, date , ServiceStatus.UITVOEREN ,customer,"banden profiel te laag < 2mm, remmen achter vervangen", serviceLines, null);
        ServiceLine serviceLine = new ServiceLine(1L,1L,2,"Test Item",100.0f,200.0f,0.21f,42.0f,242.0f,null,repair,null);
        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.OPEN,200.0f,0.21f,42.0f,242.0f, "src/test/resources/repairInvoiceTest.txt",serviceLines,customer,repair);

        serviceLines.add(serviceLine);

        when(invoiceRepository.save(repairInvoice)).thenReturn(repairInvoice);
        when(serviceRepository.findById(1L)).thenReturn(repair);
        when(serviceLineRepository.findById(1L)).thenReturn(serviceLines.get(0));
        when(serviceLineRepository.countByServiceIdService(1L)).thenReturn(serviceLines.get(0).getIdServiceLine());

        //Act
        repairInvoiceService.printInvoiceLines(repairInvoice);

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

    @Test
    void printInvoiceTotalTest() throws IOException {
        //Arrange create mock objects for test
        List<ServiceLine> serviceLines = new ArrayList<>();
        Customer customer = new Customer(1L,"Voornaam", "Achternaam","06","voornaam.achternaam@mail");
        LocalDate date = LocalDate.of(2020, 6, 8);
        Files.createDirectories(Paths.get("src/test/resources/"));

        Repair repair = new Repair (1L, date , ServiceStatus.UITVOEREN ,customer,"banden profiel te laag < 2mm, remmen achter vervangen", serviceLines, null);
        ServiceLine serviceLine1 = new ServiceLine(1L,1L,2,"Test Item",100.0f,200.0f,0.21f,42.0f,242.0f,null,repair,null);
        ServiceLine serviceLine2 = new ServiceLine(2L,2L,3,"Test Item",100.0f,300.0f,0.21f,63.0f,363.0f,null,repair,null);
        RepairInvoice repairInvoice = new RepairInvoice(1L, InvoiceStatus.OPEN,500.0f,0.21f,105.0f,605.0f, "src/test/resources/repairInvoiceTest.txt",serviceLines,customer,repair);

        serviceLines.add(serviceLine1);
        serviceLines.add(serviceLine2);

        //Act
        repairInvoiceService.printInvoiceTotal(repairInvoice);

        //Assert
        assertThat(repairInvoice.getIdInvoice()).isEqualTo(1L);
        assertThat(repairInvoice.getInvoiceStatus()).isEqualTo(InvoiceStatus.OPEN);
        assertThat(repairInvoice.getInvoiceSubtotal()).isEqualTo(500.0f);
        assertThat(repairInvoice.getVatAmount()).isEqualTo(105.0f);
        assertThat(repairInvoice.getInvoiceTotal()).isEqualTo(605.0f);
        assertThat(repairInvoice.getPathName()).isEqualTo("src/test/resources/repairInvoiceTest.txt");
    }

}