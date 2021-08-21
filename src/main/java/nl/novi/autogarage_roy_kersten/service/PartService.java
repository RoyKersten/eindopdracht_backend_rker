package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.model.Part;

import java.util.List;

public interface PartService {

    //Methods
    List<Part> getAllParts();
    void updatePartById(long idService, Part part);

}
