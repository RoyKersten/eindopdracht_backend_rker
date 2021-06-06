package nl.novi.autogarage_roy_kersten.service;


import nl.novi.autogarage_roy_kersten.model.Activity;
import nl.novi.autogarage_roy_kersten.model.Part;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The ActivityService Interface defines the methods which should be implemented by the ActivityServiceImpl class
 * */

@Service
public interface ActivityService {

    //Methods
    List<Activity> getAllActivities();
    Activity getActivityById(long idItem);
    long addActivity(Activity activity);
    void deleteActivityById(long idItem);
    void updateActivityById(long idItem, Activity item);
}
