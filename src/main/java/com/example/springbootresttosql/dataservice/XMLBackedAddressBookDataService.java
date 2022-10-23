package com.example.springbootresttosql.dataservice;

import com.example.springbootresttosql.model.AddressBookEntry;
import com.example.springbootresttosql.webservice.AddressRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;


/**
 * This interface describes a data service for persisting XML data through to a database
 */
public class XMLBackedAddressBookDataService implements DataService{
    /**
     * Array of all address book entries created from our XML data
     */
    private final List<AddressBookEntry> addressBookEntries = new ArrayList<>();

    /**
     * Reads an XML file, building each line into a big string.
     * Splits the string into individual records based on the <record>.
     * Passess each record to get turned into AddressBookEntries.
     * Finally, compiles all entries into an ArrayList that contains all entries.
     * @throws IOException when there is an input/output error
     * @throws ParserConfigurationException when there is a serious XML configuration error
     * @throws SAXException when there is an error parsing XML
     * @return addressBookEntries which is a list of all entries contained in our XML file
     */
    @Override
    public List<AddressBookEntry> addToDatabase(MultipartFile file, AddressRepository repository)
            throws ParserConfigurationException, IOException, SAXException {
        String xmlRecord = "";
        //read xml file into xml string
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String str;
            // Condition check via buffer.readLine() method
            // holding true upto that the while loop runs
            while ((str = buffer.readLine()) != null) {
                //Skip outermost tag
                if(str.contains("Address")){
                    xmlRecord = xmlRecord.trim();
                    continue;
                }
                xmlRecord = xmlRecord + str + "\n";
            }
        }
        // Catch block to handle the exceptions
        catch (IOException e) {
            // Print the line number here exception occurred
            // using printStackTrace() method
            e.printStackTrace();
        }
        //split string by record tags, and use subtags to set variables in addressbook entry
        String [] recordList = xmlRecord.split("</record>");
        for (int i = 0; i < recordList.length ; i++) {
            String recordData = recordList[i] + "</record>";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document xml = builder.parse(new InputSource(new StringReader(recordData)));
            xml.getDocumentElement().normalize();
            //call helper method to build our entry from the xml document
            AddressBookEntry entry = setAllAddressBookEntryVariables(xml);
            repository.save(entry);
            addressBookEntries.add(entry);
        }
            return addressBookEntries;
    }

    /**
     * Helper method that sets all the variables in our address book entry by grabbing them from our xml document
     * by their element name and passing them into our dependency injection constructor.
     * @param xml which is an XML formatted document containing one record
     * @return the entry which has now been fully initialized with the correct values from
     * the record contained in our XML file
     */
    public AddressBookEntry setAllAddressBookEntryVariables(@NotNull Document xml){

        //get all of our elements by their tag names
        NodeList nodeList = xml.getElementsByTagName("first_name");
        String firstname = nodeList.item(0).getTextContent().trim();

        nodeList = xml.getElementsByTagName("last_name");
        String lastname = nodeList.item(0).getTextContent().trim();

        nodeList = xml.getElementsByTagName("street_address");
        String address = nodeList.item(0).getTextContent().trim();

        nodeList = xml.getElementsByTagName("additional_address");
        String additional = nodeList.item(0).getTextContent().trim();

        nodeList = xml.getElementsByTagName("city_or_town");
        String city = nodeList.item(0).getTextContent().trim();

        nodeList = xml.getElementsByTagName("state");
        String state = nodeList.item(0).getTextContent().trim();

        nodeList = xml.getElementsByTagName("zipcode");
        String zipcode = nodeList.item(0).getTextContent().trim();

        nodeList = xml.getElementsByTagName("telephone");
        String phone = nodeList.item(0).getTextContent().trim();

        nodeList = xml.getElementsByTagName("email");
        String email = nodeList.item(0).getTextContent().trim();

        //pass the elements to constructor
        AddressBookEntry entry = new AddressBookEntry(firstname, lastname, address, additional, city, state, zipcode, phone, email);


        return entry;
    }
}
