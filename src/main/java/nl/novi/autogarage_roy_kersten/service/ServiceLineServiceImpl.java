package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.model.ServiceLine;
import nl.novi.autogarage_roy_kersten.repository.ServiceLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceLineServiceImpl implements ServiceLineService{

    //Attributes
    private ServiceLineRepository serviceLineRepository;


    //Constructors
    public ServiceLineServiceImpl(ServiceLineRepository serviceLineRepository) {
        this.serviceLineRepository = serviceLineRepository;
    }

    //Methods
    //Create a new ServiceLine
    public long addServiceLine(ServiceLine serviceLine) {
        ServiceLine storedServiceline = serviceLineRepository.save(serviceLine);
        return storedServiceline.getIdServiceLine();
    }

    //Get all ServiceLines
    @Override
    public List<ServiceLine> getAllServiceLines() {
        return serviceLineRepository.findAll();
    }

    //Get item by idServiceLine
    @Override
    public ServiceLine getServiceLineById(long idServiceLine) {
        if (!serviceLineRepository.existsById(idServiceLine)) {
            throw new RecordNotFoundException();
        }
        return serviceLineRepository.findById(idServiceLine);
    }


    //Delete Car by idServiceLine
    @Override
    public void deleteServiceLineById(long idServiceLine) {
        if (!serviceLineRepository.existsById(idServiceLine)) {
            throw new BadRequestException();
        }
        serviceLineRepository.deleteById(idServiceLine);
    }


    //Update item by idServiceLine
    @Override
    public void updateServiceLineById(long idServiceLine, ServiceLine updateServiceLine) {

        if (!serviceLineRepository.existsById(idServiceLine)) {
            throw new BadRequestException();

        }
        ServiceLine storedServiceLine = serviceLineRepository.findById(idServiceLine);
        storedServiceLine.setServiceLineNumber(updateServiceLine.getServiceLineNumber());
        storedServiceLine.setQty(updateServiceLine.getQty());
        storedServiceLine.setPrice(updateServiceLine.getPrice());
        storedServiceLine.setItemName(updateServiceLine.getItemName());
        storedServiceLine.setLineTotal((updateServiceLine.getLineTotal()));
        storedServiceLine.setItem((updateServiceLine.getItem()));
        storedServiceLine.setService((updateServiceLine.getService()));
        storedServiceLine.setInvoice((updateServiceLine.getInvoice()));
        serviceLineRepository.save(updateServiceLine);
    }

}
