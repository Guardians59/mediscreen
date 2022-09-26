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

	if ((patient.getFirstName() != null || patient.getFirstName().isEmpty())
		&& (patient.getLastName() != null || patient.getLastName().isEmpty())
		&& (patient.getBirthday() != null || patient.getBirthday().isEmpty())
		&& (patient.getGender() != null || patient.getGender().isEmpty())
		&& (patient.getAddress() != null || patient.getAddress().isEmpty())
		&& (patient.getPhoneNumber() != null || patient.getPhoneNumber().isEmpty())) {
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
    public boolean updatePatient(int id, Patient patientUpdated) {
	boolean result = false;
	Optional<Patient> patientOptional = patientRepository.findById(id);
	Patient patient = new Patient();
	logger.debug("Patient update");
	if (patientOptional.isPresent()) {
	    patient = patientOptional.get();
	    logger.info("Patient successfully found");
	    if ((patientUpdated.getAddress() == null || patientUpdated.getAddress().isBlank())
		    || (patientUpdated.getBirthday() == null || patientUpdated.getBirthday().isBlank())
		    || (patientUpdated.getFirstName() == null || patientUpdated.getFirstName().isBlank())
		    || (patientUpdated.getGender() == null || patientUpdated.getGender().isBlank())
		    || (patientUpdated.getLastName() == null || patientUpdated.getLastName().isBlank())
		    || (patientUpdated.getPhoneNumber() == null || patientUpdated.getPhoneNumber().isBlank())) {
		logger.error("Information is missing for the update");
		
	    } else {
		patient.setAddress(patientUpdated.getAddress());
		patient.setBirthday(patientUpdated.getBirthday());
		patient.setFirstName(patientUpdated.getFirstName());
		patient.setGender(patientUpdated.getGender());
		patient.setLastName(patientUpdated.getLastName());
		patient.setPhoneNumber(patientUpdated.getPhoneNumber());
		patientRepository.save(patient);
		logger.info("Updated patient information");
		result = true;
	    }
	} else {
	    logger.error("No patients found");
	}
	return result;
    }

}
