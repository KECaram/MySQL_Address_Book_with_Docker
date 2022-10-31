package com.example.springbootresttosql.webservice;

import com.example.springbootresttosql.dataservice.CSVBackedAddressBookDataService;
import com.example.springbootresttosql.dataservice.JSONBackedAddressBookDataService;
import com.example.springbootresttosql.dataservice.XMLBackedAddressBookDataService;
import com.example.springbootresttosql.model.AddressBookEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
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
     * Method that performs a GET request to the endpoint localhost:8080/search
     * @return search.html which is an HTML form that prompts for a search term
     * and then allows the choice of a field to search for said term.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public String search(){
        return "search.html";
    }

    /**
     * Method called after a POST request to the endpoint localhost:8080/uploader.
     * Method pulls the posted file from the response body, and determines which data service to call.
     * The data service parses the file, and returns a list of entries from the file.
     * @param file a representation of the AddressBookEntry class either in JSON, CSV, or XML format.
     * @return list of entries from processed and an OK response request
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
     * @return AddressBookEntry containing the passed data and an OK response request
     * @throws ProcessingException if there is an error processing the passed JSON String
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/form")
    public String createFromForm(@RequestBody String text) throws ProcessingException {
        AddressBookEntry entry;
        try {
            // a much better way
            ObjectMapper mapper = new ObjectMapper();
            entry = mapper.readValue(text, AddressBookEntry.class);
            addressRepository.save(entry);
            return entry.toString();
        } catch (Exception e) {
            throw new ProcessingException("Failed to process file.", e);
        }

    }

    /**
     * Method called after a POST request to the endpoint localhost:8080/search.
     * Method pulls the form values as a JSON String from the response body of search as a Map.
     * The method then extracts each feld and passes them to the corresponding search methods.
     * @param formData a JSON formatted string containing a search term and the field to search
     * @return ResponseEntity containing the data returned by the search method
     * and an OK response request.
     */

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/search")
    public ResponseEntity<List<AddressBookEntry>> handleSearch(@RequestBody MultiValueMap<String, String> formData){

        String term = formData.get("search-term").get(0);
        String field = formData.get("search-field").get(0);
        List<AddressBookEntry> response = null;
        switch (field) {
            case "first_name":
                response = addressRepository.findAddressBookEntriesByFirst_name(term);
                break;
            case "last_name":
                response = addressRepository.findAddressBookEntriesByLast_name(term);
                ;
                break;
            case "street_address":
                response = addressRepository.findAddressBookEntriesByStreet_address(term);
                break;
            case "additional_address":
                response = response = addressRepository.findAddressBookEntriesByAdditional_address(term);
                break;
            case "city_or_town":
                response = addressRepository.findAddressBookEntriesByCity_or_town(term);
                break;
            case "state":
                response = addressRepository.findAddressBookEntriesByState(term);
                break;
            case "zipcode":
                response = addressRepository.findAddressBookEntriesByZipcode(term);
                break;
            case "email":
                response = addressRepository.findAddressBookEntriesByEmail(term);
                break;
            case "telephone":
                response = addressRepository.findAddressBookEntriesByTelephone(term);
                break;
            case "all":
                response = addressRepository.findAddressBookEntriesByAny(term);
        }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }


}
