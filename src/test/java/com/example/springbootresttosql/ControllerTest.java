package com.example.springbootresttosql;

import com.example.springbootresttosql.model.AddressBookEntry;
import com.example.springbootresttosql.webservice.AddressRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import java.io.File;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Tests for the endpoint controller
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

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
     * set up an AddressBookEntry to test our data utilities against
     */
    final AddressBookEntry testEntry = new AddressBookEntry("Michael", "Scott", "1 First St.",
            "Apt 115", "New York", "NY", "10001", "1234567891",
            "mscott@dundermifflen.com");

    /**
     * Test GET for localhost:8080/uploader endpoint
     */
    @Test
    public void launchGETUploaderTest() throws Exception {
        this.mvc.perform(get("/uploader")).andDo(print()).andExpect(status().isOk());
    }

    /**
     * Test GET for localhost:8080/form endpoint
     */
    @Test
    public void launchGETFormTest() throws Exception {
        this.mvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
    }

    /**
     * Test submitting CSV file to endpoint
     * @throws UnirestException if there is an issue with Unirest
     * @throws JsonProcessingException if there is problem prossing json
     * @throws JSONException if there is issue parsing json
     */
    @Test
    public void POSTtestCSV() throws UnirestException, JsonProcessingException, JSONException {
        String[] args = new String[0];
        SpringBootResTtoSqlApplication.main(args);
        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/uploader")
                        .field("file", new File("src/main/resources/testEntry.csv")).asJson();
        ObjectMapper mapper = new ObjectMapper();
        AddressBookEntry entry  = mapper.readValue(response.getBody().getArray().get(0).toString(), AddressBookEntry.class);
        addressRepository.deleteById(entry.getId());
    }

    /**
     * Test submitting JSON file to endpoint
     * @throws UnirestException if there is an issue with Unirest
     * @throws JsonProcessingException if there is problem prossing json
     * @throws JSONException if there is issue parsing json
     */
    @Test
    public void POSTtestJSON() throws UnirestException, JsonProcessingException, JSONException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/uploader")
                .field("file", new File("src/main/resources/testEntry.json")).asJson();
        ObjectMapper mapper = new ObjectMapper();
        AddressBookEntry entry  = mapper.readValue(response.getBody().getArray().get(0).toString(), AddressBookEntry.class);
        addressRepository.deleteById(entry.getId());
    }

    /**
     * Test submitting XML file to endpoint
     * @throws UnirestException if there is an issue with Unirest
     * @throws JsonProcessingException if there is problem prossing json
     * @throws JSONException if there is issue parsing json
     */
    @Test
    public void POSTtestXML() throws UnirestException, JsonProcessingException, JSONException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/uploader")
                .field("file", new File("src/main/resources/testEntry.xml")).asJson();
        ObjectMapper mapper = new ObjectMapper();
        AddressBookEntry entry  = mapper.readValue(response.getBody().getArray().get(0).toString(), AddressBookEntry.class);
        addressRepository.deleteById(entry.getId());
    }

}
