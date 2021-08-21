package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Activity;
import nl.novi.autogarage_roy_kersten.model.ItemStatus;
import nl.novi.autogarage_roy_kersten.repository.ActivityRepository;
import nl.novi.autogarage_roy_kersten.repository.ItemRepository;
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
public class ActivityServiceTest {

    @Mock
    ItemRepository itemRepository;

    @Mock
    ActivityRepository activityRepository;

    @InjectMocks
    ActivityServiceImpl activityService;

    @Captor
    ArgumentCaptor<Activity> activityCaptor;

    //Method is inherit from Item but needs to contain characteristics of an Activity;
    @Test
    void addItemTest() {
        //Arrange => create Item as input for test
        Activity storedItem = new Activity(1L, "keuring auto", 1, 45.00f, "keuring", ItemStatus.LOCKED);
        when(itemRepository.save(storedItem)).thenReturn(storedItem);

        //Act => call method addItem
        activityService.addItem(storedItem);

        //Assert => capture argument and perform assertions
        verify(itemRepository).save(activityCaptor.capture());
        Activity validateActivity = activityCaptor.getValue();

        assertThat(validateActivity.getIdItem()).isEqualTo(1L);
        assertThat(validateActivity.getItemName()).isEqualTo("keuring auto");
        assertThat(validateActivity.getQty()).isEqualTo(1);
        assertThat(validateActivity.getPrice()).isEqualTo(45.00f);
        assertThat(validateActivity.getItemCategory()).isEqualTo("keuring");
        assertThat(validateActivity.getStatus()).isEqualTo(ItemStatus.LOCKED);
    }

    //Method is inherit from Item but needs to contain characteristics of an Activity;
    @Test
    void getItemByIdTest() {
        //Arrange => create Item object as input for test
        Activity storedItem = new Activity(1L, "keuring auto", 1, 45.00f, "keuring",ItemStatus.LOCKED);
        when(itemRepository.existsById(1L)).thenReturn(true);
        when(itemRepository.findById(1L)).thenReturn(storedItem);

        //Act => call method getItemById
        Activity validateActivity = (Activity) activityService.getItemById(1L);

        //Assert => validate return of method getItemById and perform assertions
        assertThat(validateActivity.getIdItem()).isEqualTo(1L);
        assertThat(validateActivity.getItemName()).isEqualTo("keuring auto");
        assertThat(validateActivity.getQty()).isEqualTo(1);
        assertThat(validateActivity.getPrice()).isEqualTo(45.00f);
        assertThat(validateActivity.getItemCategory()).isEqualTo("keuring");
        assertThat(validateActivity.getStatus()).isEqualTo(ItemStatus.LOCKED);
    }

    //Method is inherit from Item but needs to contain characteristics of an Activity;
    @Test
    void deleteItemByIdTest() {
        //Arrange => check if idItem exists and return boolean true to pass BadRequestException check
        when(itemRepository.existsById(1L)).thenReturn(true);

        //Act => call method deleteItemById
        activityService.deleteItemById(1L);

        //Assert => verify if mock itemRepository.deleteById has been called one time
        verify(itemRepository, times(1)).deleteById(1L);
    }

    //Method is defined as Activity
    @Test
    void getAllActivitiesTest() {
        Activity activity1 = new Activity(1L, "keuring auto", 1, 45.00f, "keuring",ItemStatus.LOCKED);
        Activity activity2 = new Activity(2L, "vervangen remmen", 2, 145.00f, "remmen",ItemStatus.LOCKED);
        Activity activity3 = new Activity(3L, "verversen olie longlife", 1, 40.00f, "algemeen",ItemStatus.LOCKED);

        List<Activity> activities = new ArrayList<>();
        activities.add(activity1);
        activities.add(activity2);
        activities.add(activity3);
        when(activityRepository.findAll()).thenReturn(activities);


        //Act => call method getAllActivities
        List<Activity> validateActivity = activityService.getAllActivities();

        //Assert => validate the return of method getAllActivities and perform assertions
        assertThat(validateActivity.size()).isEqualTo(3);                                                                  //check if the size of the List is 3

        assertThat(validateActivity.get(0).getIdItem()).isEqualTo(1L);
        assertThat(validateActivity.get(0).getItemName()).isEqualTo("keuring auto");
        assertThat(validateActivity.get(0).getQty()).isEqualTo(1);
        assertThat(validateActivity.get(0).getPrice()).isEqualTo(45.00f);
        assertThat(validateActivity.get(0).getItemCategory()).isEqualTo("keuring");
        assertThat(validateActivity.get(0).getStatus()).isEqualTo(ItemStatus.LOCKED);
    }

    //Method is defined as Activity
    @Test
    void updateActivityByIdTest() {
        //Arrange => create updateActivity and storedActivity object as input for test
        Activity updateActivity = new Activity(1L, "keuring auto", 1, 45.00f, "keuring",ItemStatus.LOCKED);
        Activity storedActivity = new Activity(1L, "vervangen remmen", 2, 145.00f, "remmen",ItemStatus.LOCKED);

        when(activityRepository.existsById(1L)).thenReturn(true);
        when(activityRepository.findById(1L)).thenReturn(storedActivity);

        //Act => call method updateActivityById
        activityService.updateActivityById(1L, updateActivity);

        //Assert => check if storedActivity has now the values of updateActivity
        assertThat(storedActivity.getIdItem()).isEqualTo(1L);
        assertThat(storedActivity.getItemName()).isEqualTo("keuring auto");
        assertThat(storedActivity.getQty()).isEqualTo(1);
        assertThat(storedActivity.getPrice()).isEqualTo(45.00f);
        assertThat(storedActivity.getItemCategory()).isEqualTo("keuring");
        assertThat(storedActivity.getStatus()).isEqualTo(ItemStatus.LOCKED);
    }

}
