package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Activity;
import nl.novi.autogarage_roy_kersten.model.Part;

import java.util.List;

public interface PartService extends ItemService {

    List<Part> getAllParts();
    void updatePartById(long idService, Part part);

}
