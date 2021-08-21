package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.model.Item;
import nl.novi.autogarage_roy_kersten.repository.ItemRepository;

/**
 * The ItemServiceImpl class implements the methods defined in the ItemService Interface.
 * The ItemServiceImpl class receives information via this interface from the ItemController class, adds business logic and
 * communicates with the ItemRepository interface.
 * */

public abstract class ItemServiceImpl implements ItemService {

    //Attributes
    private ItemRepository itemRepository;

    //Constructors
    // public ItemService() {}

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    //Methods
    //Create a new Item
    @Override
    public long addItem(Item item) {
        Item storedItem = itemRepository.save(item);
        return storedItem.getIdItem();
    }

    //Get item by idItem
    @Override
    public Item getItemById(long idItem) {
        if (!itemRepository.existsById(idItem)) {
            throw new RecordNotFoundException("item id does not exists");
        }
        return itemRepository.findById(idItem);
    }

    //Delete Item by idItem
    @Override
    public void deleteItemById(long idItem) {
        if (!itemRepository.existsById(idItem)) {
            throw new BadRequestException("item id does not exists");
        }
        try {
            itemRepository.deleteById(idItem);
        } catch (Exception exception) {
            throw new BadRequestException("item cannot be deleted, most likely item is used in earlier inspection and/or repair service");
        }

    }
}
