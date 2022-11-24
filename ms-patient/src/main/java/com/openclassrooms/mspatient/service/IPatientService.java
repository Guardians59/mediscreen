package com.openclassrooms.mspatient.service;

import java.util.List;

import com.openclassrooms.mspatient.model.Patient;

/**
 * L'interface IPatientService est le service de gestion des patients.
 * 
 * @author Dylan
 *
 */
public interface IPatientService {
    
    /**
     * La méthode addPatient permet d'ajouter un patient en base de données.
     * @param patient le patient à ajouter.
     * @return boolean true si l'ajout est confirmé, false si une erreur est rencontrée.
     */
    public boolean addPatient(Patient patient);
    
    /**
     * La méthode getPatient permet de récupérer les informations d'un patient
     * via son nom, prénom et date de naissance.
     * @param firstName le prénom du patient.
     * @param lastName le nom du patient.
     * @param birthday la date de naissance du patient.
     * @return patient, les informations du patient.
     */
    public Patient getPatient(String firstName, String lastName, String birthday);
    
    /**
     * La méthode getPatientById permet de récupérer les informations d'un patient grâce à
     * son id.
     * @param id l'id du patient.
     * @return patient, les informations du patient.
     */
    public Patient getPatientById(int id);
    
    /**
     * La méthode updatePatient permet de mettre à jour les informations d'un patient
     * via son id.
     * @param id l'id du patient.
     * @param patientUpdated les informations du patient modifiées.
     * @return boolean true si la mise à jour est confirmée, false
     * si une erreur est rencontrée.
     */
    public boolean updatePatient(int id, Patient patientUpdated);
    
    /**
     * La méthode deletePatient permet de supprimer un patient de la base de données.
     * @param id l'id du patient.
     * @return boolean true si la suppression est confirmée, false
     * si une erreur est rencontrée.
     */
    public boolean deletePatient(int id);

    /**
     * La méthode getAllByName permet de récupérer la liste des patients portant le même
     * nom.
     * @param lastName le nom de famille.
     * @return List la liste des patients avec le nom indiqué.
     */
    public List<Patient> getAllByName(String lastName);
}
