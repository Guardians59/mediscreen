package com.openclassrooms.mediscreenUI.services;

import java.util.List;

import com.openclassrooms.mediscreenUI.beans.NoteBean;

/**
 * L'interface INoteService est le service de gestion des notes médicales de notre application.
 * 
 * @author Dylan
 *
 */
public interface INoteService {
    
    /**
     * La méthode getNoteByPatientId permet de récupérer la liste des notes médicales
     * d'un patient via son id.
     * 
     * @param patientId l'id du patient.
     * @return List des notes médicales du patient.
     */
    public List<NoteBean> getNoteByPatientId(int patientId);
    
    
    /**
     * La méthode addNote permet d'ajouter une note médicale sur un patient.
     * 
     * @param note la note à ajouter.
     * @param patientId l'id du patient.
     * @param patientName le nom de famille du patient.
     * @return boolean true si l'ajout est validé, false si une erreur est rencontrée.
     */
    public boolean addNote(NoteBean note, int patientId, String patientName);
    
    /**
     * La méthode updateNoteById permet de mettre à jour une note via son id.
     * 
     * @param id l'id de la note.
     * @param noteUpdated les informations de la note mise à jour.
     * @return int le chiffre zéro si la note n'a pas été modifiée, le chiffre un si la
     *modification est validée, le chiffre moins un si une erreur est rencontrée.
     */
    public int updateNoteById(String id, NoteBean noteUpdated);
    
    /**
     * La méthode getNoteById permet de récupérer une note via son id.
     * 
     * @param id l'id de la note.
     * @return NoteBean la note récupérée.
     */
    public NoteBean getNoteById(String id);
    
    /**
     * La méthode deleteNoteByPatientId permet de supprimer les notes médicales d'un patient
     * via son id.
     * 
     * @param patientId l'id du patient.
     * @return boolean true si la suppression est validée, false si une erreur est rencontrée.
     */
    public boolean deleteNoteByPatientId(int patientId);

}
