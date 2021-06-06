package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.model.Item;
import nl.novi.autogarage_roy_kersten.model.Part;
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
public class PartServiceImpl implements PartService {

    //Attributes
    private PartRepository partRepository;


    @Autowired
    //Constructors
    public PartServiceImpl(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    //Methods

    //Create a new Part
    public long addPart(Part part) {
        if (part.getBrand() == null) {
            throw new BadRequestException("part should contain a brand");
        }
        Part storedItem = partRepository.save(part);
        return storedItem.getIdItem();
    }

    //Get all Parts
    @Override
    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    //Get item by idItem
    @Override
    public Part getPartById(long idItem) {
        if (!partRepository.existsById(idItem)) {
            throw new RecordNotFoundException();
        }
        return partRepository.findById(idItem);
    }


    //Delete Car by idItem
    @Override
    public void deletePartById(long idItem) {
        if (!partRepository.existsById(idItem)) {
            throw new BadRequestException();
        }
        partRepository.deleteById(idItem);
    }


    //Update item by idItem
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
        storedItem.setBrand(updateItem.getBrand());
        partRepository.save(updateItem);
    }

}
