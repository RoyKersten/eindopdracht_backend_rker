package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.model.Part;
import nl.novi.autogarage_roy_kersten.repository.ItemRepository;
import nl.novi.autogarage_roy_kersten.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The PartServiceImpl class implements the methods defined in the PartService Interface.
 * The PartServiceImpl class receives information via this interface from the PartController class, adds business logic and
 * communicates with the PartRepository interface.
 * */

@Service
public class PartServiceImpl extends ItemServiceImpl implements PartService {

    //Attributes
    private PartRepository partRepository;

    @Autowired
    //Constructors
    public PartServiceImpl(ItemRepository itemRepository, PartRepository partRepository) {
        super (itemRepository);
        this.partRepository = partRepository;
    }

    //Methods
    //Get all Parts
    @Override
    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    //Update Part by idItem
    @Override
    public void updatePartById(long idItem, Part updateItem) {
        if (!partRepository.existsById(idItem)) {
            throw new BadRequestException("item id does not exists");
        }
        Part storedItem = partRepository.findById(idItem);
        storedItem.setItemName(updateItem.getItemName());
        storedItem.setQty(updateItem.getQty());
        storedItem.setPrice(updateItem.getPrice());
        storedItem.setItemCategory(updateItem.getItemCategory());
        storedItem.setBrand(updateItem.getBrand());                             // Brand is specific for class Part
        partRepository.save(updateItem);
    }

}
