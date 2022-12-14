package com.example.springbootresttosql.webservice;

import com.example.springbootresttosql.model.AddressBookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This creates a repository that allows us to use various methods to save, delete, and lookup entries in our database
 */
@Repository
public interface AddressRepository extends JpaRepository<AddressBookEntry, Long> {
    /**
     * Passes a search query to the DB that searches for all entries in the first_name field
     * that are equal to the passed term
    */
    @Query(value = "SELECT * FROM address a WHERE a.first_name = :term", nativeQuery = true)
    List<AddressBookEntry> findAddressBookEntriesByFirst_name(String term);
    /**
     * Passes a search query to the DB that searches for all entries in the last_name field
     * equal to the passed term
     */
    @Query(value = "SELECT * FROM address a WHERE a.last_name = :term", nativeQuery = true)
    List<AddressBookEntry> findAddressBookEntriesByLast_name(String term);
    /**
     * Passes a search query to the DB that searches for all entries in the street_address field
     * equal to the passed term
     */
    @Query(value = "SELECT * FROM address a WHERE a.street_address = :term", nativeQuery = true)
    List<AddressBookEntry> findAddressBookEntriesByStreet_address(String term);
    /**
     * Passes a search query to the DB that searches for all entries in the additional_address field
     * equal to the passed term
     */
    @Query(value = "SELECT * FROM address a WHERE a.additional_address = :term", nativeQuery = true)
    List<AddressBookEntry> findAddressBookEntriesByAdditional_address(String term);
    /**
     * Passes a search query to the DB that searches for all entries in the city_or_town field
     * equal to the passed term
     */
    @Query(value = "SELECT * FROM address a WHERE a.city_or_town = :term", nativeQuery = true)
    List<AddressBookEntry> findAddressBookEntriesByCity_or_town(String term);
    /**
     * Passes a search query to the DB that searches for all entries in the state field
     * equal to the passed term
     */
    @Query(value = "SELECT * FROM address a WHERE a.state = :term", nativeQuery = true)
    List<AddressBookEntry> findAddressBookEntriesByState(String term);
    /**
     * Passes a search query to the DB that searches for all entries in the zipcode field
     * equal to the passed term
     */
    @Query(value = "SELECT * FROM address a WHERE a.zipcode = :term", nativeQuery = true)
    List<AddressBookEntry> findAddressBookEntriesByZipcode(String term);
    /**
     * Passes a search query to the DB that searches for all entries in the email field
     * equal to the passed term
     */
    @Query(value = "SELECT * FROM address a WHERE a.email = :term", nativeQuery = true)
    List<AddressBookEntry> findAddressBookEntriesByEmail(String term);
    /**
     * Passes a search query to the DB that searches for all entries in the telephone field
     * equal to the passed term
     */
    @Query(value = "SELECT * FROM address a WHERE a.telephone = :term", nativeQuery = true)
    List<AddressBookEntry> findAddressBookEntriesByTelephone(String term);
    /**
     * Passes a search query to the DB that searches for all entries in the whole database that
     * equal to the passed term
     */
    @Query(value = "SELECT * FROM address a WHERE :term in (a.first_name, a.last_name, a.street_address," +
            "a.additional_address, a.city_or_town, a.state, a.zipcode, a.email, a.telephone)", nativeQuery = true)
    List<AddressBookEntry> findAddressBookEntriesByAny(String term);
}
