package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Activity;
import nl.novi.autogarage_roy_kersten.model.Repair;

import java.util.List;

public interface ActivityService extends ItemService {

    List<Activity> getAllActivities();
    void updateActivityById(long idService, Activity activity);
}
