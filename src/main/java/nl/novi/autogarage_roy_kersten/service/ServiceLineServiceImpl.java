package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.model.Item;
import nl.novi.autogarage_roy_kersten.model.ServiceLine;
import nl.novi.autogarage_roy_kersten.repository.ItemRepository;
import nl.novi.autogarage_roy_kersten.repository.ServiceLineRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ServiceLineServiceImpl implements ServiceLineService {

    //Attributes
    private ServiceLineRepository serviceLineRepository;
    private ItemRepository itemRepository;

    //Constructors
    public ServiceLineServiceImpl(ServiceLineRepository serviceLineRepository, ItemRepository itemRepository) {
        this.serviceLineRepository = serviceLineRepository;
        this.itemRepository = itemRepository;
    }

    //Methods
    //Create a new ServiceLine
    @Override
    public long addServiceLine(ServiceLine serviceLine) {

        //save storedServiceLine
        ServiceLine storedServiceLine = serviceLineRepository.save(serviceLine);

        setServiceLineNumber(storedServiceLine);                                                                        //determine the serviceLineNumber
        setItemName(storedServiceLine);                                                                                 //Set itemName of item in the serviceLine, get from item in database
        setServiceLinePrice(storedServiceLine);                                                                         //set price of item in the serviceLine, get from item in database
        setVatRate(storedServiceLine);                                                                                  //set vatRate in the serviceLine

        calculateLineSubtotal(storedServiceLine);                                                                       //calculate lineSubtotal based on Qty * Price
        calculateVatAmount(storedServiceLine);                                                                          //calculate vatAmount based on lineSubtotal * vatRate
        calculateLineTotal(storedServiceLine);                                                                          //calculate lineTotal based on lineSubtotal + vatAmount

        return storedServiceLine.getIdServiceLine();
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

        if (!serviceLineRepository.existsById(idServiceLine)) {                                                         //Throw BadRequestException when idServiceLine does not exists
            throw new BadRequestException();
        }
        ServiceLine storedServiceLine = serviceLineRepository.findById(idServiceLine);
        storedServiceLine.setItem(updateServiceLine.getItem());                                                         //Set provided itemId in storedServiceLine
        storedServiceLine.setQty(updateServiceLine.getQty());                                                           //Set provided Qty in storedServiceLine

        setServiceLinePrice(storedServiceLine);                                                                         //set price of item in the serviceLine, get from item in database
        calculateLineSubtotal(storedServiceLine);                                                                       //calculate lineSubtotal based on Qty * Price
        calculateVatAmount(storedServiceLine);                                                                          //calculate vatAmount based on lineSubtotal * vatRate
        calculateLineTotal(storedServiceLine);                                                                          //calculate lineTotal based on lineSubtotal + vatAmount

        serviceLineRepository.save(storedServiceLine);                                                                  //save
    }


    //set unique serviceLineNumber per idService
    public ServiceLine setServiceLineNumber(ServiceLine serviceLine) {
        ServiceLine storedServiceLine = serviceLineRepository.save(serviceLine);

        long serviceLineCounter = serviceLineRepository.countByServiceIdService(storedServiceLine.getService().getIdService());
        storedServiceLine.setServiceLineNumber(serviceLineCounter);
        return serviceLineRepository.save(serviceLine);
    }

    //Set serviceLinePrice, taken from Item
    public ServiceLine setServiceLinePrice(ServiceLine serviceLine) {
        ServiceLine storedServiceLine = serviceLineRepository.save(serviceLine);
        long storedItemId = storedServiceLine.getItem().getIdItem();
        Item storedItem = itemRepository.findById(storedItemId);
        storedServiceLine.setPrice(storedItem.getPrice());                                                    //Get item price from item table and store in serviceLine price
        return serviceLineRepository.save(serviceLine);
    }

    //Calculate LineSubTotal
    public ServiceLine calculateLineSubtotal(ServiceLine serviceLine) {
        ServiceLine storedServiceLine = serviceLineRepository.save(serviceLine);
        BigDecimal lineSubtotalRounded = new BigDecimal(storedServiceLine.getQty() * storedServiceLine.getPrice());
        storedServiceLine.setLineSubTotal(lineSubtotalRounded.setScale(2, RoundingMode.HALF_EVEN).floatValue());
        return serviceLineRepository.save(serviceLine);
    }

    //Set vatRate !!!!!!!!!! AANPASSEN, NU FIXED 21%
    public void setVatRate(ServiceLine serviceLine) {
        ServiceLine storedServiceLine = serviceLineRepository.save(serviceLine);
        storedServiceLine.setVatRate(0.21f);
    }


    //Calculate vatAmount
    public ServiceLine calculateVatAmount(ServiceLine serviceLine) {
        ServiceLine storedServiceLine = serviceLineRepository.save(serviceLine);
        BigDecimal vatAmountRounded = new BigDecimal(storedServiceLine.getLineSubTotal() * storedServiceLine.getVatRate());
        storedServiceLine.setVatAmount(vatAmountRounded.setScale(2, RoundingMode.HALF_EVEN).floatValue());
        return serviceLineRepository.save(serviceLine);
    }


    //Calculate lineTotal
    public ServiceLine calculateLineTotal(ServiceLine serviceLine) {
        ServiceLine storedServiceLine = serviceLineRepository.save(serviceLine);
        BigDecimal lineTotalRounded = new BigDecimal(storedServiceLine.getLineSubTotal() + storedServiceLine.getVatAmount());
        storedServiceLine.setLineTotal(lineTotalRounded.setScale(2, RoundingMode.HALF_EVEN).floatValue());
        return serviceLineRepository.save(serviceLine);
    }

    //set ItemName taken from Item
    public ServiceLine setItemName(ServiceLine serviceLine) {
        ServiceLine storedServiceLine = serviceLineRepository.save(serviceLine);
        long storedItemId = storedServiceLine.getItem().getIdItem();
        Item storedItem = itemRepository.findById(storedItemId);
        storedServiceLine.setItemName(storedItem.getItemName());                                              //Get item name from item table and store in serviceLine itemName
        return serviceLineRepository.save(serviceLine);
    }



}


