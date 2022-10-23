package com.example.springbootresttosql.webservice;

import com.example.springbootresttosql.dataservice.CSVBackedAddressBookDataService;
import com.example.springbootresttosql.dataservice.JSONBackedAddressBookDataService;
import com.example.springbootresttosql.dataservice.XMLBackedAddressBookDataService;
import com.example.springbootresttosql.model.AddressBookEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

/**
 *  springboot controller class that shows up to handle a fileupload and a form that submits data as a JSON String
 */
@Controller
public class FileUploaderController {

    /**
     * create a repository that connects to a database
     */
    @Autowired
    AddressRepository addressRepository;

    /**
     * Method that performs a GET request to the endpoint localhost:8080/uploader
     * @return uploader.html which is an HTML form that accepts a file
     */
    @RequestMapping(method = RequestMethod.GET, value = "/uploader")
    public String file(){
        return "uploader.html";
    }

    /**
     * Method that performs a GET request to the endpoint localhost:8080/form
     * @return form.html which is an HTML form that accepts all the fields in the AddressBookEntry class
     */
    @RequestMapping(method = RequestMethod.GET, value = "/form")
    public String form(){
        return "form.html";
    }

    /**
     * Method called after a POST request to the endpoint localhost:8080/uploader.
     * Method pulls the posted file from the response body, and determines which data service to call.
     * The data service parses the file, and returns a list of entries from the file.
     * @param file a representation of the AddressBookEntry class either in JSON, CSV, or XML format.
     * @return list of entries from processed and an OK response request is
     * @throws ProcessingException if there is an error processing the file
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/uploader")
    public ResponseEntity<List<AddressBookEntry>> createFromFile(@RequestParam(value = "file", required = false) MultipartFile file)
            throws ProcessingException{
        List <AddressBookEntry> addressBookEntries = new ArrayList<>();
        try {
            if(file.getOriginalFilename().contains(".json")){
                JSONBackedAddressBookDataService service = new JSONBackedAddressBookDataService();
                addressBookEntries = service.addToDatabase(file, addressRepository);
            }
            else if(file.getOriginalFilename().contains(".csv")) {
                CSVBackedAddressBookDataService service = new CSVBackedAddressBookDataService();
                addressBookEntries = service.addToDatabase(file, addressRepository);
            }else if(file.getOriginalFilename().contains(".xml")){
                XMLBackedAddressBookDataService service = new XMLBackedAddressBookDataService();
                addressBookEntries = service.addToDatabase(file, addressRepository);

            }else{
                throw new ProcessingException("File type not supported", new Throwable());
            }
        } catch (Exception e) {
            throw new ProcessingException("Failed to process file.", e);
        }
        return new ResponseEntity<>(addressBookEntries, HttpStatus.OK);
    }

    /**
     * Method called after a POST request to the endpoint localhost:8080/form.
     * Method pulls the filled out form values as a JSON String from the response body.
     * The method then maps that string to the AddressBookEntry class and persists it through to the database.
     * @param text a representation of the AddressBookEntry class as a JSON formatted String
     * @return AddressBookEntry containing the passed data and an OK response request is
     * @throws ProcessingException if there is an error processing the passed JSON String
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/form")
    public ResponseEntity<String> createFromForm(@RequestBody String text) throws ProcessingException {
        try {
            // a much better way
            ObjectMapper mapper = new ObjectMapper();
            AddressBookEntry entry  = mapper.readValue(text, AddressBookEntry.class);
            addressRepository.save(entry);

        } catch (Exception e) {
            throw new ProcessingException("Failed to process file.", e);
        }
        return new ResponseEntity<>("Upload success!", HttpStatus.OK);
    }

}
