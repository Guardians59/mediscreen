package com.openclassrooms.msnotes.service;

import java.util.List;

import com.openclassrooms.msnotes.model.Note;

/**
 * L'interface INoteService est le service de gestion des notes médicales.
 * 
 * @author Dylan
 *
 */
public interface INoteService {
    
    /**
     * La méthode getNoteByPatientId permet de récupérer la liste des notes médicales
     * d'un patient via son id.
     * @param patientId l'id du patient.
     * @return List les notes médicales du patient.
     */
    public List<Note> getNoteByPatientId(int patientId);
    
    /**
     * La méthode addNoteByPatientId permet d'ajouter une note médicale sur un patient.
     * @param newNote la note médicale à ajouter.
     * @param patientId l'id du patient.
     * @param patientName le nom du patient.
     * @return boolean true si l'ajout est confirmé, false si une erreur est rencontrée.
     */
    public boolean addNoteByPatientId(Note newNote, int patientId, String patientName);
    
    /**
     * La méthode updateNote permet de mettre à jour une note via l'id de celle-ci.
     * @param id l'id de la note.
     * @param noteUpdated les informations de la note à modifier.
     * @return boolean true si la mise à jour est confirmée, false
     * si une erreur est rencontrée.
     */
    public boolean updateNote(String id, Note noteUpdated);
    
    /**
     * La méthode getNoteById permet de récupérer une note via son id.
     * @param id l'id de la note.
     * @return Note, la note récupérée.
     */
    public Note getNoteById(String id);
    
    /**
     * La méthode deleteByPatientId permet de supprimer les notes médicales
     * d'un patient via son id.
     * @param patientId l'id du patient.
     * @return boolean true si la suppression est confirmée, false
     * si une erreur est rencontrée.
     */
    public boolean deleteByPatientId(int patientId);

}
