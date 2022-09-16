package com.openclassrooms.mspatient.controller.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.openclassrooms.mspatient.service.IPatientService;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerIT {
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    WebApplicationContext webApplicationContext;
    
    @Autowired
    IPatientService patientService;
    
    @BeforeEach
    public void setUp() {
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void getPatientTest() throws Exception {
	
	mockMvc.perform(MockMvcRequestBuilders.get("/patient/get?firstName=Test&lastName=TestNone&birthday=1966-12-31")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.gender").value("F"))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }

}
