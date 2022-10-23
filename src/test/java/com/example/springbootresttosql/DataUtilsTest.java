package com.example.springbootresttosql;

import com.example.springbootresttosql.dataservice.CSVBackedAddressBookDataService;
import com.example.springbootresttosql.dataservice.JSONBackedAddressBookDataService;
import com.example.springbootresttosql.dataservice.XMLBackedAddressBookDataService;
import com.example.springbootresttosql.model.AddressBookEntry;
import com.example.springbootresttosql.webservice.AddressRepository;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the data processing utilities used by the endpoint
 */
@SpringBootTest
@AutoConfigureMockMvc
class DataUtilsTest {

    /**
     * set up an AddressBookEntry to test our data utilities against
     */
    final AddressBookEntry testEntry = new AddressBookEntry("Michael", "Scott", "1 First St.",
            "Apt 115", "New York", "NY", "10001", "1234567891",
            "mscott@dundermifflen.com");

    /**
     * Mock an MVC to run our application
     */
    @Autowired
    private MockMvc mvc;

    /**
     * Create a repository to actually persist data through to the DB
     */
    @Autowired
    AddressRepository addressRepository;

    /**
     * All setters are used in our actual data methods so this test is just testing all the getter methods
     * of the AddressBookEntry class
     */
    @Test
    public void DataModelTest(){
        assertEquals(testEntry.getFirst_name(), "Michael");
        assertEquals(testEntry.getLast_name(), "Scott");
        assertEquals(testEntry.getStreet_address(), "1 First St.");
        assertEquals(testEntry.getAdditional_address(), "Apt 115");
        assertEquals(testEntry.getAdditional_address(), "Apt 115");
        assertEquals(testEntry.getCity_or_town(), "New York");
        assertEquals(testEntry.getState(), "NY");
        assertEquals(testEntry.getZipcode(), "10001");
        assertEquals(testEntry.getTelephone(), "1234567891");
        assertEquals(testEntry.getEmail(), "mscott@dundermifflen.com");
    }

    /**
     * Test creating AddressBookEntry from CSV and persisting it to database
     * @throws IOException when there is an input/output error
     * @throws CsvValidationException when there is an error parsing the CSV file
     */
    @Test
    public void csvTest() throws IOException, CsvValidationException {
        //set up entry to test against
        List<AddressBookEntry> compare = new ArrayList<>();
        compare.add(testEntry);

        //build entry by creating data service and calling the addtoDatabase method
        File file = new File("src/main/resources/testEntry.csv");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        List<AddressBookEntry> test = new CSVBackedAddressBookDataService().addToDatabase(multipartFile, addressRepository);

        //have to manually set id because they are auto-generated uniquely when added to db
        compare.get(0).setId(test.get(0).getId());
        //compares our test entry to the entry that we just submitted to the DB - actually pulls it from the DB
        assertEquals(addressRepository.findById(test.get(0).getId()).get().toString(), compare.get(0).toString());
        //delete added entry to keep DB clean
        addressRepository.delete(test.get(0));
    }

    /**
     * Test creating AddressBookEntry from JSON and persisting it to database
     * @throws IOException when there is an input/output error
     */
    @Test
    public void jsonTest() throws IOException  {
        //set up entry to test against
        List<AddressBookEntry> compare = new ArrayList<>();
        compare.add(testEntry);

        //build entry by creating data service and calling the addtoDatabase method
        File file = new File("src/main/resources/testEntry.json");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        List<AddressBookEntry> test = new JSONBackedAddressBookDataService().addToDatabase(multipartFile, addressRepository);

        //have to manually set id because they are auto-generated uniquely when added to db
        compare.get(0).setId(test.get(0).getId());
        //compares our test entry to the entry that we just submitted to the DB - actually pulls it from the DB
        assertEquals(addressRepository.findById(test.get(0).getId()).get().toString(), compare.get(0).toString());
        //delete added entry to keep DB clean
        addressRepository.delete(test.get(0));
    }

    /**
     * Test creating AddressBookEntry from XML and persisting it to database
     * @throws IOException when there is an input/output error
     * @throws ParserConfigurationException when there is a serious XML configuration error
     * @throws SAXException when there is an error parsing XML
     */
    @Test
    public void xmlTest() throws IOException, ParserConfigurationException, SAXException {
        //set up entry to test against
        List<AddressBookEntry> compare = new ArrayList<>();
        compare.add(testEntry);

        //build entry by creating data service and calling the addtoDatabase method
        File file = new File("src/main/resources/testEntry.xml");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        List<AddressBookEntry> test = new XMLBackedAddressBookDataService().addToDatabase(multipartFile, addressRepository);

        //have to manually set id because they are auto-generated uniquely when added to db
        compare.get(0).setId(test.get(0).getId());
        //compares our test entry to the entry that we just submitted to the DB - actually pulls it from the DB
        assertEquals(addressRepository.findById(test.get(0).getId()).get().toString(), compare.get(0).toString());
        //delete added entry to keep DB clean
        addressRepository.delete(test.get(0));
    }

}
