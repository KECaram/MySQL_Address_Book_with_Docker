package com.example.springbootresttosql.dataservice;

import com.example.springbootresttosql.model.AddressBookEntry;
import com.example.springbootresttosql.webservice.AddressRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * This interface describes a data service for persisting CSV data through to a database
 */
public class CSVBackedAddressBookDataService implements DataService{
    /**
     * Array of all address book entries created from our CSV data
     */
    private final List<AddressBookEntry> addressBookEntries = new ArrayList<>();

    /**
     * Creates an instance of a CSVBackedAddressBook
     * Reads a CSV file, and adds each line to an array. Then parses each line into an Address Book entry.
     * Finally, compiles all entries into an ArrayList that contains all entries.
     * @throws IOException when there is an input/output error
     * @throws CsvValidationException when there is a serious csv configuration error
     */

    @Override
    public List<AddressBookEntry> addToDatabase(MultipartFile file, AddressRepository repository) throws IOException, CsvValidationException {

        List<List<String>> records = new ArrayList<List<String>>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        }
        for (int i = 0; i < records.size() ; i++) {
            List record = records.get(i);
            AddressBookEntry entry  = new AddressBookEntry();
            entry.setLast_name(String.valueOf(record.get(0)));
            entry.setFirst_name(String.valueOf(record.get(1)));
            entry.setStreet_address(String.valueOf(record.get(2)));
            entry.setAdditional_address(String.valueOf(record.get(3)));
            entry.setCity_or_town(String.valueOf(record.get(4)));
            entry.setState(String.valueOf(record.get(5)));
            entry.setZipcode(String.valueOf(record.get(6)));
            entry.setTelephone(String.valueOf(record.get(7)));
            entry.setEmail(String.valueOf(record.get(8)));
            repository.save(entry);
            addressBookEntries.add(entry);
        }
        return addressBookEntries;
    }
}
