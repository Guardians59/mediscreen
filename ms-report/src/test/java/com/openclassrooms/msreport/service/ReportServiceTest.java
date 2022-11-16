package com.openclassrooms.msreport.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.openclassrooms.msreport.model.Report;

@SpringBootTest
public class ReportServiceTest {
    
    @Autowired
    IReportService reportService;
    
    @MockBean
    ICountTriggerService countTriggerServiceMock;
    
    @Test
    public void getReportNoneByIdTest() throws IOException {
	Report result = new Report();
	when(countTriggerServiceMock.numberOfTriggerById(1)).thenReturn(0);
	result = reportService.getReportById(1, "Test", "TestNone", "F", "1966-12-31");
	assertEquals(result.getDiabetesAssessment(), "None");
    }
    
    @Test
    public void getReportBorderlineByIdTest() throws IOException {
	Report result = new Report();
	when(countTriggerServiceMock.numberOfTriggerById(2)).thenReturn(2);
	result = reportService.getReportById(2, "Test", "TestBorderline", "M", "1945-06-24");
	assertEquals(result.getDiabetesAssessment(), "Borderline");
    }
    
    @Test
    public void getReportInDangerByIdTest() throws IOException {
	Report result = new Report();
	when(countTriggerServiceMock.numberOfTriggerById(3)).thenReturn(3);
	result = reportService.getReportById(3, "Test", "TestInDanger", "M", "2004-06-18");
	assertEquals(result.getDiabetesAssessment(), "In Danger");
    }
    
    @Test
    public void getReportEarlyOnSetByIdTest() throws IOException {
	Report result = new Report();
	when(countTriggerServiceMock.numberOfTriggerById(4)).thenReturn(7);
	result = reportService.getReportById(4, "Test", "TestEarlyOnset", "F", "2002-06-28");
	assertEquals(result.getDiabetesAssessment(), "Early onset");
    }
    
    @Test
    public void getReportNoneByNameTest() throws IOException {
	List<Report> result = new ArrayList<>();
	HashMap<Integer, Integer> trigger = new HashMap<>();
	trigger.put(1, 0);
	List<String> firstNameList = new ArrayList<>();
	List<String> genderList = new ArrayList<>();
	List<String> dateList = new ArrayList<>();
	List<Integer> id = new ArrayList<>();
	firstNameList.add("Test");
	genderList.add("F");
	dateList.add("1966-12-31");
	id.add(1);
	
	when(countTriggerServiceMock.numberOfTriggerByName("TestNone")).thenReturn(trigger);
	result = reportService.getReportByName("TestNone", id, firstNameList, genderList, dateList);
	assertEquals(result.get(0).getDiabetesAssessment(), "None");
    }
    
    @Test
    public void getReportBorderlineByNameTest() throws IOException {
	List<Report> result = new ArrayList<>();
	HashMap<Integer, Integer> trigger = new HashMap<>();
	trigger.put(2, 2);
	List<String> firstNameList = new ArrayList<>();
	List<String> genderList = new ArrayList<>();
	List<String> dateList = new ArrayList<>();
	List<Integer> id = new ArrayList<>();
	firstNameList.add("Test");
	genderList.add("M");
	dateList.add("1945-06-24");
	id.add(2);
	when(countTriggerServiceMock.numberOfTriggerByName("TestBorderline")).thenReturn(trigger);
	result = reportService.getReportByName("TestBorderline", id, firstNameList, genderList, dateList);
	assertEquals(result.get(0).getDiabetesAssessment(), "Borderline");
    }
    
    @Test
    public void getReportInDangerByNameTest() throws IOException {
	List<Report> result = new ArrayList<>();
	HashMap<Integer, Integer> trigger = new HashMap<>();
	trigger.put(3, 3);
	List<String> firstNameList = new ArrayList<>();
	List<String> genderList = new ArrayList<>();
	List<String> dateList = new ArrayList<>();
	List<Integer> id = new ArrayList<>();
	firstNameList.add("Test");
	genderList.add("M");
	dateList.add("2004-06-18");
	id.add(3);
	when(countTriggerServiceMock.numberOfTriggerByName("TestInDanger")).thenReturn(trigger);
	result = reportService.getReportByName("TestInDanger", id, firstNameList, genderList, dateList);
	assertEquals(result.get(0).getDiabetesAssessment(), "In Danger");
    }
    
    @Test
    public void getReportEarlyOnSetByNameTest() throws IOException {
	List<Report> result = new ArrayList<>();
	HashMap<Integer, Integer> trigger = new HashMap<>();
	trigger.put(4, 7);
	List<String> firstNameList = new ArrayList<>();
	List<String> genderList = new ArrayList<>();
	List<String> dateList = new ArrayList<>();
	List<Integer> id = new ArrayList<>();
	firstNameList.add("Test");
	genderList.add("F");
	dateList.add("2002-06-28");
	id.add(4);
	when(countTriggerServiceMock.numberOfTriggerByName("TestEarlyOnset")).thenReturn(trigger);
	result = reportService.getReportByName("TestEarlyOnset", id, firstNameList, genderList, dateList);
	assertEquals(result.get(0).getDiabetesAssessment(), "Early onset");
    }

}
