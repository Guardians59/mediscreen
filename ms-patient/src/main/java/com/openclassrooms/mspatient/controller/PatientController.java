package com.openclassrooms.mspatient.controller;

import java.util.ArrayList;
import java.util.List;

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

/**
 * La classe PatientController est le controller qui permet de gérer les URL du
 * micro-service de gestion des patients.
 * 
 * @author Dylan
 *
 */
@RestController
public class PatientController {

    @Autowired
    IPatientService patientService;

    /**
     * La méthode getPatient permet de récupérer les informations d'un patient
     * via son nom, prénom et date de naissance.
     * @param firstName le prénom du patient.
     * @param lastName le nom du patient.
     * @param birthday la date de naissance du patient.
     * @return patient, les informations du patient.
     */
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
    
    /**
     * La méthode getPatientById permet de récupérer les informations d'un patient grâce à
     * son id.
     * @param id l'id du patient.
     * @return patient, les informations du patient.
     */
    @ApiOperation(value = "Recupere les informations d'un patient via son id.")
    @GetMapping("/patient/get/{id}")
    public Patient getPatientById(@PathVariable("id") int id) {
	Patient patient = patientService.getPatientById(id);
	
	if (patient.getFirstName() == null) {
	    throw new ErrorGetPatient("An error occurred while searching for the patient");
	}
	return patient;
    }
    
    /**
     * La méthode addPatient permet d'ajouter un patient en base de données.
     * @param patient le patient à ajouter.
     * @return response entity, status Created si le patient à été ajouté avec succès,
     * ou NotFound si une erreur est survenue.
     */
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
    
    /**
     * La méthode updatePatient permet de mettre à jour les informations d'un patient
     * via son id.
     * @param id l'id du patient.
     * @param patientUpdate les informations du patient modifiées.
     */
    @ApiOperation(value = "Mets à jour les informations d'un patient via son id.")
    @PutMapping("/patient/update/{id}")
    public void updatePatient(@PathVariable("id") int id, @RequestBody Patient patientUpdate) {
	boolean result = patientService.updatePatient(id, patientUpdate);
	if (result == false) {
	    throw new ErrorUpdatePatient("An error occurred while updating to the patient");
	}
    }
    
    /**
     * La méthode deletePatient permet de supprimer un patient de la base de données.
     * @param id l'id du patient.
     * @return response entity, status Ok si la suppression est confirmée, NotFound si
     * une erreur est survenue.
     */
    @ApiOperation(value = "Supprime un patient via son id.")
    @DeleteMapping("/patient/delete/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable("id") int id) {
	boolean result = patientService.deletePatient(id);
	if (result == false) {
	    throw new ErrorDeletePatient("An error occured while deleting the patient");
	} else {
	    return ResponseEntity.status(HttpStatus.OK).build();
	}
    }
    
    /**
     * La méthode getAllByName permet de récupérer la liste des patients portant le même
     * nom.
     * @param lastName le nom de famille.
     * @return List la liste des patients avec le nom indiqué.
     */
    @ApiOperation(value = "Recupere la liste de tous les patients correspondant au nom de famille.")
    @GetMapping("/patient/getAllByName/{lastName}")
    public List<Patient> getAllByName(@PathVariable("lastName") String lastName) {
	List<Patient> result = new ArrayList<>();
	result = patientService.getAllByName(lastName);
	if(result.isEmpty()) {
	    throw new ErrorGetPatient("An error occurred while searching for the patient");
	} else {
	    return result;
	}
    }

}
