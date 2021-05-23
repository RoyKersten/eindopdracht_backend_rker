package nl.novi.autogarage_roy_kersten;

public class Item {

    //Attributes
    private int idItem;
    private String itemName;
    private int qty;
    private float price;
    private String itemCategory;

    //Constructors

    public Item(int idItem, String itemName, int qty, float price, String itemCategory) {
        this.idItem = idItem;
        this.itemName = itemName;
        this.qty = qty;
        this.price = price;
        this.itemCategory = itemCategory;
    }

    //Getters and Setters

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
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

    //Methods



}
