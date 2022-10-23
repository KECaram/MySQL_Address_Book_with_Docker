package com.example.springbootresttosql.dataservice;

import com.example.springbootresttosql.model.AddressBookEntry;
import com.example.springbootresttosql.webservice.AddressRepository;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

/**
 * This interface describes a simple API for handling Address Book Data
 */
public interface DataService {
    /**
     * Method to take data from a file of a given type, and persist it through to our DB
     *
     * @param file which is a multipart file provided by our endpoint submission
     * @param repository which is a repository for persisting data through to our database
     * @return A map the data.
     * @throws IOException when there is an input/output error
     * @throws ParserConfigurationException when there is a serious XML configuration error
     * @throws SAXException when there is an error parsing XML
     * @throws CsvValidationException when there is an error parsing the csv file
     * @return A list of address book entries
     */
    public List<AddressBookEntry> addToDatabase(MultipartFile file, AddressRepository repository)
            throws IOException, CsvValidationException, ParserConfigurationException, SAXException;
}
