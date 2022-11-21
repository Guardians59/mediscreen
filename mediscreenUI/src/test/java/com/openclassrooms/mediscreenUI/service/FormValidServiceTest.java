package com.openclassrooms.mediscreenUI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.mediscreenUI.beans.NoteBean;
import com.openclassrooms.mediscreenUI.beans.PatientBean;
import com.openclassrooms.mediscreenUI.services.IFormValidService;

@SpringBootTest
public class FormValidServiceTest {
    
    @Autowired
    IFormValidService formValidService;
    
    @Test
    public void updatePatientFormValidTest() {
	//GIVEN
	PatientBean patient = new PatientBean();
	patient.setFirstName("TestForm");
	patient.setLastName("FormTest");
	patient.setBirthday("1990-10-10");
	patient.setAddress("3 rue");
	patient.setGender("M");
	patient.setPhoneNumber("111-222-444");
	boolean result = false;
	//WHEN
	result = formValidService.updatePatientFormValid(patient);
	//THEN
	assertEquals(result, true);
    }
    
    @Test
    public void updatePatientFormValidErrorTest() {
	//GIVEN
	PatientBean patient = new PatientBean();
	patient.setFirstName("TestForm");
	patient.setLastName("");
	patient.setBirthday("1990-10-10");
	patient.setAddress("3 rue");
	patient.setGender("M");
	patient.setPhoneNumber("111-222-444");
	boolean result = false;
	//WHEN
	result = formValidService.updatePatientFormValid(patient);
	//THEN
	assertEquals(result, false);
    }
    
    @Test
    public void addPatientFormValidTest() {
	//GIVEN
	PatientBean patient = new PatientBean();
	patient.setFirstName("TestForm");
	patient.setLastName("FormTest");
	patient.setBirthday("1990-10-10");
	patient.setGender("M");
	boolean result = false;
	//WHEN
	result = formValidService.addPatientFormValid(patient);
	//THEN
	assertEquals(result, true);
    }
    
    @Test
    public void addPatientFormValidErrorTest() {
	//GIVEN
	PatientBean patient = new PatientBean();
	patient.setFirstName("TestForm");
	patient.setLastName("");
	patient.setBirthday("1990-10-10");
	patient.setAddress("3 rue");
	patient.setGender("M");
	patient.setPhoneNumber("111-222-444");
	boolean result = false;
	//WHEN
	result = formValidService.updatePatientFormValid(patient);
	//THEN
	assertEquals(result, false);
    }
    
    @Test
    public void addNoteFormValidTest() {
	//GIVEN
	NoteBean note = new NoteBean();
	note.setNote("Test note form valid");
	boolean result = false;
	//WHEN
	result = formValidService.addNoteFormValid(note);
	//THEN
	assertEquals(result, true);
    }
    
    @Test
    public void addNoteFormValidErrorTest() {
	//GIVEN
	NoteBean note = new NoteBean();
	note.setNote("");
	boolean result = false;
	//WHEN
	result = formValidService.addNoteFormValid(note);
	//THEN
	assertEquals(result, false);
    }
    
    @Test
    public void updateNoteFormValidTest() {
	//GIVEN
	NoteBean note = new NoteBean();
	note.setNote("Test note form valid");
	boolean result = false;
	//WHEN
	result = formValidService.updateNoteFormValid(note);
	//THEN
	assertEquals(result, true);
    }
    
    @Test
    public void updateNoteFormValidErrorTest() {
	//GIVEN
	NoteBean note = new NoteBean();
	note.setNote("");
	boolean result = false;
	//WHEN
	result = formValidService.updateNoteFormValid(note);
	//THEN
	assertEquals(result, false);
    }

}
