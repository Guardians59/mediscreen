package com.openclassrooms.mspatient.controller.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.google.gson.Gson;
import com.openclassrooms.mspatient.model.Patient;
import com.openclassrooms.mspatient.repository.IPatientRepository;
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
    
    @Autowired
    IPatientRepository patientRepository;
    
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
    
    @Test
    public void getPatientErrorTest() throws Exception {
	
	mockMvc.perform(MockMvcRequestBuilders.get("/patient/get?firstName=Test&lastName=TestNone&birthday=1966-12-30")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.gender").doesNotExist())
		.andExpect(status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void updatePatientTest() throws Exception {
	//GIVEN
	Optional<Patient> patientOptional = patientRepository.findPatient("Test", "TestNone", "1966-12-31");
	Patient patient = new Patient();
	patient = patientOptional.get();
	patient.setPhoneNumber("111-222");
	Gson gson = new Gson();
	String json = gson.toJson(patient);
	
	mockMvc.perform(MockMvcRequestBuilders.put("/patient/update?id=1")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(content().string("Updated a patient " + "Test" + " TestNone" + " with success"))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
	
    }
    
    @Test
    public void updatePatientErrorTest() throws Exception {
	//GIVEN
	Optional<Patient> patientOptional = patientRepository.findPatient("Test", "TestNone", "1966-12-31");
	Patient patient = new Patient();
	patient = patientOptional.get();
	String phone = patient.getPhoneNumber();
	patient.setPhoneNumber("");
	Gson gson = new Gson();
	String json = gson.toJson(patient);
	
	mockMvc.perform(MockMvcRequestBuilders.put("/patient/update?id=1")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
	//AFTER
	patient.setPhoneNumber(phone);
	patientRepository.save(patient);
    }

}
