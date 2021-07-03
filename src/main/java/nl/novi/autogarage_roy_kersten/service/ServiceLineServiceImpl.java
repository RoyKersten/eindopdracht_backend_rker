package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.model.Item;
import nl.novi.autogarage_roy_kersten.model.ServiceLine;
import nl.novi.autogarage_roy_kersten.repository.ItemRepository;
import nl.novi.autogarage_roy_kersten.repository.ServiceLineRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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


        //if Qty < 1 no serviceLine should be created, throw BadRequestException
        if (serviceLine.getQty() < 1) {
            throw new BadRequestException("Qty mag niet kleiner zijn dan 1");
        }

        //check if there servicelines with same idService exists and are invoiced already, if yes, idService cannot be used anymore, Service is closed
//        long idService = serviceLine.getService().getIdService();

//        if(serviceLineRepository.existsById(idService)) {
//            ServiceLine validateInvoicedServiceLines = serviceLineRepository.findById(idService);
//            if (validateInvoicedServiceLines.getInvoice() != null) {
//                throw new BadRequestException("Er bestaan serviceLines met hetzelfde idService die gefactureerd zijn, service is afgesloten en kan niet meer worden gebruikt");
//            }
//        }

        //Check if inventory level is sufficient for selected item
        checkInventoryLevel(serviceLine);

        //Inventory level check performed successfully, save storedServiceLine and proceed with next steps to complete storedServiceLine
        ServiceLine storedServiceLine = serviceLineRepository.save(serviceLine);
        setServiceLineNumber(storedServiceLine);                                                                        //determine the serviceLineNumber
        setItemName(storedServiceLine);                                                                                 //Set itemName of item in the serviceLine, get from item in database
        setServiceLinePrice(storedServiceLine);                                                                         //set price of item in the serviceLine, get from item in database
        setVatRate(storedServiceLine);                                                                                  //set vatRate in the serviceLine
        calculateLineSubtotal(storedServiceLine);                                                                       //calculate lineSubtotal based on Qty * Price
        calculateVatAmount(storedServiceLine);                                                                          //calculate vatAmount based on lineSubtotal * vatRate
        calculateLineTotal(storedServiceLine);                                                                          //calculate lineTotal based on lineSubtotal + vatAmount
        updateInventory(storedServiceLine);                                                                             //update Inventory Qty of used Item in case a Part is used during Repair, initialQty and ItemId are required to ensure correct correction of Inventory

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

        updateInventoryDelete(idServiceLine);                                                                           //Before serviceLine will be deleted, the inventory Qty will be restored and put on stock again
        serviceLineRepository.deleteById(idServiceLine);                                                                //Delete serviceLine with corresponding id
    }


    //Update item by idServiceLine
    @Override
    public void updateServiceLineById(long idServiceLine, ServiceLine serviceLine) {
        //find initial booked serviceLine in database
        ServiceLine storedServiceLine = serviceLineRepository.findById(idServiceLine);

        //check if serviceLine exists, if not throw BadRequestException
        if (!serviceLineRepository.existsById(idServiceLine)) {                                                         //Throw BadRequestException when idServiceLine does not exists
            throw new BadRequestException("serviceLineId bestaat niet");
        }

        //Check if serviceLine Qty is 1 or greater than 1, if not throw BadRequestException
        if (serviceLine.getQty() < 1) {                                                                                 //if Qty < 1 no serviceLine should be created, throw BadRequestException
            throw new BadRequestException("Qty mag niet kleiner zijn dan 1");
        }

        //if serviceLine has been invoiced already it is not allowed to change the serviceLine, throw BadRequestException
        if (storedServiceLine.getInvoice()!=null) {                                                                     //When serviceLine has been invoiced already, throw BadRequestException
            throw new BadRequestException("serviceLine is al gefactureerd en mag niet aangepast worden");
        }

        //Reverse inventory of initial transaction to ensure inventory level is corrected correctly
        reverseInventory(idServiceLine);

        //Check if inventory level is sufficient for selected item
        checkInventoryLevel(serviceLine);

        //Change is allowed, sufficient inventory available, serviceLine can be processed
        storedServiceLine.setItem(serviceLine.getItem());                                                               //Set provided itemId in storedServiceLine
        storedServiceLine.setQty(serviceLine.getQty());                                                                 //Set provided Qty in storedServiceLine
        setItemName(storedServiceLine);                                                                                 //Set itemName of item in the serviceLine, get from item in database
        setServiceLinePrice(storedServiceLine);                                                                         //set price of item in the serviceLine, get from item in database
        calculateLineSubtotal(storedServiceLine);                                                                       //calculate lineSubtotal based on Qty * Price
        calculateVatAmount(storedServiceLine);                                                                          //calculate vatAmount based on lineSubtotal * vatRate
        calculateLineTotal(storedServiceLine);                                                                          //calculate lineTotal based on lineSubtotal + vatAmount
        updateInventory(storedServiceLine);                                                                             //update Inventory Qty of used Item in case a Part is used during Repair, initialQty and ItemId are required to ensure correct correction of Inventory
        serviceLineRepository.save(storedServiceLine);                                                                  //save stored serviceLine
    }


    //Specific function for PUT request: Reverse inventory level based on initial booked serviceLine
    public void reverseInventory(long idServiceLine) {
        ServiceLine storedServiceLine = serviceLineRepository.findById(idServiceLine);
        int initialQty = storedServiceLine.getQty();                                                                    //store initial Qty for correct inventory correction
        long initialIdItem = storedServiceLine.getItem().getIdItem();                                                   //store initial ItemId for correct inventory correction
        Item initialItem = itemRepository.findById(initialIdItem);                                                      //find initialItem by Id
        initialItem.setQty(initialItem.getQty() + initialQty);                                                          //Calculate initial inventory Qty for item
    }

    //Determine check Inventory Level
    public void checkInventoryLevel(ServiceLine serviceLine) {
        int updateQty = serviceLine.getQty();
        long updateIdItem = serviceLine.getItem().getIdItem();
        Item updateItem = itemRepository.findById(updateIdItem);

        int inventoryCheckLevel = (updateItem.getQty() - updateQty);

        //check inventory level
        if (inventoryCheckLevel < 0) {
            throw new BadRequestException("Niet genoeg voorraad van dit artikel");
        }
    }

    //set unique serviceLineNumber per idService
    public ServiceLine setServiceLineNumber(ServiceLine serviceLine) {
        ServiceLine storedServiceLine = serviceLineRepository.save(serviceLine);

        long serviceLineCounter = serviceLineRepository.countByServiceIdService(storedServiceLine.getService().getIdService());     //Determine the serviceLine number
        storedServiceLine.setServiceLineNumber(serviceLineCounter);
        return serviceLineRepository.save(serviceLine);
    }

    //Set serviceLinePrice, taken from Item
    public ServiceLine setServiceLinePrice(ServiceLine serviceLine) {
        ServiceLine storedServiceLine = serviceLineRepository.save(serviceLine);
        long storedItemId = storedServiceLine.getItem().getIdItem();
        Item storedItem = itemRepository.findById(storedItemId);
        storedServiceLine.setPrice(storedItem.getPrice());                                                              //Get item price from item table and store in serviceLine price
        return serviceLineRepository.save(serviceLine);
    }

    //Calculate LineSubTotal
    public ServiceLine calculateLineSubtotal(ServiceLine serviceLine) {
        ServiceLine storedServiceLine = serviceLineRepository.save(serviceLine);
        BigDecimal lineSubtotalRounded = new BigDecimal(storedServiceLine.getQty() * storedServiceLine.getPrice());
        storedServiceLine.setLineSubTotal(lineSubtotalRounded.setScale(2, RoundingMode.HALF_EVEN).floatValue());
        return serviceLineRepository.save(serviceLine);
    }

    //Set vatRate, fixed on 21%
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
        storedServiceLine.setItemName(storedItem.getItemName());                                                        //Get item name from item table and store in serviceLine itemName
        return serviceLineRepository.save(serviceLine);
    }

    public Item updateInventory(ServiceLine serviceLine) {

        ServiceLine storedServiceLine = serviceLineRepository.save(serviceLine);                                        //save serviceLine

        long storedItemId = storedServiceLine.getItem().getIdItem();                                                    //get itemId
        Item storedItem = itemRepository.findById(storedItemId);                                                        //find item by itemId

        String subClassName = storedItem.getClass().getSimpleName();                                                    //Get Item subclass name, Part or Activity
        if (subClassName.equalsIgnoreCase("Part")) {                                                        //Only in case the used Item is a Part the inventory value should be adjusted, for an activity this is not allowed

            storedItem.setQty(storedItem.getQty() - storedServiceLine.getQty());                                        //Calculate new inventory Qty for item
        }
        return itemRepository.save(storedItem);
    }

    public Item updateInventoryDelete(long idServiceLine) {

        ServiceLine storedServiceLine = serviceLineRepository.getById(idServiceLine);                                   //save serviceLine

        long storedItemId = storedServiceLine.getItem().getIdItem();                                                    //Get itemId
        Item storedItem = itemRepository.findById(storedItemId);                                                        //find item by itemId

        String subClassName = storedItem.getClass().getSimpleName();                                                    //Get Item subclass name, Part or Activity
        if (subClassName.equalsIgnoreCase("Part")) {                                                        //Only in case the used Item is a Part the inventory value should be adjusted, for an activity this is not allowed
            storedItem.setQty(storedItem.getQty() + storedServiceLine.getQty());
        }

        return itemRepository.save(storedItem);
    }

}


