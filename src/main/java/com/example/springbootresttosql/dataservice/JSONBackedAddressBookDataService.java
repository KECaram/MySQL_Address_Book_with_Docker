package com.example.springbootresttosql.dataservice;

import com.example.springbootresttosql.model.AddressBookEntry;
import com.example.springbootresttosql.webservice.AddressRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;

/**
 * This interface describes a data service for persisting JSON data through to a database
 */
public class JSONBackedAddressBookDataService implements DataService{
    /**
     * Array of all address book entries created from our JSON data
     */
    private final List<AddressBookEntry> addressBookEntries = new ArrayList<>();

    /**
     * Reads a JSON file into a tree. Gets all first level keys of our json tree.
     * Maps each object to our addressBookEntry class based on the first level key
     * Finally, compiles all entries into an ArrayList that contains all entries.
     * @throws IOException when there is an input/output error
     * @return addressBookEntries which is a list of all entries contained in our JSON file
     */
    @Override
    public List<AddressBookEntry> addToDatabase(MultipartFile file, AddressRepository repository) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        List<String> keys = new ArrayList<>();
        JsonNode jsonNode = mapper.readTree(file.getInputStream());
        Iterator<String> iterator = jsonNode.fieldNames();
        iterator.forEachRemaining(e -> keys.add(e));

        for (String key:keys) {
            AddressBookEntry entry  = mapper.readValue(String.valueOf(jsonNode.get(key)), AddressBookEntry.class);
            repository.save(entry);
            addressBookEntries.add(entry);
        }
        return addressBookEntries;
    }
}
