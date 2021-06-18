package nl.novi.autogarage_roy_kersten.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;


/**
 * The Customer class is the blueprint for all Customer objects.
 * A customer object contains all basic information of a customer like: firstName, LastName, phoneNumber and email.
 */

@Entity
@Table(name = "customer")

public class Customer {


    //attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCustomer;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @OneToMany (mappedBy = "customer")
    @JsonIgnore
    private List<Invoice> invoice;

    @OneToMany (mappedBy = "customer")
    @JsonIgnore
    private List<Car> car;

    @OneToMany (mappedBy = "customer")
    @JsonIgnore
    private List<Service> service;


    //Constructors
    public Customer() {
    }

    public Customer(Long idCustomer, String firstName, String lastName, String phoneNumber, String email) {
        this.idCustomer = idCustomer;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


    //Getters and Setters
    public Long getIdCustomer() {
        return idCustomer;
    }


    public void setIdCustomer(Long idCustomer) {
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

}
