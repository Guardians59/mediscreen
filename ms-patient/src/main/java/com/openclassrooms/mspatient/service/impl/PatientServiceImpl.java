package com.openclassrooms.mspatient.service.impl;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mspatient.model.Patient;
import com.openclassrooms.mspatient.repository.IPatientRepository;
import com.openclassrooms.mspatient.service.IPatientService;

@Service
public class PatientServiceImpl implements IPatientService {

    @Autowired
    IPatientRepository patientRepository;

    private static Logger logger = LogManager.getLogger(PatientServiceImpl.class);

    @Override
    public boolean addPatient(Patient patient) {
	Patient newPatient = new Patient();
	boolean result = false;
	logger.debug("Adding a new patient");
	
	if ((patient.getFirstName() != null) && (patient.getLastName() != null) && (patient.getBirthday() != null)
		&& (patient.getGender() != null) && (patient.getAddress() != null) && (patient.getPhoneNumber() != null)) {
	    newPatient.setFirstName(patient.getFirstName());
	    newPatient.setLastName(patient.getLastName());
	    newPatient.setBirthday(patient.getBirthday());
	    newPatient.setGender(patient.getGender());
	    newPatient.setAddress(patient.getAddress());
	    newPatient.setPhoneNumber(patient.getPhoneNumber());
	    result = true;
	    logger.info("Added a new patient successfully executed");
	} else {
	    logger.error("An error occurred while adding the patient");
	}
	return result;
    }

    @Override
    public Patient getPatient(String firstName, String lastName, String birthday) {
	Optional<Patient> patientOptional = patientRepository.findPatient(firstName, lastName, birthday);
	Patient patient = new Patient();
	logger.debug("Search patient with firstname : " + firstName + " lastname : " + lastName + " and birthday : "
		+ birthday);
	if (patientOptional.isPresent()) {
	    patient = patientOptional.get();
	    logger.info("Patient successfully found");
	} else {
	    logger.error("No patients found");
	}

	return patient;
    }

    @Override
    public boolean updatePatient() {
	// TODO Auto-generated method stub
	return false;
    }

}
