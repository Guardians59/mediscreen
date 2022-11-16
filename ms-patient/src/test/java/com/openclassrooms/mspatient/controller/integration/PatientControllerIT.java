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
		.andExpect(status().isNotFound());
    }
    
    @Test
    public void getPatientByIdTest() throws Exception {
	
	mockMvc.perform(MockMvcRequestBuilders.get("/patient/get/2")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.gender").value("M"))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void getPatientByIdErrorTest() throws Exception {
	
	mockMvc.perform(MockMvcRequestBuilders.get("/patient/get/200")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.gender").doesNotExist())
		.andExpect(status().isNotFound());
    }
    
    @Test
    public void updatePatientTest() throws Exception {
	//GIVEN
	Optional<Patient> patientOptional = patientRepository.findPatient("Test", "TestNone", "1966-12-31");
	Patient patient = new Patient();
	patient = patientOptional.get();
	String phone = patient.getPhoneNumber();
	patient.setPhoneNumber("111-222");
	Gson gson = new Gson();
	String json = gson.toJson(patient);
	
	mockMvc.perform(MockMvcRequestBuilders.put("/patient/update/1")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
	
	//AFTER
	patient.setPhoneNumber(phone);
	patientRepository.save(patient);
	
    }
    
    @Test
    public void updatePatientErrorTest() throws Exception {
	//GIVEN
	Optional<Patient> patientOptional = patientRepository.findPatient("Test", "TestNone", "1966-12-31");
	Patient patient = new Patient();
	patient = patientOptional.get();
	patient.setPhoneNumber("");
	Gson gson = new Gson();
	String json = gson.toJson(patient);
	
	mockMvc.perform(MockMvcRequestBuilders.put("/patient/update/1")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
	
    }
    
    @Test
    public void addPatientAndDeleteTest() throws Exception {
	Patient patient = new Patient();
	patient.setFirstName("Test");
	patient.setLastName("ControllerTest");
	patient.setBirthday("2000-02-10");
	patient.setGender("F");
	Gson gson = new Gson();
	String json = gson.toJson(patient);
	
	mockMvc.perform(MockMvcRequestBuilders.post("/patient/add")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andDo(MockMvcResultHandlers.print());
	
	Optional<Patient> patientOptional = patientRepository.findPatient("Test", "ControllerTest", "2000-02-10");
	int id = patientOptional.get().getId();
	
	mockMvc.perform(MockMvcRequestBuilders.delete("/patient/delete/" + id)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
	
    }
    
    @Test
    public void addPatientErrorTest() throws Exception {
	Patient patient = new Patient();
	patient.setFirstName("Test");
	patient.setLastName("ControllerErrorTest");
	patient.setBirthday("2000-02-10");
	Gson gson = new Gson();
	String json = gson.toJson(patient);
	
	mockMvc.perform(MockMvcRequestBuilders.post("/patient/add")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	
    }
    
    @Test
    public void deletePatientErrorTest() throws Exception {
	mockMvc.perform(MockMvcRequestBuilders.delete("/patient/delete/500")
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
    }
    
    @Test
    public void getAllPatientByNameTest() throws Exception {
	
	mockMvc.perform(MockMvcRequestBuilders.get("/patient/getAllByName/TestNone")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.gender").value("F"))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void getAllPatientByNameErrorTest() throws Exception {
	
	mockMvc.perform(MockMvcRequestBuilders.get("/patient/getAllByName/TestError")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.gender").doesNotExist())
		.andExpect(status().isNotFound());
    }

}
