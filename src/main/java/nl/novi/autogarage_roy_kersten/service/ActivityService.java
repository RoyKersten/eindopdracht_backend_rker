package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Activity;

import java.util.List;

public interface ActivityService  {

    //Methods
    List<Activity> getAllActivities();
    void updateActivityById(long idService, Activity activity);
}
