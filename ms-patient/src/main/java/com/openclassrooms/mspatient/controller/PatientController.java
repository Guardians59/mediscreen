package com.openclassrooms.mspatient.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mspatient.controller.exception.ErrorAddPatient;
import com.openclassrooms.mspatient.controller.exception.ErrorDeletePatient;
import com.openclassrooms.mspatient.controller.exception.ErrorGetPatient;
import com.openclassrooms.mspatient.controller.exception.ErrorUpdatePatient;
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
	
	if (patient.getFirstName() == null) {
	    throw new ErrorGetPatient("An error occurred while searching for the patient");
	}
	return patient;
    }

    @ApiOperation(value = "Envoie les informations d'un patient au proxy.")
    @PostMapping("/patient/get")
    public Patient getPatientProxy(@RequestBody HashMap<String, Object> mapParams) {
	String firstName = mapParams.get("firstName").toString();
	String lastName = mapParams.get("lastName").toString();
	String birthday = mapParams.get("birthday").toString();

	Patient patient = patientService.getPatient(firstName, lastName, birthday);
	if (patient.getFirstName() == null)
	    throw new ErrorGetPatient("An error occurred while searching for the patient");

	return patient;
    }
    
    @ApiOperation(value = "Recupere les informations d'un patient via son id.")
    @GetMapping("/patient/get/{id}")
    public Patient getPatientById(@PathVariable("id") int id) {
	Patient patient = patientService.getPatientById(id);
	
	if (patient.getFirstName() == null) {
	    throw new ErrorGetPatient("An error occurred while searching for the patient");
	}
	return patient;
    }
    
    @ApiOperation(value = "Envoie les informations d'un patient via son id au proxy.")
    @PostMapping("/patient/get/{id}")
    public Patient getPatientByIdProxy(@PathVariable("id") int id) {
	Patient patient = patientService.getPatientById(id);
	
	if (patient.getFirstName() == null) {
	    throw new ErrorGetPatient("An error occurred while searching for the patient");
	}
	return patient;
    }
    @ApiOperation(value = "Ajoute un patient en base de données.")
    @PostMapping("/patient/add")
    public ResponseEntity<?> addPatient(@RequestBody Patient patient) {
	boolean result = patientService.addPatient(patient);
	if (result == false) {
	    throw new ErrorAddPatient("An error occurred while adding to the patient");
	} else {
	    return ResponseEntity.status(HttpStatus.CREATED).build();
	}
    }
    @ApiOperation(value = "Mets à jour les informations d'un patient via son id.")
    @PutMapping("patient/update/{id}")
    public void updatePatient(@PathVariable("id") int id, @RequestBody Patient patientUpdate) {
	boolean result = patientService.updatePatient(id, patientUpdate);
	if (result == false) {
	    throw new ErrorUpdatePatient("An error occurred while updating to the patient");
	}
    }
    @ApiOperation(value = "Supprime un patient via son id.")
    @DeleteMapping("patient/delete/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable("id") int id) {
	boolean result = patientService.deletePatient(id);
	if (result == false) {
	    throw new ErrorDeletePatient("An error occured while deleting the patient");
	} else {
	    return ResponseEntity.status(HttpStatus.OK).build();
	}
    }

}
