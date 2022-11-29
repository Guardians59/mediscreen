package com.openclassrooms.mediscreenUI.services;

import com.openclassrooms.mediscreenUI.beans.NoteBean;
import com.openclassrooms.mediscreenUI.beans.PatientBean;

/**
 * L'interface IFormValidService est le service de validation des champs d'informations.
 * 
 * @author Dylan
 *
 */
public interface IFormValidService {
    
    /**
     * La méthode updatePatientFormValid permet de vérifier que tous les champs
     * d'informations obligatoires pour la mise à jour d'un patient soient remplis.
     * 
     * @param patientUpdated les informations mis à jour du patient.
     * @return boolean true si les champs sont valides, false si il manque des
     * informations nécéssaires.
     */
    public boolean updatePatientFormValid(PatientBean patientUpdated);
    
    
    /**
     * La méthode addPatientFormValid permet de vérifier que tous les champs
     * d'informations pour l'ajout d'un patient soient remplis.
     * 
     * @param newPatient les informations du nouveau patient.
     * @return boolean true si les champs sont valides, false si il manque des
     * informations nécéssaires.
     */
    public boolean addPatientFormValid(PatientBean newPatient);
    
    
    /**
     * La méthode updateNoteFormValid permet de vérifier que le champs
     * d'informations pour la mise à jour d'une note soit remplit.
     * 
     * @param noteUpdated les informations mis à jour de la note.
     * @return boolean true si le champs est valide, false si il est manquant.
     */
    public boolean updateNoteFormValid(NoteBean noteUpdated);
    
    /**
     * La méthode addNoteFormValid permet de vérifier que le champs 
     * d'informations pour l'ajout d'une note soit remplit.
     * 
     * @param newNote les informations de la nouvelle note.
     * @return boolean true si le champs est valide, false si il est manquant.
     */
    public boolean addNoteFormValid(NoteBean newNote);

}
