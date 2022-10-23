package com.example.springbootresttosql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
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
        this.mvc.perform(get("/form")).andDo(print()).andExpect(status().isOk());
    }

}
