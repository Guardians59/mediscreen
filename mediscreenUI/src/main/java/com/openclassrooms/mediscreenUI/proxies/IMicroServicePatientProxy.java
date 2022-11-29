package com.openclassrooms.mediscreenUI.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.openclassrooms.mediscreenUI.beans.PatientBean;

/**
 * L'interface IMicroServicePatientProxy est le service qui nous permet d'utiliser le
 * microService Patient depuis feign.
 * 
 * @author Dylan
 *
 */
@FeignClient(name = "microservice-patient", url = "localhost:8081")
public interface IMicroServicePatientProxy {

    /**
     * La méthode getPatient permet de récupérer un patient via son prénom, son nom, sa
     * date de naissance.
     * 
     * @param firstName le prénom du patient.
     * @param lastName le nom du patient.
     * @param birthday la date de naissance du patient.
     * @return PatientBean le patient récupéré.
     */
    @GetMapping(value = "/patient/get")
    PatientBean getPatient(@RequestParam String firstName, @RequestParam String lastName,
	    @RequestParam String birthday);

    /**
     * La méthode getPatientById permet de récupérer un patient via son id.
     * 
     * @param id l'id du patient.
     * @return PatientBean le patient récupéré.
     */
    @GetMapping(value = "/patient/get/{id}")
    PatientBean getPatientById(@PathVariable("id") int id);

    /**
     * La méthode updatePatient permet de mettre à jour un patient via son id.
     * 
     * @param id l'id du patient.
     * @param patientUpdated les informations mises à jour du patient.
     */
    @PutMapping(value = "/patient/update/{id}")
    void updatePatient(@PathVariable("id") int id, @RequestBody PatientBean patientUpdated);

    /**
     * La méthode addPatient permet d'ajouter un patient en base de données.
     * 
     * @param newPatient les informations du patient à ajouter.
     * @return ResponseEntity status 201 si l'ajout est confirmé, status 404 si une
     * erreur est survenue.
     */
    @PostMapping(value = "/patient/add")
    ResponseEntity<?> addPatient(@RequestBody PatientBean newPatient);

    /**
     * La méthode deletePatient permet de supprimer un patient via son id.
     * 
     * @param id l'id du patient.
     * @return ResponseEntity status 200 si la suppression est confirmée, status 404 si une
     * erreur est survenue.
     */
    @DeleteMapping(value = "/patient/delete/{id}")
    ResponseEntity<?> deletePatient(@PathVariable("id") int id);

    /**
     * La méthode getAllByName permet de récupérer la liste des patients portant le même
     * nom.
     * 
     * @param lastName le nom de famille.
     * @return List des patients ayant le nom correspondant.
     */
    @GetMapping(value = "/patient/getAllByName/{lastName}")
    List<PatientBean> getAllByName(@PathVariable("lastName") String lastName);
}
