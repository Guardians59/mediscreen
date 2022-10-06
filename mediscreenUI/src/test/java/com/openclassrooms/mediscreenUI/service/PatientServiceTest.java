package com.openclassrooms.mediscreenUI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.openclassrooms.mediscreenUI.beans.PatientBean;
import com.openclassrooms.mediscreenUI.proxies.IMicroServicePatientProxy;
import com.openclassrooms.mediscreenUI.services.IPatientService;

@SpringBootTest
public class PatientServiceTest {
    
    @MockBean
    IMicroServicePatientProxy microServicePatientProxyMock;
    
    @Autowired
    IPatientService patientService;
    
    @Test
    public void getPatientTest() {
	//GIVEN
	PatientBean getPatient = new PatientBean();
	PatientBean patient = new PatientBean();
	patient.setId(15);
	patient.setFirstName("Test");
	patient.setLastName("TestThree");
	patient.setGender("F");
	patient.setBirthday("1980-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	HashMap<String, Object> mapParams = new HashMap<>();
	mapParams.put("firstName", "Test");
	mapParams.put("lastName", "TestThree");
	mapParams.put("birthday", "1980-10-20");
	//WHEN
	when(microServicePatientProxyMock.getPatient(mapParams)).thenReturn(patient);
	getPatient = patientService.getPatient("Test", "TestThree", "1980-10-20");
	//THEN
	assertEquals(getPatient.getAddress(), patient.getAddress());
    }
    
    @Test
    public void getPatientByIdTest() {
	//GIVEN
	PatientBean getPatient = new PatientBean();
	PatientBean patient = new PatientBean();
	patient.setId(15);
	patient.setFirstName("Test");
	patient.setLastName("TestThree");
	patient.setGender("F");
	patient.setBirthday("1980-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	//WHEN
	when(microServicePatientProxyMock.getPatientById(15)).thenReturn(patient);
	getPatient = patientService.getPatientById(15);
	//THEN
	assertEquals(getPatient.getAddress(), patient.getAddress());
    }
    
    @Test
    public void updatePatientTest() {
	//GIVEN
	int result;
	PatientBean patient = new PatientBean();
	patient.setId(15);
	patient.setFirstName("Test");
	patient.setLastName("TestThree");
	patient.setGender("F");
	patient.setBirthday("1980-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	PatientBean patientUpdated = new PatientBean();
	patientUpdated.setId(15);
	patientUpdated.setFirstName("Test");
	patientUpdated.setLastName("TestThree");
	patientUpdated.setGender("F");
	patientUpdated.setBirthday("1985-10-20");
	patientUpdated.setAddress("2 rue test");
	patientUpdated.setPhoneNumber("03253636");
	//WHEN
	when(microServicePatientProxyMock.getPatientById(15)).thenReturn(patient);
	result = patientService.updatePatient(15, patientUpdated);
	//THEN
	assertEquals(result, 1);
    }
    
    @Test
    public void updatePatientSameInfosTest() {
	//GIVEN
	int result;
	PatientBean patient = new PatientBean();
	patient.setId(15);
	patient.setFirstName("Test");
	patient.setLastName("TestThree");
	patient.setGender("F");
	patient.setBirthday("1980-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	PatientBean patientUpdated = new PatientBean();
	patientUpdated.setId(15);
	patientUpdated.setFirstName("Test");
	patientUpdated.setLastName("TestThree");
	patientUpdated.setGender("F");
	patientUpdated.setBirthday("1980-10-20");
	patientUpdated.setAddress("2 rue test");
	patientUpdated.setPhoneNumber("032536");
	//WHEN
	when(microServicePatientProxyMock.getPatientById(15)).thenReturn(patient);
	result = patientService.updatePatient(15, patientUpdated);
	//THEN
	assertEquals(result, 0);
    }
    
    @Test
    public void addPatientTest() {
	//GIVEN
	boolean result;
	PatientBean patient = new PatientBean();
	patient.setFirstName("Test");
	patient.setLastName("TestAdd");
	patient.setGender("M");
	patient.setBirthday("1985-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	ResponseEntity resultAdd = Mockito.mock(ResponseEntity.class);
	Mockito.when(resultAdd.getStatusCode()).thenReturn(HttpStatus.CREATED);
	//WHEN
	when(microServicePatientProxyMock.addPatient(patient)).thenReturn(resultAdd);
	result = patientService.addPatient(patient);
	//THEN
	assertEquals(result, true);
    }
    
    @Test
    public void addPatientErrorTest() {
	//GIVEN
	boolean result;
	PatientBean patient = new PatientBean();
	patient.setFirstName("Test");
	patient.setLastName("TestAddError");
	patient.setBirthday("1985-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	ResponseEntity resultAdd = Mockito.mock(ResponseEntity.class);
	Mockito.when(resultAdd.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);
	//WHEN
	when(microServicePatientProxyMock.addPatient(patient)).thenReturn(resultAdd);
	result = patientService.addPatient(patient);
	//THEN
	assertEquals(result, false);
    }

}
