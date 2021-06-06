package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.model.Activity;
import nl.novi.autogarage_roy_kersten.model.Item;
import nl.novi.autogarage_roy_kersten.model.Part;
import nl.novi.autogarage_roy_kersten.repository.ActivityRepository;
import nl.novi.autogarage_roy_kersten.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The ActivityServiceImpl class implements the methods defined in the ActivityService Interface and is an intermediate
 * class between the ActivityController class and ActivityRepository class.
 * The ActivityServiceImpl class receives information from the ActivityController class, adds business logic and
 * communicates with / provides information for the ActivityRepository class.
 * <p>
 * In the ActivityServiceImpl class the business logic code is written.
 * Business Logic:
 */


@Service
public class ActivityServiceImpl implements ActivityService {

    //Attributes
    private ActivityRepository activityRepository;


    @Autowired
    //Constructors
    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    //Methods

    //Create a new Activity
    public long addActivity(Activity activity) {
        Activity storedItem = activityRepository.save(activity);
        return storedItem.getIdItem();
    }

    //Get all Activities
    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    //Get item by idItem
    @Override
    public Activity getActivityById(long idItem) {
        if (!activityRepository.existsById(idItem)) {
            throw new RecordNotFoundException();
        }
        return activityRepository.findById(idItem);
    }


    //Delete Car by idItem
    @Override
    public void deleteActivityById(long idItem) {
        if (!activityRepository.existsById(idItem)) {
            throw new BadRequestException();
        }
        activityRepository.deleteById(idItem);
    }


    //Update item by idItem
    @Override
    public void updateActivityById(long idItem, Activity updateItem) {

        if (!activityRepository.existsById(idItem)) {
            throw new BadRequestException();

        }
        Activity storedItem = activityRepository.findById(idItem);
        storedItem.setItemName(updateItem.getItemName());
        storedItem.setQty(updateItem.getQty());
        storedItem.setPrice(updateItem.getPrice());
        storedItem.setItemCategory(updateItem.getItemCategory());
        activityRepository.save(updateItem);
    }

}
