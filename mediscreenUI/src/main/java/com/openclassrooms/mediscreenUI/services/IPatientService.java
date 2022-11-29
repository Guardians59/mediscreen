package com.openclassrooms.mediscreenUI.services;

import java.util.List;

import com.openclassrooms.mediscreenUI.beans.PatientBean;

/**
 * L'interface IPatientService est le service de gestion des patients de notre application.
 * 
 * @author Dylan
 *
 */
public interface IPatientService {
    
    /**
     * La méthode getPatient permet de récupérer les informations d'un patient via son
     * prénom, son nom, sa date de naissance.
     * 
     * @param firstName le prénom du patient.
     * @param lastName le nom du patient.
     * @param birthday la date de naissance du patient.
     * @return PatientBean le patient récupéré.
     */
    public PatientBean getPatient(String firstName, String lastName, String birthday);
    
    /**
     * La méthode getPatientById permet de récupérer les informations d'un patient via
     * son id.
     * 
     * @param id l'id du patient.
     * @return PatientBean le patient récupéré.
     */
    public PatientBean getPatientById(int id);
    
    /**
     * La méthode updatePatient permet de mettre à jour les informations d'un patient
     * via son id.
     * 
     * @param id l'id du patient.
     * @param patientUpdated les informations mises à jour du patient.
     * @return int le chiffre zéro si aucune modifications n'est apportée, le chiffre un
     * si la mise à jour est validée, le chiffre moins un si une erreur est survenue.
     */
    public int updatePatient(int id, PatientBean patientUpdated);
    
    /**
     * La méthode addPatient permet d'ajouter un patient.
     * 
     * @param newPatient les informations du patient à ajouter.
     * @return boolean true si l'ajout est validé, false si une erreur est survenue.
     */
    public boolean addPatient(PatientBean newPatient);
    
    /**
     * La méthode deletePatient permet de supprimer un patient via son id.
     * 
     * @param id l'id du patient.
     * @return boolean true si la suppression est validé, false si une erreur est survenue.
     */
    public boolean deletePatient(int id);
    
    /**
     * La méthode getAllByName permet de récupérer la liste des patients ayant le nom de
     * famille recherché.
     * 
     * @param lastName le nom de famille.
     * @return List PatientBean les patients récupérés.
     */
    public List<PatientBean> getAllByName(String lastName);

}
