package nl.novi.autogarage_roy_kersten.controller;

import nl.novi.autogarage_roy_kersten.model.Item;
import nl.novi.autogarage_roy_kersten.service.ItemServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public abstract class ItemController {
   private ItemServiceImpl itemServiceImpl;

   public ItemController(ItemServiceImpl itemServiceImpl) {
        this.itemServiceImpl = itemServiceImpl;
    }

    //Methods

    //Create a new Item
    @PostMapping(value = "")
    public ResponseEntity<Object> addItem(@RequestBody Item item) {
        long newId = itemServiceImpl.addItem(item);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idItem}")
                .buildAndExpand(newId).toUri();
        return ResponseEntity.created(location).body(location);
    }


    //Get Item by idItem, can be defined in abstract class Item as it works for Part and Activity
    @GetMapping("/{idItem}")
    public ResponseEntity<Object> getItemById(@PathVariable("idItem") long idItem) {
        Item item = itemServiceImpl.getItemById(idItem);
        return ResponseEntity.ok(item);
    }


    //Delete Item by idItem
    @DeleteMapping("/{idItem}")
    public ResponseEntity<Object> deleteItemById(@PathVariable("idItem") long idItem) {
        itemServiceImpl.deleteItemById(idItem);
        return ResponseEntity.ok("item successfully deleted");
    }



}
