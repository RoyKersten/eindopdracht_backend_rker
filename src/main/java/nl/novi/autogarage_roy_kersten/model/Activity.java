package nl.novi.autogarage_roy_kersten.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("activity")
public class Activity extends Item {

    //Constructors
    public Activity() {}

    public Activity(Long idItem, String itemName, int qty, float price, String itemCategory,ItemStatus status) {
        super(idItem, itemName, qty, price,itemCategory,status);
    }

}
