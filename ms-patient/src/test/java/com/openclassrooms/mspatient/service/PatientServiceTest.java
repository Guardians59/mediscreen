package com.openclassrooms.mspatient.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.mspatient.model.Patient;

@SpringBootTest
public class PatientServiceTest {
    
    @Autowired
    IPatientService patientService;
    
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

}
