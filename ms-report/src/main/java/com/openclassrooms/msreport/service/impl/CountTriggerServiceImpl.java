package com.openclassrooms.msreport.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.msreport.model.Note;
import com.openclassrooms.msreport.repository.IReportRepository;
import com.openclassrooms.msreport.service.ICountTriggerService;

@Service
public class CountTriggerServiceImpl implements ICountTriggerService {

    @Autowired
    IReportRepository reportRepository;
    
    private static Logger logger = LogManager.getLogger(CountTriggerServiceImpl.class);
    private int countResult;

    @Override
    public int numberOfTriggerById(int patientId) throws IOException {
	ArrayList<String> keyList = new ArrayList<>();
	BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/triggerTerm.txt"));
	logger.debug("Reading the file containing the trigger term");
	countResult = 0;
	try {
	    String line = reader.readLine();
	    while (line != null) {
		keyList.add(line);
		line = reader.readLine();
	    }
	} catch (IOException e) {
	    logger.error("Error when reading the file source", e);
	} finally {
	    reader.close();
	}
	
	keyList.forEach(key -> {
	    List<Note> list = new ArrayList<>();
	    list = reportRepository.findNumberNoteWithTriggerById(patientId, key);
	    if(!list.isEmpty()) {
		countResult++;
	    }
	});
	logger.info("The number of trigger has been successfully found");
	return countResult;
    }

    @Override
    public HashMap<Integer, Integer> numberOfTriggerByName(String lastName) throws IOException {
	ArrayList<String> keyList = new ArrayList<>();
	HashMap<Integer, Integer> mapPatient = new HashMap<>();
	List<Note> listNote = new ArrayList<>();
	ArrayList<Integer> listId = new ArrayList<>();
	listNote = reportRepository.findAllByName(lastName);
	BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/triggerTerm.txt"));
	logger.debug("Reading the file containing the trigger term");
	countResult = 0;
	try {
	    String line = reader.readLine();
	    while (line != null) {
		keyList.add(line);
		line = reader.readLine();
	    }
	} catch (IOException e) {
	    logger.error("Error when reading the file source", e);
	} finally {
	    reader.close();
	}
	
	listNote.forEach(note -> {
	    int patientId = note.getPatientId();
	    if(!listId.contains(patientId)) {
		listId.add(patientId);
	    }
	});
	
	
	listId.forEach(id -> {
	    countResult = 0;
	    keyList.forEach(key -> {
		    List<Note> list = new ArrayList<>();
		    list = reportRepository.findNumberNoteWithTriggerById(id, key);
		    if(!list.isEmpty()) {
			countResult++;
		    }

		});
	    mapPatient.put(id, countResult);
	});
	logger.info("The number of trigger has been successfully found");
	return mapPatient;
    }

}
