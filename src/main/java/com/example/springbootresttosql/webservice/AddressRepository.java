package com.example.springbootresttosql.webservice;

import com.example.springbootresttosql.model.AddressBookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This creates a repository that allows us to use various methods to save, delete, and lookup entries in our database
 */
@Repository
public interface AddressRepository extends JpaRepository<AddressBookEntry, Long> {
}
