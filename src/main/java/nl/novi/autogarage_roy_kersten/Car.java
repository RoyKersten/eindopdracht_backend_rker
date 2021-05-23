package nl.novi.autogarage_roy_kersten;

public class Car {

    // Attributes
    private int idCar;
    private String brand;
    private String model;
    private String yearOfConstruction;
    private String licensePlateNumber;
    private Customer customer;              // see also constructor


    // Constructors
    public Car(int idCar, String brand, String model, String yearOfConstruction, String licensePlateNumber, Customer customer) {
        this.idCar = idCar;
        this.brand = brand;
        this.model = model;
        this.yearOfConstruction = yearOfConstruction;
        this.licensePlateNumber = licensePlateNumber;
        this.customer = customer;
    }

    // Getters and Setters
    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYearOfConstruction() {
        return yearOfConstruction;
    }

    public void setYearOfConstruction(String yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

//Methods


}

