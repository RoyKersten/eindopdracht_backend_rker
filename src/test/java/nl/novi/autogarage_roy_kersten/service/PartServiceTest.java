package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.ItemStatus;
import nl.novi.autogarage_roy_kersten.model.Part;
import nl.novi.autogarage_roy_kersten.repository.ItemRepository;
import nl.novi.autogarage_roy_kersten.repository.PartRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PartServiceTest {

    @Mock
    ItemRepository itemRepository;

    @Mock
    PartRepository partRepository;

    @InjectMocks
    PartServiceImpl partService;

    @Captor
    ArgumentCaptor<Part> partCaptor;


    //Method is inherit from Item but needs to contain characteristics of a Part
    @Test
    void addItemTest() {
        //Arrange => create Item as input for test
        Part storedItem = new Part (1L,"zomerband 205/55/R16", 1, 55.0f,"Goodyear", "banden", ItemStatus.LOCKED);
        when(itemRepository.save(storedItem)).thenReturn(storedItem);                                                   //itemRepository because of inheritance

        //Act => call method addItem
        partService.addItem(storedItem);                                                                                //storedItem needs to have characteristics of a Part (including Brand)

        //Assert => capture argument and perform assertions
        verify(itemRepository).save(partCaptor.capture());                                                              //itemRepository because of inheritance
        Part validatePart = partCaptor.getValue();                                                                      //itemRepository because of inheritance

        assertThat(validatePart.getIdItem()).isEqualTo(1L);
        assertThat(validatePart.getItemName()).isEqualTo("zomerband 205/55/R16");
        assertThat(validatePart.getQty()).isEqualTo(1);
        assertThat(validatePart.getPrice()).isEqualTo(55.00f);
        assertThat(validatePart.getBrand()).isEqualTo("Goodyear");
        assertThat(validatePart.getItemCategory()).isEqualTo("banden");
        assertThat(validatePart.getStatus()).isEqualTo(ItemStatus.LOCKED);

    }

    //Method is inherit from Item but needs to contain characteristics of a Part
    @Test
    void getItemByIdTest() {
        //Arrange => create Item object as input for test
        Part storedItem = new Part (1L,"zomerband 205/55/R16", 1, 55.0f,"Goodyear", "banden",ItemStatus.LOCKED);
        when(itemRepository.existsById(1L)).thenReturn(true);
        when(itemRepository.findById(1L)).thenReturn(storedItem);

        //Act => call method getItemById
        Part validatePart = (Part) partService.getItemById(1L);                                                   //validatePart needs to have characteristics of a Part (including Brand)

        //Assert => validate return of method getItemById and perform assertions
        assertThat(validatePart.getIdItem()).isEqualTo(1L);
        assertThat(validatePart.getItemName()).isEqualTo("zomerband 205/55/R16");
        assertThat(validatePart.getQty()).isEqualTo(1);
        assertThat(validatePart.getPrice()).isEqualTo(55.00f);
        assertThat(validatePart.getBrand()).isEqualTo("Goodyear");                                                      //Brand is specific for Part object not for Activity
        assertThat(validatePart.getItemCategory()).isEqualTo("banden");
        assertThat(validatePart.getStatus()).isEqualTo(ItemStatus.LOCKED);
    }

    //Method is inherit from Item but needs to contain characteristics of a Part
    @Test
    void deleteItemByIdTest() {
        //Arrange => check if idItem exists and return boolean true to pass BadRequestException check
        when(itemRepository.existsById(1L)).thenReturn(true);                                                            //itemRepository because of inheritance

        //Act => call method deleteItemById
        partService.deleteItemById(1L);

        //Assert => verify if mock itemRepository.deleteById has been called one time
        verify(itemRepository, times(1)).deleteById(1L);                                          //itemRepository because of inheritance
    }

    //Method is defined as Part
    @Test
    void getAllPartsTest() {
        Part part1 = new Part(1L, "zomerband 205/55/R16", 1, 55.0f, "Goodyear", "banden", ItemStatus.LOCKED);
        Part part2 = new Part(2L, "winterbandband 200/55/R16", 2, 145.0f, "Vredestein", "banden", ItemStatus.LOCKED);
        Part part3 = new Part(3L, "remschijf achter", 1, 199.0f, "Bosch", "remmen", ItemStatus.LOCKED);

        List<Part> parts = new ArrayList<>();
        parts.add(part1);
        parts.add(part2);
        parts.add(part3);
        when(partRepository.findAll()).thenReturn(parts);


        //Act => call method getAllParts
        List<Part> validateParts = partService.getAllParts();

        //Assert => validate the return of method getAllParts and perform assertions
        assertThat(validateParts.size()).isEqualTo(3);                                                                  //check if the size of the List is 3

        assertThat(validateParts.get(0).getIdItem()).isEqualTo(1L);
        assertThat(validateParts.get(0).getItemName()).isEqualTo("zomerband 205/55/R16");
        assertThat(validateParts.get(0).getQty()).isEqualTo(1);
        assertThat(validateParts.get(0).getPrice()).isEqualTo(55.00f);
        assertThat(validateParts.get(0).getBrand()).isEqualTo("Goodyear");                                              //Brand is specific for Part object not for Activity
        assertThat(validateParts.get(0).getItemCategory()).isEqualTo("banden");
        assertThat(validateParts.get(0).getStatus()).isEqualTo(ItemStatus.LOCKED);
    }

    //Method is defined as Part
    @Test
    void updatePartByIdTest() {
        //Arrange => create updatePart and storedPart object as input for test
        Part updatePart = new Part (1L,"zomerband 205/55/R16", 1, 55.0f,"Goodyear", "banden",ItemStatus.LOCKED);
        Part storedPart = new Part (1L,"winterbandband 200/55/R16", 2, 145.0f,"Vredestein", "banden",ItemStatus.LOCKED);

        when(partRepository.existsById(1L)).thenReturn(true);
        when(partRepository.findById(1L)).thenReturn(storedPart);

        //Act => call method updatePartById
        partService.updatePartById(1L, updatePart);

        //Assert => check if storedPart has now the values of updatePart
        assertThat(storedPart.getIdItem()).isEqualTo(1L);
        assertThat(storedPart.getItemName()).isEqualTo("zomerband 205/55/R16");
        assertThat(storedPart.getQty()).isEqualTo(1);
        assertThat(storedPart.getPrice()).isEqualTo(55.00f);
        assertThat(storedPart.getBrand()).isEqualTo("Goodyear");                                                      //Brand is specific for Part object not for Activity
        assertThat(storedPart.getItemCategory()).isEqualTo("banden");
        assertThat(storedPart.getStatus()).isEqualTo(ItemStatus.LOCKED);
    }

}
