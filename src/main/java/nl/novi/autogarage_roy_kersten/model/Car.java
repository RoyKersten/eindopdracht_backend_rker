package nl.novi.autogarage_roy_kersten.model;

import javax.persistence.*;


/**
 * The Car class is the blueprint for all Car objects.
 * A Car object contains all basic information of a car like: brand, model, year_of_construction and license_plate_number.
 */

@Entity
@Table(name = "car")
public class Car {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCar;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "year_of_construction")
    private String yearOfConstruction;

    @Column(name = "license_plate_number")
    private String licensePlateNumber;

    @ManyToOne
    //@Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Customer customer;

    //@JsonIgnoreProperties("car")

    // Constructors
    public Car() {}

    public Car(Long idCar, String brand, String model, String yearOfConstruction, String licensePlateNumber, Customer customer) {
        this.idCar = idCar;
        this.brand = brand;
        this.model = model;
        this.yearOfConstruction = yearOfConstruction;
        this.licensePlateNumber = licensePlateNumber;
        this.customer = customer;
    }

    // Getters and Setters
    public Long getIdCar() {
        return idCar;
    }

    public void setIdCar(Long idCar) {
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

