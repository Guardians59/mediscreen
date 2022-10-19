package com.openclassrooms.mediscreenUI.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	logger.debug("Search doctors notes for patient with id " + patientId);
	List<NoteBean> result = new ArrayList<>();
	result = noteProxy.getNoteByPatientId(patientId);
	return result;
    }

}
