package com.openclassrooms.mediscreenUI.services.impl;

import org.springframework.stereotype.Service;

import com.openclassrooms.mediscreenUI.beans.PatientBean;
import com.openclassrooms.mediscreenUI.services.IFormValidService;

@Service
public class FormValidServiceImpl implements IFormValidService {

    @Override
    public boolean updateFormValid(PatientBean patientUpdated) {
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
    public boolean addFormValid(PatientBean newPatient) {
	boolean result = true;
	if ((newPatient.getFirstName() == null || newPatient.getFirstName().isBlank())
		|| (newPatient.getLastName() == null || newPatient.getLastName().isBlank())
		|| (newPatient.getBirthday() == null || newPatient.getBirthday().isBlank())
		|| (newPatient.getGender() == null || newPatient.getGender().isBlank())) {
	    result = false;
	} 
	return result;
    }
}
