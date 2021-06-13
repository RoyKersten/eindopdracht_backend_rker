package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.model.Activity;
import nl.novi.autogarage_roy_kersten.repository.ActivityRepository;
import nl.novi.autogarage_roy_kersten.repository.ItemRepository;
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
public class ActivityService extends ItemService {

    //Attributes
    private ActivityRepository activityRepository;


    @Autowired
    //Constructors
    public ActivityService(ItemRepository itemRepository, ActivityRepository activityRepository) {
        super(itemRepository);
        this.activityRepository = activityRepository;
    }

    //Methods

    //Update Activity by idItem
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
