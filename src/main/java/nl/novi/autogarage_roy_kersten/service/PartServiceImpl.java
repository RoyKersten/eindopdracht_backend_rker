package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.model.Part;
import nl.novi.autogarage_roy_kersten.repository.ItemRepository;
import nl.novi.autogarage_roy_kersten.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The PartServiceImpl class implements the methods defined in the PartService Interface and is an intermediate
 * class between the PartController class and PartRepository class.
 * The PartServiceImpl class receives information from the PartController class, adds business logic and
 * communicates with / provides information for the PartRepository class.
 * <p>
 * In the PartServiceImpl class the business logic code is written.
 * Business Logic:
 */

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
            throw new BadRequestException();

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
