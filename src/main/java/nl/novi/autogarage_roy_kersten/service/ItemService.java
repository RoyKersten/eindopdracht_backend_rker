package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.model.Item;
import nl.novi.autogarage_roy_kersten.repository.ItemRepository;

import java.util.List;

public abstract class ItemService {

    //Attributes
    private ItemRepository itemRepository;


    //Constructors
   // public ItemService() {}

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    //Methods

    //Create a new Item
    public long addItem(Item item) {
        Item storedItem = itemRepository.save(item);
        return storedItem.getIdItem();
    }

    //Get all Item

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    //Get item by idItem
    public Item getItemById(long idItem) {
        if (!itemRepository.existsById(idItem)) {
            throw new RecordNotFoundException();
        }
        return itemRepository.findById(idItem);
    }


    //Delete Item by idItem
    public void deleteItemById(long idItem) {
        if (!itemRepository.existsById(idItem)) {
            throw new BadRequestException();
        }
        itemRepository.deleteById(idItem);
    }



}
