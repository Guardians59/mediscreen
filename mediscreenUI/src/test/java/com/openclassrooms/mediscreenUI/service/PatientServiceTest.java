package com.openclassrooms.mediscreenUI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
	patient.setId(25);
	patient.setFirstName("Test");
	patient.setLastName("TestThree");
	patient.setGender("F");
	patient.setBirthday("1980-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	//WHEN
	when(microServicePatientProxyMock.getPatient("Test", "TestThree", "1980-10-20")).thenReturn(patient);
	getPatient = patientService.getPatient("Test", "TestThree", "1980-10-20");
	//THEN
	assertEquals(getPatient.getAddress(), patient.getAddress());
    }
    
    @Test
    public void getPatientByIdTest() {
	//GIVEN
	PatientBean getPatient = new PatientBean();
	PatientBean patient = new PatientBean();
	patient.setId(25);
	patient.setFirstName("Test");
	patient.setLastName("TestThree");
	patient.setGender("F");
	patient.setBirthday("1980-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	//WHEN
	when(microServicePatientProxyMock.getPatientById(25)).thenReturn(patient);
	getPatient = patientService.getPatientById(25);
	//THEN
	assertEquals(getPatient.getAddress(), patient.getAddress());
    }
    
    @Test
    public void updatePatientTest() {
	//GIVEN
	int result;
	PatientBean patient = new PatientBean();
	patient.setId(35);
	patient.setFirstName("Test");
	patient.setLastName("TestThree");
	patient.setGender("F");
	patient.setBirthday("1980-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	PatientBean patientUpdated = new PatientBean();
	patientUpdated.setId(35);
	patientUpdated.setFirstName("Test");
	patientUpdated.setLastName("TestThree");
	patientUpdated.setGender("F");
	patientUpdated.setBirthday("1985-10-20");
	patientUpdated.setAddress("2 rue test");
	patientUpdated.setPhoneNumber("03253636");
	//WHEN
	when(microServicePatientProxyMock.getPatientById(35)).thenReturn(patient);
	result = patientService.updatePatient(35, patientUpdated);
	//THEN
	assertEquals(result, 1);
    }
    
    @Test
    public void updatePatientSameInfosTest() {
	//GIVEN
	int result;
	PatientBean patient = new PatientBean();
	patient.setId(35);
	patient.setFirstName("Test");
	patient.setLastName("TestThree");
	patient.setGender("F");
	patient.setBirthday("1980-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	PatientBean patientUpdated = new PatientBean();
	patientUpdated.setId(35);
	patientUpdated.setFirstName("Test");
	patientUpdated.setLastName("TestThree");
	patientUpdated.setGender("F");
	patientUpdated.setBirthday("1980-10-20");
	patientUpdated.setAddress("2 rue test");
	patientUpdated.setPhoneNumber("032536");
	//WHEN
	when(microServicePatientProxyMock.getPatientById(35)).thenReturn(patient);
	result = patientService.updatePatient(35, patientUpdated);
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
    
    @Test
    public void deletePatientTest() {
	//GIVEN
	boolean result;
	ResponseEntity resultDelete = Mockito.mock(ResponseEntity.class);
	Mockito.when(resultDelete.getStatusCode()).thenReturn(HttpStatus.OK);
	//WHEN
	when(microServicePatientProxyMock.deletePatient(60)).thenReturn(resultDelete);
	result = patientService.deletePatient(60);
	//THEN
	assertEquals(result, true);
    }
    
    @Test
    public void deletePatientErrorTest() {
	//GIVEN
	boolean result;
	ResponseEntity resultDelete = Mockito.mock(ResponseEntity.class);
	Mockito.when(resultDelete.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);
	//WHEN
	when(microServicePatientProxyMock.deletePatient(61)).thenReturn(resultDelete);
	result = patientService.deletePatient(61);
	//THEN
	assertEquals(result, false);
    }
    
    @Test
    public void getAllByNameTest() {
	//GIVEN
	List<PatientBean> listPatient = new ArrayList<>();
	PatientBean patient1 = new PatientBean();
	patient1.setId(50);
	patient1.setFirstName("TestFamily");
	patient1.setLastName("TestFamilyName");
	patient1.setGender("F");
	patient1.setBirthday("1980-02-10");
	listPatient.add(patient1);
	PatientBean patient2 = new PatientBean();
	patient2.setId(51);
	patient2.setFirstName("TestFamilyBis");
	patient2.setLastName("TestFamilyName");
	patient2.setGender("M");
	patient2.setBirthday("1978-04-10");
	listPatient.add(patient2);
	List<PatientBean> list = new ArrayList<>();
	//WHEN
	when(microServicePatientProxyMock.getAllByName("TestFamilyName")).thenReturn(listPatient);
	list = patientService.getAllByName("TestFamilyName");
	//THEN
	assertEquals(list.size(), 2);
    }
    
    @Test
    public void getAllByNameErrorTest() {
	//GIVEN
	List<PatientBean> listPatient = new ArrayList<>();
	List<PatientBean> list = new ArrayList<>();
	//WHEN
	when(microServicePatientProxyMock.getAllByName("TestFamilyName")).thenReturn(listPatient);
	list = patientService.getAllByName("TestFamilyName");
	//THEN
	assertEquals(list.size(), 0);
	
    }

}
