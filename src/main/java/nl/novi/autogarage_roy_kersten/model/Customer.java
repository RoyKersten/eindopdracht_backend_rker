package nl.novi.autogarage_roy_kersten.model;

/**
 * De Customer Class zorgt ervoor dat nieuwe klanten kunnen worden geregistreerd.
 * De klasse bevat standaard informatie over de klant.
 */

public class Customer {

    //attributes

    private int idCustomer;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;


    //Constructors
    public Customer() {};


    public Customer(int idCustomer, String firstName, String lastName, String phoneNumber, String email) {
        this.idCustomer = idCustomer;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    //Getters and Setters
    public int getIdCustomer() {
        return idCustomer;
    }


    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
       this.email = email;
    }

    //Methods

    @Override
    public String toString() {
        return getIdCustomer() + " " + getFirstName() + " " + getLastName() + " " + getPhoneNumber() + " " + getEmail();
    }

}
