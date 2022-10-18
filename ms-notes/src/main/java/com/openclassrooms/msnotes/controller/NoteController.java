package com.openclassrooms.msnotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
