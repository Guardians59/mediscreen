package com.openclassrooms.msnotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.msnotes.controller.exception.ErrorAddNote;
import com.openclassrooms.msnotes.controller.exception.ErrorGetNote;
import com.openclassrooms.msnotes.controller.exception.ErrorUpdateNote;
import com.openclassrooms.msnotes.model.Note;
import com.openclassrooms.msnotes.service.INoteService;

import io.swagger.annotations.ApiOperation;

@RestController
public class NoteController {
    
    @Autowired
    INoteService noteService;
    
    @ApiOperation(value = "Recupere les notes des medecins sur un patient via son id.")
    @GetMapping("/note/getByPatientId/{id}")
    public List<Note> getNoteByPatientId(@PathVariable("id") int id) {
	List<Note> result = noteService.getNoteByPatientId(id);
	return result;
    }
    @ApiOperation(value = "Envoie au proxy les notes des medecins sur un patient via son id.")
    @PostMapping("/note/getByPatientId/{id}")
    public List<Note> getNoteByPatientIdProxy(@PathVariable("id") int id) {
	List<Note> result = noteService.getNoteByPatientId(id);
	return result;
    }
    @ApiOperation(value = "Ajoute une note de medecin sur un patient.")
    @PostMapping("/note/add/{id}")
    public ResponseEntity<?> addNote(@PathVariable("id") int id, @RequestParam String patientName, @RequestBody Note newNote) {
	boolean result = noteService.addNoteByPatientId(newNote, id, patientName);
	if(result == false) {
	    throw new ErrorAddNote("An error occurred while adding to the note");
	} else {
	    return ResponseEntity.status(HttpStatus.CREATED).build();
	}
    }
    @ApiOperation(value = "Mets à jour une note de medecin sur un patient via l'id de la note.")
    @PutMapping("/note/update/{id}")
    public ResponseEntity<?> updateNoteById(@PathVariable("id") String id, @RequestBody Note noteUpdated) {
	boolean result = noteService.updateNote(id, noteUpdated);
	if(result == false) {
	    throw new ErrorUpdateNote("An error occurred while adding to the note");
	} else {
	    return ResponseEntity.status(HttpStatus.OK).build();
	}
    }
    
    @GetMapping("/note/get/{id}")
    public Note getNoteById(@PathVariable("id") String id) {
	Note note = noteService.getNoteById(id);
	if(note.getPatient() == null) {
	    throw new ErrorGetNote("An error occured while searching the note");
	} else {
	    return note;
	}
    }

    }

