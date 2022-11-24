package com.openclassrooms.msnotes.controller;

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

import com.openclassrooms.msnotes.controller.exception.ErrorAddNote;
import com.openclassrooms.msnotes.controller.exception.ErrorDeleteNote;
import com.openclassrooms.msnotes.controller.exception.ErrorGetNote;
import com.openclassrooms.msnotes.controller.exception.ErrorUpdateNote;
import com.openclassrooms.msnotes.model.Note;
import com.openclassrooms.msnotes.service.INoteService;

import io.swagger.annotations.ApiOperation;

/**
 * La classe NoteController est le controller qui permet de gérer les URL du
 * micro-service de gestion de note.
 * 
 * @author Dylan
 *
 */
@RestController
public class NoteController {
    
    @Autowired
    INoteService noteService;
    
    /**
     * La méthode getNoteByPatientId permet de récupérer la liste des notes médicales
     * d'un patient via son id.
     * @param id l'id du patient.
     * @return List les notes médicales du patient.
     */
    @ApiOperation(value = "Recupere les notes medicales sur un patient via son id.")
    @GetMapping("/note/getByPatientId/{id}")
    public List<Note> getNoteByPatientId(@PathVariable("id") int id) {
	List<Note> result = noteService.getNoteByPatientId(id);
	return result;
    }
    
    /**
     * La méthode addNote permet d'ajouter une note médicale sur un patient.
     * @param id l'id du patient.
     * @param patientName le nom du patient.
     * @param newNote la note médicale à ajouter.
     * @return response entity, status Created si la note à été ajoutée avec succès,
     * ou NotFound si une erreur est survenue.
     */
    @ApiOperation(value = "Ajoute une note de medicale sur un patient.")
    @PostMapping("/note/add/{id}")
    public ResponseEntity<?> addNote(@PathVariable("id") int id, @RequestParam String patientName, @RequestBody Note newNote) {
	boolean result = noteService.addNoteByPatientId(newNote, id, patientName);
	if(result == false) {
	    throw new ErrorAddNote("An error occurred while adding to the note");
	} else {
	    return ResponseEntity.status(HttpStatus.CREATED).build();
	}
    }
    
    /**
     * La méthode updateNoteById permet de mettre à jour une note via l'id de celle-ci.
     * @param id l'id de la note.
     * @param noteUpdated les informations de la note modifiées.
     * @return response entity, status Ok si la mise à jour est validée, ou NotFound si
     * une erreur est rencontrée.
     */
    @ApiOperation(value = "Mets à jour une note medicale sur un patient via l'id de la note.")
    @PutMapping("/note/update/{id}")
    public ResponseEntity<?> updateNoteById(@PathVariable("id") String id, @RequestBody Note noteUpdated) {
	boolean result = noteService.updateNote(id, noteUpdated);
	if(result == false) {
	    throw new ErrorUpdateNote("An error occurred while adding to the note");
	} else {
	    return ResponseEntity.status(HttpStatus.OK).build();
	}
    }
    
    /**
     * La méthode getNoteById permet de récupérer une note via son id.
     * @param id l'id de la note.
     * @return Note, la note récupérée.
     */
    @ApiOperation(value = "Recupere une note medicale via l'id.")
    @GetMapping("/note/get/{id}")
    public Note getNoteById(@PathVariable("id") String id) {
	Note note = noteService.getNoteById(id);
	if(note.getPatient() == null) {
	    throw new ErrorGetNote("An error occured while searching the note");
	} else {
	    return note;
	}
    }
    
    /**
     * La méthode deleteNoteByPatientId permet de supprimer les notes médicales d'un patient
     * via son id.
     * @param id l'id du patient.
     * @return response entity, status Ok si la suppression est confirmée, NotFound si
     * une erreur est survenue.
     */
    @ApiOperation(value = "Supprime les notes medicales d'un patient via son id.")
    @DeleteMapping("/note/patientId/{id}")
    public ResponseEntity<?> deleteNoteByPatientId(@PathVariable("id") int id) {
	boolean result = noteService.deleteByPatientId(id);
	if(result == false) {
	    throw new ErrorDeleteNote("An error occured while searching the note");
	} else {
	    return ResponseEntity.status(HttpStatus.OK).build();
	}
     }

    }

