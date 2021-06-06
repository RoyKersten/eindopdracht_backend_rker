package nl.novi.autogarage_roy_kersten.model;

import nl.novi.autogarage_roy_kersten.model.Item;


import javax.persistence.*;

@Entity
@DiscriminatorValue ("part")                        //Ensures that the item_type automatically will be "parts"
public class Part extends Item {

    //Attributes


    @Column (name  = "brand")
    private String brand;

    //Constructors
    public Part (){}

    public Part(Long idItem, String itemName, int qty, float price,String brand, String itemCategory) {
        super(idItem, itemName, qty, price, itemCategory);
    this.brand = brand;
    }

    //Getters and Setters

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    //Methods


}
