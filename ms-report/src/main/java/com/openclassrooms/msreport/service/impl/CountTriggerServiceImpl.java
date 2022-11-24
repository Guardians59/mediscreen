package com.openclassrooms.msreport.service.impl;

import java.io.BufferedReader;
import java.io.File;
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

/**
 * La classe CountTriggerServiceImpl est l'implémentation de l'interface ICountTriggerService.
 * 
 * @see ICountTriggerService
 * @author Dylan
 *
 */
@Service
public class CountTriggerServiceImpl implements ICountTriggerService {

    @Autowired
    IReportRepository reportRepository;
    
    private static Logger logger = LogManager.getLogger(CountTriggerServiceImpl.class);
    private int countResult;

    @Override
    public int numberOfTriggerById(int patientId) throws IOException {
	ArrayList<String> keyList = new ArrayList<>();
	//On récupére le fichier contenant les mots clés des occurences.
	String path = new File("src/main/resources/triggerTerm.txt").getAbsolutePath();
	BufferedReader reader = new BufferedReader(new FileReader(path));
	logger.debug("Reading the file containing the trigger term");
	countResult = 0;
	
	/*
	 * On initie un try catch afin de lire le fichier pour ajouter chaque occurence
	 * dans une liste de String.
	 * Si une erreur est rencontrée on capture l'IOException.
	 */
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
	/*
	 * On initie une boucle forEach afin de chercher dans les notes médicales si le mot
	 * clés correspondant à une occurence est présent dans une de celles-ci.
	 * Si on retrouve une ou plusieurs note avec cette occurence alors on ajoute un,
	 * à la valeur de l'integer countResult, qui sera retourné au final.
	 */
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
	//On récupére le fichier contenant les mots clés des occurences.
	String path = new File("src/main/resources/triggerTerm.txt").getAbsolutePath();
	BufferedReader reader = new BufferedReader(new FileReader(path));
	logger.debug("Reading the file containing the trigger term");
	countResult = 0;
	
	/**
	 * On initie un try catch afin de lire le fichier pour ajouter chaque occurence
	 * dans une liste de String.
	 * Si une erreur est rencontrée on capture l'IOException.
	 */
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
	/*
	 * On récupére dans une liste les id des patients ayant le même nom de famille que 
	 * celui rechercher.
	 */
	listNote.forEach(note -> {
	    int patientId = note.getPatientId();
	    if(!listId.contains(patientId)) {
		listId.add(patientId);
	    }
	});
	
	/*
	 * On initie une boucle forEach pour chaque id des patients retrouvés précedemment
	 * puis on initie une autre boucle forEach pour chaque mot clés d'occurence, afin
	 * de rechercher dans les notes de chaque patient le nombre d'occurence.
	 * On retourne ensuite dans la HashMap l'id du patient avec le nombre d'occurence
	 * retrouvées.
	 */
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
