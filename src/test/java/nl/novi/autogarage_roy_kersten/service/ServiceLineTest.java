package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.model.*;
import nl.novi.autogarage_roy_kersten.repository.ItemRepository;
import nl.novi.autogarage_roy_kersten.repository.ServiceLineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceLineTest {

    @Mock
    ServiceLineRepository serviceLineRepository;

    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    ServiceLineServiceImpl serviceLineService;

    @Captor
    ArgumentCaptor<ServiceLine> serviceLineCaptor;

    @Test
    void addServiceLineWhenQtyIsLessThenOneThrowBadRequestException() {
        ServiceLine serviceLine = new ServiceLine();

        //Ensure BadRequestException is Thrown and tested by setting Qty to 0
        serviceLine.setQty(0);
        assertThrows(BadRequestException.class, () -> serviceLineService.addServiceLine(serviceLine));

        //Rest of the methods invoked in addServiceLine() are tested separately by other unit tests in this class
    }

    @Test
    void getAllServiceLinesTest() {
        //Arrange
        ServiceLine serviceLine1 = new ServiceLine();
        ServiceLine serviceLine2 = new ServiceLine();
        List<ServiceLine> serviceLines = new ArrayList<>();
        serviceLines.add(serviceLine1);
        serviceLines.add(serviceLine2);

        when(serviceLineRepository.findAll()).thenReturn(serviceLines);

        //Act
        List<ServiceLine> validateServiceLines = serviceLineService.getAllServiceLines();

        //Assert
        verify(serviceLineRepository, times(1)).findAll();
        assertThat(validateServiceLines.size()).isEqualTo(2);
    }

    @Test
    void getServiceLineByIdTest() {
        //Arrange => create mock serviceLine object as input for test
        ServiceLine serviceLine = new ServiceLine(1L,1L,2,"Test Item",100.0f,100.0f,0.21f,21.0f,121.0f,null,null,null);
        when(serviceLineRepository.existsById(1L)).thenReturn(true);
        when(serviceLineRepository.findById(1L)).thenReturn(serviceLine);

        //Act
        ServiceLine validateServiceLine = serviceLineService.getServiceLineById(1L);

        //Assert
        verify(serviceLineRepository, times(1)).findById(1L);
        assertThat(validateServiceLine.getIdServiceLine()).isEqualTo(1L);
        assertThat(validateServiceLine.getQty()).isEqualTo(2);
        assertThat(validateServiceLine.getServiceLineNumber()).isEqualTo(1L);
        assertThat(validateServiceLine.getItemName()).isEqualTo("Test Item");
        assertThat(validateServiceLine.getPrice()).isEqualTo(100.0f);
        assertThat(validateServiceLine.getLineSubTotal()).isEqualTo(100.0f);
        assertThat(validateServiceLine.getVatRate()).isEqualTo(0.21f);
        assertThat(validateServiceLine.getVatAmount()).isEqualTo(21.0f);
        assertThat(validateServiceLine.getLineTotal()).isEqualTo(121.0f);
    }

    @Test
    void deleteServiceLineByIdTestWhenInvoiceIsNotNullThenThrowBadRequest() {
        //Arrange => check if idServiceLine exists and return boolean true to pass BadRequestException check. invoice is not null, BadRequestException will be thrown
        Invoice invoice = new InspectionInvoice();
        ServiceLine serviceLine = new ServiceLine(1L,1L,5,"Test Item",100.0f,200.0f,0.21f,42.0f,0.0f,null,null,invoice);

        when(serviceLineRepository.existsById(1L)).thenReturn(true);
        when(serviceLineRepository.findById(1L)).thenReturn(serviceLine);

        //Act => call method deleteServiceLineById
        assertThrows(BadRequestException.class, () ->  serviceLineService.deleteServiceLineById(1L));
    }

    @Test
    void deleteServiceLineByIdTestWhenInvoiceIsNullThenThrowNullPointerException() {
        //Arrange => check if idServiceLine exists and return boolean true to pass BadRequestException check
        ServiceLine serviceLine = new ServiceLine(1L,1L,5,"Test Item",100.0f,200.0f,0.21f,42.0f,0.0f,null,null,null);

        when(serviceLineRepository.existsById(1L)).thenReturn(true);
        when(serviceLineRepository.findById(1L)).thenReturn(serviceLine);

        //Act => call method deleteServiceLineById
        assertThrows(NullPointerException.class, () ->  serviceLineService.deleteServiceLineById(1L));

        //!!! Method: updateInventoryAfterDeleteServiceLine(idServiceLine) is Tested separately (see last unit test in this class) !
    }



    @Test
    void updateServiceLineByIdTest() {
        //Arrange
        ServiceLine serviceLine = new ServiceLine(1L,1L,5,"Test Item",100.0f,200.0f,0.21f,42.0f,0.0f,null,null,null);

        when(serviceLineRepository.existsById(1L)).thenReturn(true);
        when(serviceLineRepository.findById(1L)).thenReturn(serviceLine);

        //Assert
        //Ensure BadRequestException is Thrown and tested by setting Qty to 0
        serviceLine.setQty(0);
        assertThrows(BadRequestException.class, () -> serviceLineService.updateServiceLineById(1L, serviceLine));
        verify(serviceLineRepository, times(1)).existsById(1L);
        verify(serviceLineRepository, times(1)).findById(1L);

        //Rest of the methods invoked in addServiceLine() are tested separately by other unit tests in this class
    }

    @Test
    void reverseInventoryInitialTransactionInCaseOfPartTest() {
        //Arrange
        //To succeed the test new part Qty should be = 5 (inventory qty = 3, serviceLine qty = 2)
        Part initialPart = new Part (1L, "stored Test Item", 3, 100.0f, "Test", "Test",ItemStatus.LOCKED);
        ServiceLine storedServiceLine = new ServiceLine(1L,1L,2,"initial Test Item",100.0f,100.0f,0.21f,21.0f,121.0f,initialPart,null,null);

        when(itemRepository.findById(1L)).thenReturn(initialPart);
        when(serviceLineRepository.findById(1L)).thenReturn(storedServiceLine);

        //Act
        serviceLineService.reverseInventoryInitialTransaction(1L);

        //Assert
        verify(itemRepository, times(1)).findById(1L);                                     //verify if intemRepository.findById is called one time
        verify(serviceLineRepository, times(1)).findById(1L);                        //verify if serviceLineRepository.findById is called one time

        long qty = itemRepository.findById(1L).getQty();                                                          //Get Qty mock item
        assertThat(qty).isEqualTo(5);                                                                                   //check if inventory level has been reversed with Qty of serviceLine

    }

    @Test
    void reverseInventoryInitialTransactionInCaseOfActivityTest() {
        //Arrange
        //To succeed the test new part Qty should be = 5 (inventory qty = 3, serviceLine qty = 2)
        Activity activity = new Activity(1L, "remmen vervangen", 1, 100.00f, "keuring",ItemStatus.LOCKED);
        ServiceLine storedServiceLine = new ServiceLine(1L,1L,2,"remmen vervangen",100.0f,100.0f,0.21f,21.0f,121.0f,activity,null,null);

        when(itemRepository.findById(1L)).thenReturn(activity);
        when(serviceLineRepository.findById(1L)).thenReturn(storedServiceLine);

        //Act
        serviceLineService.reverseInventoryInitialTransaction(1L);

        //Assert
        verify(itemRepository, times(1)).findById(1L);                                     //verify if intemRepository.findById is invoked one time
        verify(serviceLineRepository, times(1)).findById(1L);                        //verify if serviceLineRepository.findById is invoked one time

        long qty = itemRepository.findById(1L).getQty();                                                          //Get Qty mock item
        assertThat(qty).isEqualTo(1);                                                                                   //check if inventory level of Activity has not been reversed with Qty of serviceLine

    }

    @Test
    void checkInventoryLevelNewTransactionPartWhenLessThenZeroThrowBadRequestException() {
        //Arrange =>
        //To succeed the test: stock qty of part = 1, serviceLine qty = 2  (result: 1 - 2 = -1, not sufficient inventory available)
        Part part = new Part (1L, "Test Item", 1, 100.0f, "Test", "Test",ItemStatus.LOCKED);
        ServiceLine serviceLine = new ServiceLine(1L,1L,2,"Test Item",100.0f,100.0f,0.21f,21.0f,121.0f,part,null,null);

        when(itemRepository.findById(1L)).thenReturn(part);

        //Act & Assert
        assertThat(part.getQty()).isLessThan(serviceLine.getQty());                                                     //Compare Qty mock item with serviceLine Qty
        assertThrows(BadRequestException.class, () -> serviceLineService.checkInventoryLevelNewTransaction(serviceLine));             //check if BadRequestException will be Thrown
    }

    @Test
    void checkInventoryLevelNewTransactionPartWhenLargerThenZeroDoNotThrowBadRequestException() {
        //Arrange => stock qty of part = 5, serviceLine qty = 1
        //To succeed the test: stock qty of part = 5, serviceLine qty = 1  (result: 5 - 1 = 4, sufficient inventory available)
        Part part = new Part (1L, "Test Item", 5, 100.0f, "Test", "Test",ItemStatus.LOCKED);
        ServiceLine serviceLine = new ServiceLine(1L,1L,2,"Test Item",100.0f,100.0f,0.21f,21.0f,121.0f,part,null,null);

        when(itemRepository.findById(1L)).thenReturn(part);

        //Act
        serviceLineService.checkInventoryLevelNewTransaction(serviceLine);

        //Assert
        verify(itemRepository, times(1)).findById(1L);                                     //verify if itemRepository.findById has bene called one time
        long qty = itemRepository.findById(1L).getQty();                                                          //Get Qty mock item
        assertThat(qty).isGreaterThan(serviceLine.getQty());                                                            //Compare Qty mock item with serviceLine Qty
    }

    @Test
    void checkInventoryLevelNewTransactionPartWhenEqualToZeroDoNotThrowBadRequestException() {
        //Arrange =>
        //To succeed the test: stock qty of part = 2, serviceLine qty = 2  (result: 2 - 2 = 0, sufficient inventory available)
        Part part = new Part (1L, "Test Item", 2, 100.0f, "Test", "Test",ItemStatus.LOCKED);
        ServiceLine serviceLine = new ServiceLine(1L,1L,2,"Test Item",100.0f,100.0f,0.21f,21.0f,121.0f,part,null,null);

        when(itemRepository.findById(1L)).thenReturn(part);

        //Act
        serviceLineService.checkInventoryLevelNewTransaction(serviceLine);

        //Assert
        verify(itemRepository, times(1)).findById(1L);                                     //verify if itemRepository.findById has bene called one time
        long qty = itemRepository.findById(1L).getQty();                                                          //Get Qty mock item
        assertThat(qty).isEqualTo(serviceLine.getQty());                                                                //compare Qty mock item with serviceLine Qty
    }

    @Test
    void setServiceLineNumberTest() {
        //Arrange => create storedServiceLine as mock object with serviceLineNumber value null, after executing Test, the  serviceLineNumber should be 1L
        Repair storedRepair = new Repair(1L, null, null, null, "uitlaat vervangen", null, null);
        ServiceLine storedServiceLine = new ServiceLine(1L,null,2,"Test Item1",100.0f,100.0f,0.21f,21.0f,121.0f,null,storedRepair,null);

        when(serviceLineRepository.save(storedServiceLine)).thenReturn(storedServiceLine);
        when(serviceLineRepository.countByServiceIdService(storedServiceLine.getService().getIdService())).thenReturn(storedServiceLine.getService().getIdService());

        //Act
        ServiceLine serviceLine = serviceLineService.setServiceLineNumber(storedServiceLine);

        //Assert
        verify(serviceLineRepository, times(2)).save(storedServiceLine);
        verify(serviceLineRepository, times(1)).countByServiceIdService(1L);
        assertThat(serviceLine.getServiceLineNumber()).isEqualTo(1L);                                                   //check if serviceLineNumber is set to 1L
        }

    @Test
    void setServiceLinePriceItemStatusLockedTest() {
        //Arrange => create a serviceLine mock with serviceLine price = 50, price of part is 100.0f, serviceLine price should be 100.0f after test as ItemStatus is "LOCKED"
        Part part = new Part (1L, "Test Item", 2, 100.0f, "Test", "Test",ItemStatus.LOCKED);
        ServiceLine serviceLine = new ServiceLine(1L,1L,2,"Test Item",50.0f,0.0f,0.0f,0.0f,0.0f,part,null,null);

        when(serviceLineRepository.save(serviceLine)).thenReturn(serviceLine);
        when(itemRepository.findById(1L)).thenReturn(part);

        //Act
        ServiceLine validateServiceLine = serviceLineService.setServiceLinePriceItemStatusLocked(serviceLine);

        //Assert
        verify(serviceLineRepository, times(2)).save(serviceLine);
        assertThat(validateServiceLine.getPrice()).isEqualTo(100.0f);                                                   //check if serviceLine price is taken from the Item and from the serviceLine
    }

    @Test
    void setServiceLinePriceItemStatusOpen() {
        //Arrange => create a serviceLine mock with serviceLine price = 100, price of activity is 0.0f, serviceLine price should have 100.0f after test as ItemStatus is "OPEN"
        Activity activity = new Activity(1L, "overige handelingen", 1, 0.00f, "keuring",ItemStatus.OPEN);
        ServiceLine serviceLine = new ServiceLine(1L,1L,2,"remmen vervangen",100.0f,100.0f,0.21f,21.0f,121.0f,activity,null,null);
        ServiceLine storedServiceLine = new ServiceLine(1L,1L,1,"test",20.0f,100.0f,0.21f,21.0f,121.0f,activity,null,null);

        when(serviceLineRepository.save(storedServiceLine)).thenReturn(storedServiceLine);
        when(itemRepository.findById(1L)).thenReturn(activity);

        //Act
        serviceLineService.setServiceLinePriceItemStatusOpen(serviceLine, storedServiceLine);

        //Assert
        verify(serviceLineRepository,times(1)).save(serviceLineCaptor.capture());
        ServiceLine validateServiceLine = serviceLineCaptor.getValue();

        verify(serviceLineRepository, times(1)).save(storedServiceLine);
        assertThat(validateServiceLine.getPrice()).isEqualTo(100.0f);                                                   ///check if serviceLine price fro the storedServiceLine is taken from the serviceLine and not from the item

    }


    @Test
    void calculateLineSubtotalTest() {
        //Arrange => create a serviceLine mock with lineSubtotal = 0.0f
        ServiceLine serviceLine = new ServiceLine(1L,1L,5,"Test Item",100.0f,0.0f,0.0f,0.0f,0.0f,null,null,null);

        when(serviceLineRepository.save(serviceLine)).thenReturn(serviceLine);

        //Act
        ServiceLine validateServiceLine = serviceLineService.calculateLineSubtotal(serviceLine);

        //Assert
        verify(serviceLineRepository, times(2)).save(serviceLine);
        assertThat(validateServiceLine.getLineSubTotal()).isEqualTo(500.0f);                                            //lineSubtotal = Qty * Price => 5 * 100.0f = 500.0f
        }

    @Test
    void setVatRateTest() {
        //Arrange => create a serviceLine mock with vatRate 0.0f (after test vatRate should be set to 0.21f
        ServiceLine serviceLine = new ServiceLine(1L,1L,5,"Test Item",100.0f,0.0f,0.0f,0.0f,0.0f,null,null,null);

        when(serviceLineRepository.save(serviceLine)).thenReturn(serviceLine);

        //Act
        serviceLineService.setVatRate(serviceLine);

        //Assert
        assertThat(serviceLine.getVatRate()).isEqualTo(0.21f);                                                          //Check if vatRate in the serviceLine has been set to 0.21f
    }

    @Test
    void calculateVatAmountTest() {
        //Arrange => create a serviceLine mock with vatAmount 0.0f (after test vatAmount should be set to 21.0f
        ServiceLine serviceLine = new ServiceLine(1L,1L,1,"Test Item",100.0f,100.0f,0.21f,0.0f,0.0f,null,null,null);

        when(serviceLineRepository.save(serviceLine)).thenReturn(serviceLine);

        //Act
        ServiceLine validateServiceLine = serviceLineService.calculateVatAmount(serviceLine);

        //Assert
        verify(serviceLineRepository, times(2)).save(serviceLine);
        assertThat(validateServiceLine.getVatAmount()).isEqualTo(21.0f);                                                //check if VAT amount = 0.21f
    }

    @Test
    void calculateLineTotalTest() {
        //Arrange => create a serviceLine mock with lineTotal 0.0f (after test lineTotal should be lineSubTotal + vatAmount => 200 + 42 = 242.0f )
        ServiceLine serviceLine = new ServiceLine(1L,1L,2,"Test Item",100.0f,200.0f,0.21f,42.0f,0.0f,null,null,null);

        when(serviceLineRepository.save(serviceLine)).thenReturn(serviceLine);

        //Act
        ServiceLine validateServiceLine = serviceLineService.calculateLineTotal(serviceLine);

        //Assert
        verify(serviceLineRepository, times(2)).save(serviceLine);
        assertThat(validateServiceLine.getLineTotal()).isEqualTo(242.0f);                                                //check if lineTotal = 242.0f
    }


    @Test
    void setItemNameTest() {
        //Arrange => create a Item mock and a serviceLine mock with item name null, after test item name in serviceLine should be "Test Item", taken from the item
        Part part = new Part (1L, "Test Item", 2, 100.0f, "Test", "Test",ItemStatus.LOCKED);
        ServiceLine serviceLine = new ServiceLine(1L,1L,2,null,100.0f,200.0f,0.21f,42.0f,0.0f,part,null,null);

        when(serviceLineRepository.save(serviceLine)).thenReturn(serviceLine);
        when(itemRepository.findById(1L)).thenReturn(part);

        //Act
        ServiceLine validateServiceLine = serviceLineService.setItemName(serviceLine);

        //Assert
        verify(serviceLineRepository, times(2)).save(serviceLine);
        assertThat(validateServiceLine.getItemName()).isEqualTo("Test Item");
    }

    @Test
    void updateInventoryTest() {
        //Arrange => create a Item mock and a serviceLine mock with item name null, after test item name in serviceLine should be "Test Item", taken from the item
        //New inventory Qty should be 7 - 5 = 2 to succeed the Test
        Part part = new Part (1L,"Test Item",7,100.0f,"Test","Test",ItemStatus.LOCKED);

        ServiceLine serviceLine = new ServiceLine(1L,1L,5,"Test Item",100.0f,200.0f,0.21f,42.0f,0.0f,part,null,null);

        when(serviceLineRepository.save(serviceLine)).thenReturn(serviceLine);
        when(itemRepository.findById(1L)).thenReturn(part);
        when(itemRepository.save(part)).thenReturn(part);

        //Act
        Item validateItem = serviceLineService.updateInventory(serviceLine);

        //Assert
        verify(serviceLineRepository, times(1)).save(serviceLine);
        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).save(part);

        assertThat(validateItem.getQty()).isEqualTo(2);                                                                 //check if new inventory Qty is equal 2 (7-5)
    }

    @Test
    void updateInventoryAfterDeleteServiceLine() {
        //Arrange => create a Item mock and a serviceLine mock with item name null, after test item name in serviceLine should be "Test Item", taken from the item
        //New inventory Qty should be 7 + 5 = 12 to succeed the Test
        Part part = new Part (1L,"Test Item",7,100.0f,"Test","Test",ItemStatus.LOCKED);
        ServiceLine serviceLine = new ServiceLine(1L,1L,5,"Test Item",100.0f,200.0f,0.21f,42.0f,0.0f,part,null,null);

        when(serviceLineRepository.getById(1L)).thenReturn(serviceLine);
        when(itemRepository.findById(1L)).thenReturn(part);
        when(itemRepository.save(part)).thenReturn(part);

        //Act
        serviceLineService.updateInventoryAfterDeleteServiceLine(1L);

        //Assert
        verify(serviceLineRepository, times(1)).getById(1L);
        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).save(part);
        assertThat(part.getQty()).isEqualTo(12);
    }

}
