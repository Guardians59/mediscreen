package com.openclassrooms.mediscreenUI.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.openclassrooms.mediscreenUI.beans.PatientBean;
import com.openclassrooms.mediscreenUI.models.GetPatientModel;
import com.openclassrooms.mediscreenUI.proxies.IMicroServicePatientProxy;
import com.openclassrooms.mediscreenUI.services.IFormValidService;
import com.openclassrooms.mediscreenUI.services.IPatientService;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    IMicroServicePatientProxy microServicePatientProxyMock;

    @MockBean
    IPatientService patientService;
    
    @MockBean
    IFormValidService formValidService;

    @BeforeEach
    public void setUp() {
	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getSearchPage() throws Exception {
	mockMvc.perform(get("/patient/search")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED))
		.andExpect(view().name("searchPatient"));
    }
    
    @Test
    public void getPatientPageTest() throws Exception {
	//GIVEN
	PatientBean patient = new PatientBean();
	patient.setId(10);
	patient.setFirstName("Test");
	patient.setLastName("TestTwo");
	patient.setGender("M");
	patient.setBirthday("1980-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	HashMap<String, Object> mapParams = new HashMap<>();
	mapParams.put("firstName", "Test");
	mapParams.put("lastName", "TestTwo");
	mapParams.put("birthday", "1980-10-20");
	GetPatientModel patientModel = new GetPatientModel();
	patientModel.setFirstName("Test");
	patientModel.setLastName("TestTwo");
	patientModel.setBirthday("1980-10-20");
	//WHEN
	when(microServicePatientProxyMock.getPatient(mapParams)).thenReturn(patient);
	when(patientService.getPatient("Test", "TestTwo", "1980-10-20")).thenReturn(patient);
	//THEN
	mockMvc.perform(get("/patient/getPatient")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("getPatientModel", patientModel))
		.andExpect(view().name("getPatient"));
	
    }

    @Test
    public void getPatientTest() throws Exception {
	//GIVEN
	PatientBean patient = new PatientBean();
	patient.setId(10);
	patient.setFirstName("Test");
	patient.setLastName("TestTwo");
	patient.setGender("M");
	patient.setBirthday("1980-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	HashMap<String, Object> mapParams = new HashMap<>();
	mapParams.put("firstName", "Test");
	mapParams.put("lastName", "TestTwo");
	mapParams.put("birthday", "1980-10-20");
	GetPatientModel patientModel = new GetPatientModel();
	patientModel.setFirstName("Test");
	patientModel.setLastName("TestTwo");
	patientModel.setBirthday("1980-10-20");
	//WHEN
	when(microServicePatientProxyMock.getPatient(mapParams)).thenReturn(patient);
	when(patientService.getPatient("Test", "TestTwo", "1980-10-20")).thenReturn(patient);
	//THEN
	mockMvc.perform(post("/patient/getPatient")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("getPatientModel", patientModel))
		.andExpect(view().name("getPatient"))
		.andExpect(status().isOk());
    }
    
    @Test
    public void getPatientByIdPageTest() throws Exception {
	//GIVEN
	PatientBean patient = new PatientBean();
	patient.setId(10);
	patient.setFirstName("Test");
	patient.setLastName("TestTwo");
	patient.setGender("M");
	patient.setBirthday("1980-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	//WHEN
	when(microServicePatientProxyMock.getPatientById(10)).thenReturn(patient);
	when(patientService.getPatientById(10)).thenReturn(patient);
	//THEN
	mockMvc.perform(get("/patient/getPatient/10")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("patientBean", patient))
		.andExpect(view().name("getPatient"));
    }
    
    @Test
    public void updatePatientPageTest() throws Exception {
	//GIVEN
	PatientBean patient = new PatientBean();
	patient.setId(11);
	patient.setFirstName("Test");
	patient.setLastName("TestTwoo");
	patient.setGender("M");
	patient.setBirthday("1985-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	//WHEN
	when(microServicePatientProxyMock.getPatientById(11)).thenReturn(patient);
	when(patientService.getPatientById(11)).thenReturn(patient);
	//THEN
	mockMvc.perform(get("/patient/update/11")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("patientBean", patient))
		.andExpect(view().name("updatePatient"));
    }
    
    @Test
    public void updatePatientTest() throws Exception {
	//GIVEN
	PatientBean patient = new PatientBean();
	patient.setId(11);
	patient.setFirstName("Test");
	patient.setLastName("TestTwoo");
	patient.setGender("M");
	patient.setBirthday("1985-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	PatientBean patientUpdated = new PatientBean();
	patientUpdated.setId(11);
	patientUpdated.setFirstName("Test");
	patientUpdated.setLastName("TestTwoo");
	patientUpdated.setGender("M");
	patientUpdated.setBirthday("1985-10-20");
	patientUpdated.setAddress("2 rue test");
	patientUpdated.setPhoneNumber("03253636");
	//WHEN
	when(formValidService.updatePatientFormValid(patientUpdated)).thenReturn(true);
	when(patientService.updatePatient(11, patientUpdated)).thenReturn(1);
	when(microServicePatientProxyMock.getPatientById(11)).thenReturn(patient);
	when(patientService.getPatientById(11)).thenReturn(patient);
	//THEN
	mockMvc.perform(post("/patient/update/11")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("patientBean", patientUpdated))
		.andExpect(model().attributeExists("updateSuccess"))
		.andExpect(view().name("getPatient"));
    }
    
    @Test
    public void updatePatientSameInfosTest() throws Exception {
	//GIVEN
	PatientBean patient = new PatientBean();
	patient.setId(11);
	patient.setFirstName("Test");
	patient.setLastName("TestTwoo");
	patient.setGender("M");
	patient.setBirthday("1985-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	PatientBean patientUpdated = new PatientBean();
	patientUpdated.setId(11);
	patientUpdated.setFirstName("Test");
	patientUpdated.setLastName("TestTwoo");
	patientUpdated.setGender("M");
	patientUpdated.setBirthday("1985-10-20");
	patientUpdated.setAddress("2 rue test");
	patientUpdated.setPhoneNumber("032536");
	//WHEN
	when(formValidService.updatePatientFormValid(patientUpdated)).thenReturn(true);
	when(patientService.updatePatient(11, patientUpdated)).thenReturn(0);
	when(microServicePatientProxyMock.getPatientById(11)).thenReturn(patient);
	when(patientService.getPatientById(11)).thenReturn(patient);
	//THEN
	mockMvc.perform(post("/patient/update/11")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("patientBean", patientUpdated))
		.andExpect(model().attributeExists("noUpdate"))
		.andExpect(view().name("getPatient"));
    }
    
    @Test
    public void updatePatientErrorTest() throws Exception {
	//GIVEN
	PatientBean patient = new PatientBean();
	patient.setId(11);
	patient.setFirstName("Test");
	patient.setLastName("TestTwoo");
	patient.setGender("M");
	patient.setBirthday("1985-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	PatientBean patientUpdated = new PatientBean();
	patientUpdated.setId(11);
	patientUpdated.setFirstName("Test");
	patientUpdated.setLastName("TestTwoo");
	patientUpdated.setGender("M");
	patientUpdated.setBirthday("1985-10-20");
	patientUpdated.setAddress("");
	patientUpdated.setPhoneNumber("032536");
	//WHEN
	when(formValidService.updatePatientFormValid(patientUpdated)).thenReturn(false);
	when(patientService.updatePatient(11, patientUpdated)).thenReturn(-1);
	when(microServicePatientProxyMock.getPatientById(11)).thenReturn(patient);
	when(patientService.getPatientById(11)).thenReturn(patient);
	//THEN
	mockMvc.perform(post("/patient/update/11")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("patientBean", patientUpdated))
		.andExpect(model().attributeExists("updateError"))
		.andExpect(view().name("updatePatient"));
    }
    
    @Test
    public void addPatientPageTest() throws Exception {
	mockMvc.perform(get("/patient/add")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED))
		.andExpect(view().name("addPatient"));
    }
    
    @Test
    public void addPatientTest() throws Exception {
	//GIVEN
	PatientBean patient = new PatientBean();
	patient.setId(50);
	patient.setFirstName("Test");
	patient.setLastName("TestAddController");
	patient.setGender("M");
	patient.setBirthday("1985-08-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	ResponseEntity resultAdd = Mockito.mock(ResponseEntity.class);
	Mockito.when(resultAdd.getStatusCode()).thenReturn(HttpStatus.CREATED);
	//WHEN
	when(formValidService.addPatientFormValid(patient)).thenReturn(true);
	when(microServicePatientProxyMock.addPatient(patient)).thenReturn(resultAdd);
	when(patientService.addPatient(patient)).thenReturn(true);
	when(patientService.getPatient(patient.getFirstName(), patient.getLastName() , patient.getBirthday())).thenReturn(patient);
	//THEN
	mockMvc.perform(post("/patient/add")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("newPatient", patient))
		.andExpect(model().attributeExists("addSuccess"))
		.andExpect(view().name("getPatient"));
	
    }
    
    @Test
    public void addPatientErrorTest() throws Exception {
	//GIVEN
	PatientBean patient = new PatientBean();
	patient.setId(50);
	patient.setFirstName("Test");
	patient.setLastName("TestAddController");
	//patient.setGender("M");
	patient.setBirthday("1985-08-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	//WHEN
	when(formValidService.addPatientFormValid(patient)).thenReturn(false);
	//THEN
	mockMvc.perform(post("/patient/add")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("newPatient", patient))
		.andExpect(model().attributeDoesNotExist("addSuccess"))
		.andExpect(view().name("addPatient"));
	
    }
    
    @Test
    public void deletePatientTest() throws Exception {
	//GIVEN
	ResponseEntity resultDelete = Mockito.mock(ResponseEntity.class);
	Mockito.when(resultDelete.getStatusCode()).thenReturn(HttpStatus.OK);
	//WHEN
	when(microServicePatientProxyMock.deletePatient(70)).thenReturn(resultDelete);
	when(patientService.deletePatient(70)).thenReturn(true);
	//THEN
	mockMvc.perform(get("/patient/delete/70")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED))
		.andExpect(model().attributeExists("deleteSuccess"))
		.andExpect(view().name("home"));
	
    }
    
    @Test
    public void deletePatientErrorTest() throws Exception {
	//GIVEN
	PatientBean patient = new PatientBean();
	patient.setId(71);
	patient.setFirstName("Test");
	patient.setLastName("TestAddController");
	patient.setGender("M");
	patient.setBirthday("1985-08-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	ResponseEntity resultDelete = Mockito.mock(ResponseEntity.class);
	Mockito.when(resultDelete.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);
	//WHEN
	when(microServicePatientProxyMock.deletePatient(71)).thenReturn(resultDelete);
	when(patientService.deletePatient(71)).thenReturn(false);
	when(patientService.getPatientById(71)).thenReturn(patient);
	//THEN
	mockMvc.perform(get("/patient/delete/71")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED))
		.andExpect(model().attributeExists("deleteError"))
		.andExpect(view().name("deletePatient"));
	
    }
    
    @Test
    public void confirmDeletePatientTest() throws Exception {
	//GIVEN
	PatientBean patient = new PatientBean();
	patient.setId(71);
	patient.setFirstName("Test");
	patient.setLastName("TestAddController");
	patient.setGender("M");
	patient.setBirthday("1985-08-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	//WHEN
	when(patientService.getPatientById(71)).thenReturn(patient);
	//THEN
	mockMvc.perform(get("/patient/delete/confirm/71")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("patientBean", patient))
		.andExpect(view().name("deletePatient"));
	
    }

}
