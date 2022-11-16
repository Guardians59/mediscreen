package com.openclassrooms.msreport.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.msreport.model.Report;
import com.openclassrooms.msreport.service.IReportService;

import io.swagger.annotations.ApiOperation;

@RestController
public class ReportController {

    @Autowired
    IReportService reportService;

    @ApiOperation(value = "Recupere le rapport de diabete via l'id du patient.")
    @GetMapping("/assess/id/{id}")
    public Report getReportById(@PathVariable("id") int patientId, @RequestParam String firstName,
	    @RequestParam String lastName, @RequestParam String gender, @RequestParam String date) throws IOException {
	Report result = new Report();
	result = reportService.getReportById(patientId, firstName, lastName, gender, date);
	return result;
    }

    @ApiOperation(value = "Envoie le rapport de diabete via l'id du patient au proxy.")
    @PostMapping("/assess/id/{id}")
    public Report getReportByIdProxy(@PathVariable("id") int patientId, @RequestBody HashMap<String, Object> mapParams)
	    throws IOException {
	Report result = new Report();
	String firstName = mapParams.get("firstName").toString();
	String lastName = mapParams.get("lastName").toString();
	String gender = mapParams.get("gender").toString();
	String date = mapParams.get("date").toString();
	result = reportService.getReportById(patientId, firstName, lastName, gender, date);
	return result;
    }

    @ApiOperation(value = "Recupere les rapports de diabete familiaux via le nom de famille.")
    @GetMapping("/assess/familyName/{lastName}")
    public List<Report> getReportByName(@PathVariable("lastName") String lastName, @RequestParam List<Integer> patientId, @RequestParam List<String> firstNameList,
	    @RequestParam List<String> genderList, @RequestParam List<String> dateList)
	    throws IOException {
	List<Report> result = new ArrayList<>();
	result = reportService.getReportByName(lastName, patientId, firstNameList, genderList, dateList);
	return result;
	
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "Envoie les rapports de diabete familiaux via le nom de famille au proxy.")
    @PostMapping("/assess/familyName/{lastName}")
    public List<Report> getReportByNameProxy(@PathVariable("lastName") String lastName,
	    @RequestBody HashMap<String, List<?>> listParam) throws IOException {
	List<Report> result = new ArrayList<>();
	List<Integer> patientId = (List<Integer>) listParam.get("patientId");
	List<String> firstNameList = (List<String>) listParam.get("firstName");
	List<String> genderList = (List<String>) listParam.get("gender");
	List<String> dateList = (List<String>) listParam.get("date");
	result = reportService.getReportByName(lastName, patientId, firstNameList, genderList, dateList);
	return result;
    }

}
