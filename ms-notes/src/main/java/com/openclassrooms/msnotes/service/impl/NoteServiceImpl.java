package com.openclassrooms.msnotes.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.msnotes.model.Note;
import com.openclassrooms.msnotes.repository.INoteRepository;
import com.openclassrooms.msnotes.service.INoteService;

@Service
public class NoteServiceImpl implements INoteService {

    @Autowired
    INoteRepository noteRepository;

    private static Logger logger = LogManager.getLogger(NoteServiceImpl.class);

    @Override
    public List<Note> getNoteByPatientId(int patientId) {
	logger.debug("Search doctors notes for patient with id " + patientId);
	List<Note> result = new ArrayList<>();
	result = noteRepository.findByPatientId(patientId);
	if (result.isEmpty()) {
	    logger.info("No doctor's note found for this patient");
	} else {
	    logger.info("The doctor's notes for this patient have been successfully found");
	}
	return result;
    }

    @Override
    public boolean addNoteByPatientId(Note newNote, int patientId) {
	logger.debug("Add a note for the patient with id " + patientId);
	boolean result = false;
	Note note = new Note();
	if ((newNote.getPatient().isBlank() || newNote.getPatient() == null) || (newNote.getNote().isBlank()
		|| newNote.getNote() == null)) {
	    logger.error("An error occurred while adding the note");
	} else {
	    note.setPatientId(patientId);
	    note.setPatient(newNote.getPatient());
	    note.setNote(newNote.getNote());
	    noteRepository.insert(note);
	    result = true;
	    logger.info("Added the note successfully executed");
	}

	return result;
    }

}
