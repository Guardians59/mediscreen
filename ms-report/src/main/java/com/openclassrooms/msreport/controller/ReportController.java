package com.openclassrooms.msreport.controller;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.msreport.model.Report;
import com.openclassrooms.msreport.service.ICountTriggerService;
import com.openclassrooms.msreport.service.IReportService;

@RestController
public class ReportController {

    @Autowired
    ICountTriggerService countService;
    
    @Autowired
    IReportService reportService;

    @GetMapping("/count/{id}")
    public HashMap<String, Integer> numberOfKeyWords(@PathVariable("id") int patientId) throws IOException {
	HashMap<String, Integer> result = new HashMap<>();
	int numberTrigger = countService.numberOfKeyWords(patientId);
	result.put("Number of trigger", numberTrigger);
	return result;
    }

    @GetMapping("/assess/{id}")
    public Report getReport(@PathVariable("id") int patientId, @RequestParam String firstName,
	    @RequestParam String lastName, @RequestParam String gender, @RequestParam String date) throws IOException {
	Report result = new Report();
	result = reportService.getReport(patientId, firstName, lastName, gender, date);
	return result;
    }

}
