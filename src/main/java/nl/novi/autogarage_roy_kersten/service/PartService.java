package nl.novi.autogarage_roy_kersten.service;


import nl.novi.autogarage_roy_kersten.model.Item;
import nl.novi.autogarage_roy_kersten.model.Part;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The PartService Interface defines the methods which should be implemented by the PartServiceImpl class
 * */

@Service
public interface PartService {

    //Methods
    List<Part> getAllParts();
    Part getPartById(long idItem);
    long addPart(Part part);
    void deletePartById(long idItem);
    void updatePartById(long idItem, Part item);
}
