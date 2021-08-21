package nl.novi.autogarage_roy_kersten.service;

import nl.novi.autogarage_roy_kersten.exception.BadRequestException;
import nl.novi.autogarage_roy_kersten.exception.RecordNotFoundException;
import nl.novi.autogarage_roy_kersten.model.Item;
import nl.novi.autogarage_roy_kersten.model.ItemStatus;
import nl.novi.autogarage_roy_kersten.model.ServiceLine;
import nl.novi.autogarage_roy_kersten.repository.ItemRepository;
import nl.novi.autogarage_roy_kersten.repository.ServiceLineRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * The ServiceLineServiceImpl class implements the methods defined in the ServiceLineService Interface.
 * The ServiceLineServiceImpl class receives information via this interface from the ServiceLineController class, adds business logic and
 * communicates with the ServiceLineRepository interface.
 * */


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
        //if Qty < 1 no serviceLine can be created
        if (serviceLine.getQty() < 1) {
            throw new BadRequestException("Qty cannot be less than 1");
        }
        checkInventoryLevelNewTransaction(serviceLine);

        ServiceLine storedServiceLine = serviceLineRepository.save(serviceLine);
        setServiceLineNumber(storedServiceLine);                                                                        //determine the serviceLineNumber
        setItemName(storedServiceLine);                                                                                 //Set itemName of item in the serviceLine, get from item in database
        setServiceLinePriceItemStatusOpen(serviceLine,storedServiceLine);                                               //When ItemStatus is "OPEN" The price can be adjusted by the user in the ServiceLine
        setServiceLinePriceItemStatusLocked(storedServiceLine);                                                         //When ItemStatus is "LOCKED" get price from item in database
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

    //Get ServiceLine by idServiceLine
    @Override
    public ServiceLine getServiceLineById(long idServiceLine) {
        if (!serviceLineRepository.existsById(idServiceLine)) {
            throw new RecordNotFoundException("serviceLineId does not exists");
        }
        return serviceLineRepository.findById(idServiceLine);
    }

    //Delete ServiceLine by idServiceLine
    @Override
    public void deleteServiceLineById(long idServiceLine) {
        if (!serviceLineRepository.existsById(idServiceLine)) {
            throw new BadRequestException("serviceLine Id does not exists");
        }
        if (serviceLineRepository.findById(idServiceLine).getInvoice()!=null){
            throw new BadRequestException("serviceLine cannot be deleted, serviceline is invoiced already");
        }
        updateInventoryAfterDeleteServiceLine(idServiceLine);                                                           //Before serviceLine will be deleted the inventory Qty will be restored and put on stock again
        serviceLineRepository.deleteById(idServiceLine);                                                                //Delete serviceLine with corresponding id
    }

    //Update item by idServiceLine
    @Override
    public void updateServiceLineById(long idServiceLine, ServiceLine serviceLine) {
        //check 1. check if serviceLine exists
        if (!serviceLineRepository.existsById(idServiceLine)) {                                                         //Throw BadRequestException when idServiceLine does not exists
            throw new BadRequestException("serviceLineId does not exists");
        }
        ServiceLine storedServiceLine = serviceLineRepository.findById(idServiceLine);

        //check 2. if Qty < 1 no serviceLine can be created
        if (serviceLine.getQty() < 1) {                                                                                 //if Qty < 1 no serviceLine should be created, throw BadRequestException
            throw new BadRequestException("Qty cannot be less than 1");
        }

        //check 3. if serviceLine has been invoiced already it is not allowed to change the serviceLine
        if (storedServiceLine.getInvoice() != null) {                                                                   //When serviceLine has been invoiced already, throw BadRequestException
            throw new BadRequestException("serviceLine is already invoiced and cannot be changed");
        }

        checkInventoryLevelNewTransaction(serviceLine);
        reverseInventoryInitialTransaction(idServiceLine);
        storedServiceLine.setItem(serviceLine.getItem());                                                               //Set provided itemId in storedServiceLine
        storedServiceLine.setQty(serviceLine.getQty());                                                                 //Set provided Qty in storedServiceLine
        setItemName(storedServiceLine);                                                                                 //Set itemName of item in the serviceLine, get from item in database
        setServiceLinePriceItemStatusOpen(serviceLine,storedServiceLine);                                               //When ItemStatus is "OPEN" The price can be adjusted in the ServiceLine
        setServiceLinePriceItemStatusLocked(storedServiceLine);                                                         //When ItemStatus is "LOCKED" set price of item in the serviceLine taken from item in database
        calculateLineSubtotal(storedServiceLine);                                                                       //calculate lineSubtotal based on Qty * Price
        calculateVatAmount(storedServiceLine);                                                                          //calculate vatAmount based on lineSubtotal * vatRate
        calculateLineTotal(storedServiceLine);                                                                          //calculate lineTotal based on lineSubtotal + vatAmount
        updateInventory(storedServiceLine);                                                                             //update Inventory Qty of used Item in case a Part is used during Repair, initialQty and ItemId are required to ensure correct correction of Inventory
        serviceLineRepository.save(storedServiceLine);                                                                  //save stored serviceLine
    }

    //Specific function for PUT request: Reverse inventory level based on initial booked serviceLine
    public void reverseInventoryInitialTransaction(long idServiceLine) {
        ServiceLine storedServiceLine = serviceLineRepository.findById(idServiceLine);
        int initialQty = storedServiceLine.getQty();                                                                    //store initial Qty for correct inventory correction
        long initialIdItem = storedServiceLine.getItem().getIdItem();                                                   //store initial ItemId for correct inventory correction
        Item initialItem = itemRepository.findById(initialIdItem);                                                      //find initialItem by Id

        if(initialItem.getClass().getSimpleName().equalsIgnoreCase("Part")) {
            initialItem.setQty(initialItem.getQty() + initialQty);                                                      //Calculate initial inventory Qty for item in case of a Part, in case of an Activity Inventory level will remain 1.
        }
    }

    //Determine check Inventory Level
    public void checkInventoryLevelNewTransaction(ServiceLine serviceLine) {
        int updateQty = serviceLine.getQty();
        long updateIdItem = serviceLine.getItem().getIdItem();
        Item updateItem = itemRepository.findById(updateIdItem);

        if (updateItem.getClass().getSimpleName().equalsIgnoreCase("Part")) {                               //If item is a Part, inventory level needs to be checked
            int checkInventoryLevel = (updateItem.getQty() - updateQty);

            if (checkInventoryLevel < 0) {
                throw new BadRequestException("not sufficient inventory available for this transaction");
            }
        }
    }

    //set unique serviceLineNumber per idService
    public ServiceLine setServiceLineNumber(ServiceLine serviceLine) {
        ServiceLine storedServiceLine = serviceLineRepository.save(serviceLine);                                        //by saving the serviceLine the new line counter can be determined
        long serviceLineCounter = serviceLineRepository.countByServiceIdService(storedServiceLine.getService().getIdService());     //Determine the serviceLine number
        storedServiceLine.setServiceLineNumber(serviceLineCounter);
        return serviceLineRepository.save(serviceLine);
    }

    //Validate ItemStatus, in case ItemStatus equals OPEN, the price can be adjusted in the serviceLine
    public void setServiceLinePriceItemStatusOpen (ServiceLine serviceLine, ServiceLine storedServiceLine) {
        long idItem = serviceLine.getItem().getIdItem();
        ItemStatus itemstatus = itemRepository.findById(idItem).getStatus();

        if(itemstatus.equals(ItemStatus.OPEN)) {
            storedServiceLine.setPrice(serviceLine.getPrice());                                                         //Set provided price, for example required in case of item "overige handelingen"
            serviceLineRepository.save(storedServiceLine);
        }
    }

    //Set serviceLinePrice, taken from Item
    public ServiceLine setServiceLinePriceItemStatusLocked(ServiceLine serviceLine) {
        ServiceLine storedServiceLine = serviceLineRepository.save(serviceLine);
        long storedItemId = storedServiceLine.getItem().getIdItem();
        Item storedItem = itemRepository.findById(storedItemId);

        if(storedItem.getStatus().equals(ItemStatus.LOCKED)) {                                                          //When ItemStatus is "LOCKED" price should always be calculated with price set in the Item.
            storedServiceLine.setPrice(storedItem.getPrice());                                                          //Get item price from item table and store in serviceLine price
        }
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

    //update Inventory Item => method invoked via addServiceLine() or updateServiceLineByID()
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

    //update Inventory Item => method invoked via deleteServiceLineByID()
    public Item updateInventoryAfterDeleteServiceLine(long idServiceLine) {
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


