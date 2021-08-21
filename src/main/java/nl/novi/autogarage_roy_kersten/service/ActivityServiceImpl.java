package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.model.Activity;
import nl.novi.autogarage_roy_kersten.repository.ActivityRepository;
import nl.novi.autogarage_roy_kersten.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The ActivityServiceImpl class implements the methods defined in the ActivityService Interface.
 * The ActivityServiceImpl class receives information via this interface from the ActivityController class, adds business logic and
 * communicates with the ActivityRepository interface.
* */

@Service
public class ActivityServiceImpl extends ItemServiceImpl implements ActivityService {

    //Attributes
    private ActivityRepository activityRepository;

    @Autowired
    //Constructors
    public ActivityServiceImpl(ItemRepository itemRepository, ActivityRepository activityRepository) {
        super(itemRepository);
        this.activityRepository = activityRepository;
    }

    //Methods
    //Get all Activities
    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    //Update Activity by idItem
    @Override
    public void updateActivityById(long idItem, Activity updateItem) {
        if (!activityRepository.existsById(idItem)) {
            throw new BadRequestException("item id does not exists");
        }
        Activity storedItem = activityRepository.findById(idItem);
        storedItem.setItemName(updateItem.getItemName());
        storedItem.setQty(updateItem.getQty());
        storedItem.setPrice(updateItem.getPrice());
        storedItem.setItemCategory(updateItem.getItemCategory());
        activityRepository.save(updateItem);
    }

}
