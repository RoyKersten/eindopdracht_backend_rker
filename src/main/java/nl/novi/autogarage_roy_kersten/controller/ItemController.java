package nl.novi.autogarage_roy_kersten.controller;

import nl.novi.autogarage_roy_kersten.model.Item;
import nl.novi.autogarage_roy_kersten.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public abstract class ItemController {
   private ItemService itemService;

   public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    //Methods

    //Create a new Item
    @PostMapping(value = "")
    public ResponseEntity<Object> addItem(@RequestBody Item item) {
        long newId = itemService.addItem(item);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idItem}")
                .buildAndExpand(newId).toUri();
        return ResponseEntity.created(location).body(location);
    }

    //Get all Items
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }


    //Get Item by idItem
    @GetMapping("/{idItem}")
    public ResponseEntity<Object> getItemById(@PathVariable("idItem") long idItem) {
        Item item = itemService.getItemById(idItem);
        return ResponseEntity.ok(item);
    }


    //Delete Item by idItem
    @DeleteMapping("/{idItem}")
    public ResponseEntity<Object> deleteItemById(@PathVariable("idItem") long idItem) {
        itemService.deleteItemById(idItem);
        return ResponseEntity.ok("item successfully deleted");
    }



}
