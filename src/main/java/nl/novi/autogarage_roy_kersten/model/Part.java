package nl.novi.autogarage_roy_kersten.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue ("part")
public class Part extends Item {

    //Attributes
    @Column (name  = "brand")
    private String brand;

    //Constructors
    public Part (){}

    public Part(Long idItem, String itemName, int qty, float price,String brand,  String itemCategory, ItemStatus status) {
        super(idItem, itemName, qty, price, itemCategory, status);
    this.brand = brand;
    }

    //Getters and Setters
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
