package com.openclassrooms.mspatient.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mspatient.model.Patient;
import com.openclassrooms.mspatient.repository.IPatientRepository;
import com.openclassrooms.mspatient.service.IPatientService;

/**
 * La classe PatientServiceImpl est l'implémentation de l'interface IPatientService.
 * 
 * @see IPatientService
 * @author Dylan
 *
 */
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
	/*
	 * On vérifie que les informations soient bien présentes, si tel est le cas
	 * on sauvegarde le nouveau patient en base de données et on renvoit true au boolean,
	 * sinon on renvoit false.
	 */
	if ((patient.getFirstName() == null || patient.getFirstName().isEmpty())
		|| (patient.getLastName() == null || patient.getLastName().isEmpty())
		|| (patient.getBirthday() == null || patient.getBirthday().isEmpty())
		|| (patient.getGender() == null || patient.getGender().isEmpty())) {
	    logger.error("An error occurred while adding the patient");
	} else {
	    newPatient.setFirstName(patient.getFirstName());
	    newPatient.setLastName(patient.getLastName());
	    newPatient.setBirthday(patient.getBirthday());
	    newPatient.setGender(patient.getGender());
	    newPatient.setAddress(patient.getAddress());
	    newPatient.setPhoneNumber(patient.getPhoneNumber());
	    result = true;
	    patientRepository.save(newPatient);
	    logger.info("Added a new patient successfully executed");
	}
	return result;
    }

    @Override
    public Patient getPatient(String firstName, String lastName, String birthday) {
	//On récupére le patient en base de données via son prénom, nom et date de naissance.
	Optional<Patient> patientOptional = patientRepository.findPatient(firstName, lastName, birthday);
	Patient patient = new Patient();
	logger.debug("Search patient with firstname : " + firstName + " lastname : " + lastName + " and birthday : "
		+ birthday);
	if (patientOptional.isPresent()) {
	    patient = patientOptional.get();
	    logger.info("Patient successfully found");
	} else {
	    logger.error("No patient found");
	}

	return patient;
    }
    
    @Override
    public Patient getPatientById(int id) {
	//On récupére le patient en base de données via son id.
	Optional<Patient> patientOptional = patientRepository.findById(id);
	Patient patient = new Patient();
	logger.debug("Search patient with id : " + id);
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
	//On récupére le patient en base de données via son id.
	Optional<Patient> patientOptional = patientRepository.findById(id);
	Patient patient = new Patient();
	logger.debug("Patient update");
	if (patientOptional.isPresent()) {
	    patient = patientOptional.get();
	    logger.info("Patient successfully found");
	    /*
	     * On vérifie que toutes les informations soient bien présentes, afin de mettre à jour
	     * le patient, et de le sauvegarder en base de données.
	     * Le boolean renverra true si la mise est à jour est un succès, false si une
	     * une erreur est rencontrée.
	     */
	    if ((patientUpdated.getBirthday() == null || patientUpdated.getBirthday().isBlank())
		    || (patientUpdated.getFirstName() == null || patientUpdated.getFirstName().isBlank())
		    || (patientUpdated.getGender() == null || patientUpdated.getGender().isBlank())
		    || (patientUpdated.getLastName() == null || patientUpdated.getLastName().isBlank())) {
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
	    logger.error("No patient found");
	}
	return result;
    }

    @Override
    public boolean deletePatient(int id) {
	logger.debug("Delete the patient with id : " + id);
	boolean result = false;
	/*
	 * On vérifie que le patient existe bien en base de données via son id, afin
	 * ensuite de le supprimer.
	 */
	if(patientRepository.existsById(id)) {
	    patientRepository.deleteById(id);
	    result = true;
	    logger.info("Patient deleted with success");
	} else {
	    logger.error("An error occured, no patient found with id : " + id);
	}
	return result;
    }

    @Override
    public List<Patient> getAllByName(String lastName) {
	List<Patient> result = new ArrayList<>();
	//On récupére tous les patients ayant le nom de famille indiqué.
	result = patientRepository.findAllPatientByName(lastName);
	logger.debug("Search patient with lastName : " + lastName);
	if(!result.isEmpty()) {
	    logger.info("Patients successfully found");
	} else {
	    logger.error("No patients found");
	}
	return result;
    }

}
