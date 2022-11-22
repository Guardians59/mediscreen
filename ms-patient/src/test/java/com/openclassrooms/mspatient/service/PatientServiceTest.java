package com.openclassrooms.mspatient.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.mspatient.model.Patient;
import com.openclassrooms.mspatient.repository.IPatientRepository;

@SpringBootTest
public class PatientServiceTest {
    
    @Autowired
    IPatientService patientService;
    
    @Autowired
    IPatientRepository patientRepository;
    
    @Test
    public void getPatientTest() {
	//GIVEN
	Patient patient = new Patient();
	//WHEN
	patient = patientService.getPatient("Test", "TestNone", "1966-12-31");
	//THEN
	assertEquals(patient.getAddress() != null, true);
	assertEquals(patient.getGender(), "F");
    }
    
    @Test
    public void getPatientErrorTest() {
	//GIVEN
	Patient patient = new Patient();
	//WHEN
	patient = patientService.getPatient("Test", "TestNone", "1966-12-30");
	//THEN
	assertTrue(patient.getAddress() == null);
    }
    
    @Test
    public void getPatientByIdTest() {
	//GIVEN
	Patient patient = new Patient();
	//WHEN
	patient = patientService.getPatientById(2);
	//THEN
	assertEquals(patient.getLastName(), "TestBorderline");
    }
    
    @Test
    public void getPatientByIdErrorTest() {
	//GIVEN
	Patient patient = new Patient();
	//WHEN
	patient = patientService.getPatientById(200);
	//THEN
	assertTrue(patient.getLastName() == null);
    }
    
    @Test
    public void updatePatientTest() {
	//GIVEN
	Optional<Patient> patientOptional = patientRepository.findPatient("Test", "TestNone", "1966-12-31");
	Patient patient = new Patient();
	patient = patientOptional.get();
	String phone = patient.getPhoneNumber();
	boolean result = false;
	//WHEN
	patient.setPhoneNumber("111-222");
	result = patientService.updatePatient(patient.getId(), patient);
	String newPhone = patientRepository.findPatient("Test", "TestNone", "1966-12-31").get().getPhoneNumber();
	//THEN
	assertEquals(result, true);
	assertNotEquals(phone, newPhone);
	//AFTER
	patient.setPhoneNumber(phone);
	patientRepository.save(patient);
    }
    
    @Test
    public void updatePatientErrorTest() {
	//GIVEN
	Optional<Patient> patientOptional = patientRepository.findPatient("Test", "TestNone", "1966-12-31");
	Patient patient = new Patient();
	patient = patientOptional.get();
	String gender = patient.getGender();
	boolean result = false;
	//WHEN
	patient.setGender("");
	result = patientService.updatePatient(patient.getId(), patient);
	String newGender = patientRepository.findPatient("Test", "TestNone", "1966-12-31").get().getGender();
	//THEN
	assertEquals(result, false);
	assertEquals(gender, newGender);
    }
    
    @Test
    public void addPatientAndDeleteTest() {
	//GIVEN
	Patient patient = new Patient();
	patient.setFirstName("Test");
	patient.setLastName("ServiceTest");
	patient.setBirthday("1999-02-10");
	patient.setGender("M");
	//WHEN
	patientService.addPatient(patient);
	Optional<Patient> patientOptional = patientRepository.findPatient("Test", "ServiceTest", "1999-02-10");
	//THEN
	assertEquals(patientOptional.isPresent(), true);
	
	//GIVEN
	boolean resultDelete = false;
	//WHEN
	resultDelete = patientService.deletePatient(patientOptional.get().getId());
	boolean result = patientRepository.existsById(patientOptional.get().getId());
	//THEN
	assertEquals(resultDelete, true);
	assertEquals(result, false);
    }
    
    @Test
    public void addPatientErrorTest() {
	//GIVEN
	Patient patient = new Patient();
	patient.setFirstName("Test");
	patient.setLastName("ServiceTest2");
	patient.setBirthday("1999-02-12");
	patient.setAddress("1 rue du parc");
	//WHEN
	patientService.addPatient(patient);
	Optional<Patient> patientOptional = patientRepository.findPatient("Test", "ServiceTest2", "1999-02-12");
	//THEN
	assertEquals(patientOptional.isPresent(), false);
    }
    
    @Test
    public void getPatientByNameTest() {
	//GIVEN
	List<Patient> result = new ArrayList<>();
	//WHEN
	result = patientService.getAllByName("TestBorderline");
	//THEN
	assertEquals(result.isEmpty(), false);
    }
    
    @Test
    public void getPatientByNameErrorTest() {
	//GIVEN
	List<Patient> result = new ArrayList<>();
	//WHEN
	result = patientService.getAllByName("TestError");
	//THEN
	assertEquals(result.isEmpty(), true);
    }

}
