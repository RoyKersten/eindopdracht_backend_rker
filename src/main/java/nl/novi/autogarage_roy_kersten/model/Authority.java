package nl.novi.autogarage_roy_kersten.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass (AuthorityKey.class)
@Table(name = "authorities")
public class Authority implements Serializable {

    //Attributes
    @Id
    @Column  (nullable = false)
    private String username;

    @Id
    @Column(nullable = false)
    private String authority;


    //Constructors
    public Authority() {}
    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    //Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
