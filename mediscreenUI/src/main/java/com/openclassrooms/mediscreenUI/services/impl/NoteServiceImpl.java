package com.openclassrooms.mediscreenUI.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.openclassrooms.mediscreenUI.beans.NoteBean;
import com.openclassrooms.mediscreenUI.proxies.IMicroServiceNoteProxy;
import com.openclassrooms.mediscreenUI.services.INoteService;
@Service
public class NoteServiceImpl implements INoteService {
    
    private Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);
    private final IMicroServiceNoteProxy noteProxy;
    
    public NoteServiceImpl(IMicroServiceNoteProxy noteProxy) {
	this.noteProxy = noteProxy;
    }

    @Override
    public List<NoteBean> getNoteByPatientId(int patientId) {
	logger.debug("Search doctors notes for the patient with id " + patientId);
	List<NoteBean> result = new ArrayList<>();
	result = noteProxy.getNoteByPatientId(patientId);
	return result;
    }

    @Override
    public boolean addNote(NoteBean note, int patientId) {
	logger.debug("Add the new note for the patient with id " + patientId);
	boolean result = false;
	NoteBean newNote = new NoteBean();
	newNote = note;
	ResponseEntity<?> resultAdd = noteProxy.addNote(patientId, newNote);
	if(resultAdd.getStatusCode().value() == 201) {
	    result = true;
	    logger.info("Note added with successfully");
	} else {
	    logger.error("An error occurred while adding to the note");
	}
	return result;
    }

}
