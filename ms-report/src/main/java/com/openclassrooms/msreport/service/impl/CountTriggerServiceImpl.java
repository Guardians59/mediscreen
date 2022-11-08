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

    @Override
    public int numberOfKeyWords(int patientId) throws IOException {
	HashMap<String, Integer> hasmap = new HashMap<>();
	ArrayList<String> keyList = new ArrayList<>();
	List<Note> listResult = new ArrayList<>();
	BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/triggerTerm.txt"));

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
	    list = reportRepository.findKeyWords(patientId, key);
	    listResult.addAll(list);
	    hasmap.put(key, list.size());

	});
	int result = listResult.size();
	return result;
    }

}
