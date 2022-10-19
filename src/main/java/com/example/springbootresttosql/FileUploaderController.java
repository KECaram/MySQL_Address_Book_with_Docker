package com.example.springbootresttosql;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.AddressBookEntry;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** simple springboot controller class example that shows up to handle a fileupload
 *
 */
@Controller
public class FileUploaderController {

    @RequestMapping(method = RequestMethod.GET, value = "/uploader")
    public String form(){
        return "form.html";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/uploader")
    /**
     * Given a file that is a JSON representation of a 'Person' object, this endpoint will convert contents of file into
     * Java objects
     *
     * @param file a json representation of a Person. (Right now just an id)
     */
    public ResponseEntity<List<AddressBookEntry>> create(@RequestParam(value = "file", required = false) MultipartFile file) throws ProcessingException {
        System.out.println(file.getOriginalFilename());
        try {
            JSONObject obj = new JSONObject(file.getInputStream());
            Iterator<String> keys = obj.keys();

            while(keys.hasNext()) {
                String key = keys.next();
                if (obj.get(key) instanceof JSONObject) {
                    System.out.println(key.a);
                }
            }

        } catch (Exception e) {
            throw new ProcessingException("Failed to processing file.", e);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
