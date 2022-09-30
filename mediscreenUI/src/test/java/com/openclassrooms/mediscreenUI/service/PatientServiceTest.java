package com.openclassrooms.mediscreenUI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
	
	when(microServicePatientProxyMock.getPatient(mapParams)).thenReturn(patient);
	getPatient = patientService.getPatient("Test", "TestThree", "1980-10-20");
	
	assertEquals(getPatient.getAddress(), patient.getAddress());
    }
    
    @Test
    public void getPatientByIdTest() {
	PatientBean getPatient = new PatientBean();
	PatientBean patient = new PatientBean();
	patient.setId(15);
	patient.setFirstName("Test");
	patient.setLastName("TestThree");
	patient.setGender("F");
	patient.setBirthday("1980-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	
	when(microServicePatientProxyMock.getPatientById(15)).thenReturn(patient);
	getPatient = patientService.getPatientById(15);
	
	assertEquals(getPatient.getAddress(), patient.getAddress());
    }
    
    @Test
    public void updatePatientTest() {
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
	
	when(microServicePatientProxyMock.getPatientById(15)).thenReturn(patient);
	result = patientService.updatePatient(15, patientUpdated);
	
	assertEquals(result, 1);
    }
    
    @Test
    public void updatePatientSameInfosTest() {
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
	
	when(microServicePatientProxyMock.getPatientById(15)).thenReturn(patient);
	result = patientService.updatePatient(15, patientUpdated);
	
	assertEquals(result, 0);
    }

}
