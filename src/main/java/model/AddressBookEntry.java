package model;

import javax.persistence.*;

/** Extremely simple JPA (Java Persistence) for a person
 * This person just has an id
 **/
@Entity
@Table(name = "address")
public class AddressBookEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private Long id;
    @Column(nullable = false)
    private String first_name;
    @Column(nullable = false)
    private String last_name;
    @Column(nullable = false)
    private String street_address;
    @Column(nullable = false)
    private String additional_address;
    @Column(nullable = false)
    private String city_or_town;
    @Column(nullable = false, length = 2)
    private String state;
    @Column(nullable = false, length = 9)
    private String zipcode;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false, length = 16)
    private String telephone;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getStreet_address() {
        return street_address;
    }
    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    public String getAdditional_address() {
        return additional_address;
    }
    public void setAdditional_address(String additional_address) {
        this.additional_address = additional_address;
    }

    public String getCity_or_town() {
        return city_or_town;
    }
    public void setCity_or_town(String city_or_town) {
        this.city_or_town = city_or_town;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return telephone;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", street_address=" + street_address + '\'' +
                ", additional_address='" + additional_address + '\'' +
                ", city_or_town='" + city_or_town + '\'' +
                ", state='" + state + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone +
                '}';
    }

}