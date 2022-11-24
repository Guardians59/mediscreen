package com.openclassrooms.msnotes.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.msnotes.model.Note;
import com.openclassrooms.msnotes.repository.INoteRepository;
import com.openclassrooms.msnotes.service.INoteService;

/**
 * La classe NoteServiceImpl est l'implémentation de l'interface INoteService.
 * 
 * @see INoteService
 * @author Dylan
 *
 */
@Service
public class NoteServiceImpl implements INoteService {

    @Autowired
    INoteRepository noteRepository;

    private static Logger logger = LogManager.getLogger(NoteServiceImpl.class);

    @Override
    public List<Note> getNoteByPatientId(int patientId) {
	logger.debug("Search doctors notes for patient with id " + patientId);
	List<Note> result = new ArrayList<>();
	//On récupére la liste de note dans la base de données via l'id du patient.
	result = noteRepository.findByPatientId(patientId);
	if (result.isEmpty()) {
	    logger.info("No doctor's note found for this patient");
	} else {
	    logger.info("The doctor's notes for this patient have been successfully found");
	}
	return result;
    }

    @Override
    public boolean addNoteByPatientId(Note newNote, int patientId, String patientName) {
	logger.debug("Add a note for the patient with id " + patientId);
	boolean result = false;
	Note note = new Note();
	/*
	 * On vérifie que les informations soient bien présentes, si tel est le cas
	 * on sauvegarde la nouvelle note en base de données et on renvoit true au boolean,
	 * sinon on renvoit false.
	 */
	if ((newNote.getNote().isBlank() || newNote.getNote() == null) || (patientId <= 0)
		|| (patientName.isBlank() || patientName == null)) {
	    logger.error("An error occurred while adding the note");
	} else {
	    note.setPatientId(patientId);
	    note.setPatient(patientName);
	    note.setNote(newNote.getNote());
	    noteRepository.insert(note);
	    result = true;
	    logger.info("Added the note successfully executed");
	}

	return result;
    }

    @Override
    public boolean updateNote(String id, Note noteUpdated) {
	/*
	 * On récupére la note en base de données via son id, on vérifie que la nouvelle
	 * note n'est pas vide ou null, puis on sauvegarde la nouvelle description de la note
	 * et on renvoit true au boolean.
	 */
	boolean result = false;
	Optional<Note> optionalNote = noteRepository.findById(id);
	if (optionalNote.isPresent()) {
	    Note note = optionalNote.get();
	    if ((noteUpdated.getNote().isBlank() || noteUpdated.getNote() == null)) {
		logger.error("Information is missing for the update");
	    } else {
		note.setNote(noteUpdated.getNote());
		noteRepository.save(note);
		result = true;
		logger.info("Updated note information");
	    }
	} else {
	    logger.error("An error occurred, no note found with this id " + id);
	}

	return result;
    }

    @Override
    public Note getNoteById(String id) {
	//On récupére la note depuis la base de données via son id.
	Optional<Note> optionalNote = noteRepository.findById(id);
	Note note = new Note();
	logger.debug("Search the note with id " + id);
	if (optionalNote.isPresent()) {
	    note = optionalNote.get();
	    logger.info("The note have been successfully found");
	} else {
	    logger.error("An error occurred, no note found with this id " + id);
	}
	return note;
    }

    @Override
    public boolean deleteByPatientId(int patientId) {
	logger.debug("Search doctors notes for patient with id " + patientId);
	boolean result = false;
	List<Note> list = new ArrayList<>();
	//On récupére la liste des notes médicales du patient via son id.
	list = noteRepository.findByPatientId(patientId);
	if(list.isEmpty()) {
	    logger.info("No doctor's note found for this patient");
	    result = true;
	} else {
	    /*
	     * On supprime la liste des notes médicales du patient, puis on vérifie que tout
	     * s'est bien exécuté en recherchant la première note avec son id récupérer auparavant.
	     */
	    String id = list.get(0).getId();
	    noteRepository.deleteAll(list);
	    boolean noteDelete = noteRepository.existsById(id);
	    if(noteDelete == false) {
		logger.info("The notes corresponding to the patient with the id " + patientId
			    + " have been successfully deleted");
		result = true;
	    } else {
		logger.error("An error occurred while deleting notes");
	    }
	}
	return result;
    }

}
