package com.example.springbootresttosql.model;

import javax.persistence.*;

/**
 * This class holds a single address book entry e.g. a specific person or business.
 * Contains annotations that allow it to be persisted into a DB
 */
@Entity
@Table(name = "address")
public class AddressBookEntry {
    /**
     * Variable declaration for AddressBookEntry Class
     * Contains annotations that allow data to be persisted to DB
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="first_name", nullable = false)
    private String first_name;
    @Column(name="last_name", nullable = false)
    private String last_name;
    @Column(name="street_address", nullable = false)
    private String street_address;
    @Column(name="additional_address", nullable = false)
    private String additional_address;
    @Column(name="city_or_town", nullable = false)
    private String city_or_town;
    @Column(name="state", nullable = false, length = 2)
    private String state;
    @Column(name="zipcode", nullable = false, length = 9)
    private String zipcode;
    @Column(name="email", nullable = false)
    private String email;
    @Column(name="telephone", nullable = false, length = 16)
    private String telephone;

    /**
     *  Dependency Injection constructor for AddressBookEntry.
     *  Injects all auto-generated fields.
     */
    public AddressBookEntry (String first_name, String last_name, String street_address, String additional_address, String city_or_town,
                             String state, String zipcode, String telephone,String email){
        this.first_name = first_name;
        this.last_name = last_name;
        this.street_address = street_address;
        this.additional_address = additional_address;
        this.city_or_town = city_or_town;
        this.state = state;
        this.zipcode = zipcode;
        this.telephone = telephone;
        this.email = email;
    }

    /**
     * Default constructor for AddressBookEntry
     */
    public AddressBookEntry() {
    }

    /**
     * getter method for AddressBookEntry id variable
     * @return id of AddressBookEntry
     */
    public Long getId() {
        return id;
    }
    /**
     * setter method for AddressBookEntry id variable
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getter method for AddressBookEntry first_name variable
     * @return first_name of AddressBookEntry
     */
    public String getFirst_name() {
        return first_name;
    }
    /**
     * setter method for AddressBookEntry first_name variable
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * getter method for AddressBookEntry last_name variable
     * @return last_name of AddressBookEntry
     */
    public String getLast_name() {
        return last_name;
    }
    /**
     * setter method for AddressBookEntry last_name variabley
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * getter method for AddressBookEntry street_address variable
     * @return street_address of AddressBookEntry
     */
    public String getStreet_address() {
        return street_address;
    }
    /**
     * setter method for AddressBookEntry street_address variable
     */
    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    /**
     * getter method for AddressBookEntry additional_address variable
     * @return additional_address of AddressBookEntry
     */
    public String getAdditional_address() {
        return additional_address;
    }
    /**
     * setter method for AddressBookEntry street_address variable
     */
    public void setAdditional_address(String additional_address) {
        this.additional_address = additional_address;
    }

    /**
     * getter method for AddressBookEntry city_or_town variable
     * @return city_or_town of AddressBookEntry
     */
    public String getCity_or_town() {
        return city_or_town;
    }
    /**
     * setter method for AddressBookEntry city_or_town variable
     */
    public void setCity_or_town(String city_or_town) {
        this.city_or_town = city_or_town;
    }

    /**
     * getter method for AddressBookEntry state variable
     * @return state of AddressBookEntry
     */
    public String getState() {
        return state;
    }
    /**
     * setter method for AddressBookEntry state variable
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * getter method for AddressBookEntry zipcode variable
     * @return zipcode of AddressBookEntry
     */
    public String getZipcode() {
        return zipcode;
    }
    /**
     * setter method for AddressBookEntry zipcode variable
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * getter method for AddressBookEntry email variable
     * @return email of AddressBookEntry
     */
    public String getEmail() {
        return email;
    }
    /**
     * setter method for AddressBookEntry email variable
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getter method for AddressBookEntry telephone variable
     * @return telephone of AddressBookEntry
     */
    public String getTelephone() {
        return telephone;
    }
    /**
     * setter method for AddressBookEntry telephone variable
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    /**
     * toString method for AddressBookEntry
     * @return  a representation of an AddressBookEntry as a String
     */
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