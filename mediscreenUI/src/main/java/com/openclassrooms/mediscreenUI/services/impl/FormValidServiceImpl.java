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
		|| (patientUpdated.getAddress() == null || patientUpdated.getAddress().isBlank())
		|| (patientUpdated.getGender() == null || patientUpdated.getGender().isBlank())
		|| (patientUpdated.getPhoneNumber() == null || patientUpdated.getPhoneNumber().isBlank())) {
	    result = false;
	} 
	
	return result;
    }
}
