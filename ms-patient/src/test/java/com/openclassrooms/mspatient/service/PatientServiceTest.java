package com.openclassrooms.mspatient.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
	assertEquals(patient.getAddress() == null, true);
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
	String phone = patient.getPhoneNumber();
	boolean result = false;
	//WHEN
	patient.setPhoneNumber("");
	result = patientService.updatePatient(patient.getId(), patient);
	String newPhone = patientRepository.findPatient("Test", "TestNone", "1966-12-31").get().getPhoneNumber();
	//THEN
	assertEquals(result, false);
	assertEquals(phone, newPhone);
    }

}
