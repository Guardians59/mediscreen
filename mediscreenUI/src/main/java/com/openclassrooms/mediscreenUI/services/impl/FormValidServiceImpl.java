package com.openclassrooms.mediscreenUI.services.impl;

import org.springframework.stereotype.Service;

import com.openclassrooms.mediscreenUI.beans.NoteBean;
import com.openclassrooms.mediscreenUI.beans.PatientBean;
import com.openclassrooms.mediscreenUI.services.IFormValidService;

@Service
public class FormValidServiceImpl implements IFormValidService {

    @Override
    public boolean updatePatientFormValid(PatientBean patientUpdated) {
	boolean result = true;
	if ((patientUpdated.getFirstName() == null || patientUpdated.getFirstName().isBlank())
		|| (patientUpdated.getLastName() == null || patientUpdated.getLastName().isBlank())
		|| (patientUpdated.getBirthday() == null || patientUpdated.getBirthday().isBlank())
		|| (patientUpdated.getGender() == null || patientUpdated.getGender().isBlank())) {
	    result = false;
	}

	return result;
    }

    @Override
    public boolean addPatientFormValid(PatientBean newPatient) {
	boolean result = true;
	if ((newPatient.getFirstName() == null || newPatient.getFirstName().isBlank())
		|| (newPatient.getLastName() == null || newPatient.getLastName().isBlank())
		|| (newPatient.getBirthday() == null || newPatient.getBirthday().isBlank())
		|| (newPatient.getGender() == null || newPatient.getGender().isBlank())) {
	    result = false;
	}
	return result;
    }

    @Override
    public boolean addNoteFormValid(NoteBean newNote) {
	boolean result = true;
	if ((newNote.getNote() == null || newNote.getNote().isBlank())) {
	    result = false;
	}
	return result;
    }

    @Override
    public boolean updateNoteFormValid(NoteBean noteUpdated) {
	boolean result = true;
	if ((noteUpdated.getNote() == null || noteUpdated.getNote().isBlank())) {
	    result = false;
	}
	return result;
    }
}
