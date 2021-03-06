package nl.novi.autogarage_roy_kersten.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)                   //Set Strategy SINGLE_TABLE => Create one table for all subclasses with a subclass type column to differentiate between subclasses
@DiscriminatorColumn(name = "item_type")                                //Subclasses of class Item can be identified in the table by item_type
@Table(name = "item")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Part.class, name = "part"),
        @JsonSubTypes.Type(value = Activity.class, name = "activity"),
})

public abstract class Item {

    //Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItem;

    @Column (name  = "item_name")
    private String itemName;

    @Column (name = "qty")
    private int qty;

    @Column (name = "price")
    private float price;

    @Column (name = "item_category")
    private String itemCategory;

    @Column (name = "status")
    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @OneToMany (mappedBy = "item")
    @JsonIgnore                                     // Ignore ServiceLine connections when item information is called
    private List<ServiceLine> serviceLine;


    //Constructors
    public Item () {}

    public Item(Long idItem, String itemName, int qty, float price, String itemCategory,ItemStatus status) {
        this.idItem = idItem;
        this.itemName = itemName;
        this.qty = qty;
        this.price = price;
        this.itemCategory = itemCategory;
        this.status = status;
    }

    //Getters and Setters
    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public List<ServiceLine> getServiceLine() {
        return serviceLine;
    }

    public void setServiceLine(List<ServiceLine> serviceLine) {
        this.serviceLine = serviceLine;
    }

}
