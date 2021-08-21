package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Item;

public interface ItemService {

    //Methods
    Item getItemById (long idItem);
    long addItem (Item item);
    void deleteItemById(long idItem);
}
