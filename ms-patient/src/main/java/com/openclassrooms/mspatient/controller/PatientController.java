package com.openclassrooms.mspatient.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mspatient.controller.exception.ErrorGetPatient;
import com.openclassrooms.mspatient.model.Patient;
import com.openclassrooms.mspatient.service.IPatientService;

import io.swagger.annotations.ApiOperation;

@RestController
public class PatientController {

    @Autowired
    IPatientService patientService;

    @ApiOperation(value = "Recupere les informations d'un patient.")
    @GetMapping("/patient/get")
    public Patient getPatient(@RequestParam String firstName, @RequestParam String lastName,
	    @RequestParam String birthday) {

	Patient patient = patientService.getPatient(firstName, lastName, birthday);
	if (patient == null)
	    throw new ErrorGetPatient("An error occurred while searching for the patient");

	return patient;
    }
    
    @ApiOperation(value = "Envoie les informations d'un patient au proxy.")
    @PostMapping("/patient/get")
    public Patient getPatientProxy(@RequestBody HashMap<String, Object> mapParams) {
	String firstName = mapParams.get("firstName").toString();
	String lastName = mapParams.get("lastName").toString();
	String birthday = mapParams.get("birthday").toString();
	
	Patient patient = patientService.getPatient(firstName, lastName, birthday);
	if (patient == null)
	    throw new ErrorGetPatient("An error occurred while searching for the patient");

	return patient;
    }

    @PostMapping("/patient/add")
    public ResponseEntity<?> addPatient(@RequestBody Patient patient) {
	boolean result = patientService.addPatient(patient);
	if (result == true) {
	    return ResponseEntity.status(HttpStatus.CREATED).body("Added a new patient " + patient.getFirstName() + " "
		    + patient.getLastName() + " with success");
	} else {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
    }

}
