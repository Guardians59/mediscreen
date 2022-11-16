package com.openclassrooms.msreport.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.openclassrooms.msreport.model.Note;
import com.openclassrooms.msreport.repository.IReportRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTest {
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    WebApplicationContext webApplicationContext;
    
    /*@MockBean
    IReportService reportService;
    
    @MockBean
    ICountTriggerService countTriggerService;*/
    
    @MockBean
    IReportRepository reportRepository;
    
    @BeforeEach
    public void setUp() {
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void getReportByNameTest() throws Exception {
	Note note = new Note();
	note.setPatientId(20);
	note.setPatient("TestBorderline");
	note.setNote("Reaction Dizziness");
	Note note2 = new Note();
	note2.setPatientId(21);
	note2.setPatient("TestBorderline");
	note2.setNote("Reaction Dizziness abnormal Antibodies");
	List<Note> listNote = new ArrayList<>();
	listNote.add(note);
	listNote.add(note2);
	List<Note> listNoteP1 = new ArrayList<>();
	listNoteP1.add(note);
	List<Note> listNoteP2 = new ArrayList<>();
	listNoteP2.add(note2);
	
	when(reportRepository.findAllByName("TestBorderline")).thenReturn(listNote);
	when(reportRepository.findNumberNoteWithTriggerById(20, "Reaction")).thenReturn(listNoteP1);
	when(reportRepository.findNumberNoteWithTriggerById(20, "Dizziness")).thenReturn(listNoteP1);
	when(reportRepository.findNumberNoteWithTriggerById(21, "Abnormal")).thenReturn(listNoteP2);
	when(reportRepository.findNumberNoteWithTriggerById(21, "Reaction")).thenReturn(listNoteP2);
	when(reportRepository.findNumberNoteWithTriggerById(21, "Dizziness")).thenReturn(listNoteP2);
	when(reportRepository.findNumberNoteWithTriggerById(21, "Antibodies")).thenReturn(listNoteP2);
	
	mockMvc.perform(MockMvcRequestBuilders.get("/assess/familyName/TestBorderline?patientId=20,21&firstNameList=Test,TestBis&genderList=M,F&dateList=2000-06-24,2001-09-21")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.[0]diabetesAssessment").value("None"))
		.andExpect(jsonPath("$.[1]diabetesAssessment").value("In Danger"))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());

    }
    
    @Test
    public void getReportByNameProxyTest() throws Exception {
	Note note = new Note();
	note.setPatientId(20);
	note.setPatient("TestBorderline");
	note.setNote("Reaction Dizziness");
	Note note2 = new Note();
	note2.setPatientId(21);
	note2.setPatient("TestBorderline");
	note2.setNote("Reaction Dizziness abnormal Antibodies");
	List<Note> listNote = new ArrayList<>();
	listNote.add(note);
	listNote.add(note2);
	List<Note> listNoteP1 = new ArrayList<>();
	listNoteP1.add(note);
	List<Note> listNoteP2 = new ArrayList<>();
	listNoteP2.add(note2);
	HashMap<String, List<?>> listParam = new HashMap<>();
	List<String> firstNameList = new ArrayList<>();
	firstNameList.add("Test");
	firstNameList.add("Test");
	listParam.put("firstName", firstNameList);
	List<String> genderList = new ArrayList<>();
	genderList.add("M");
	genderList.add("F");
	listParam.put("gender", genderList);
	List<String> dateList = new ArrayList<>();
	dateList.add("1990-06-24");
	dateList.add("1960-09-21");
	listParam.put("date", dateList);
	List<Integer> id = new ArrayList<>();
	id.add(20);
	id.add(21);
	listParam.put("patientId", id);
	Gson gson = new Gson();
	String json = gson.toJson(listParam);
	
	when(reportRepository.findAllByName("TestBorderline")).thenReturn(listNote);
	when(reportRepository.findNumberNoteWithTriggerById(20, "Reaction")).thenReturn(listNoteP1);
	when(reportRepository.findNumberNoteWithTriggerById(20, "Dizziness")).thenReturn(listNoteP1);
	when(reportRepository.findNumberNoteWithTriggerById(21, "Abnormal")).thenReturn(listNoteP2);
	when(reportRepository.findNumberNoteWithTriggerById(21, "Reaction")).thenReturn(listNoteP2);
	when(reportRepository.findNumberNoteWithTriggerById(21, "Dizziness")).thenReturn(listNoteP2);
	when(reportRepository.findNumberNoteWithTriggerById(21, "Antibodies")).thenReturn(listNoteP2);
    
	mockMvc.perform(MockMvcRequestBuilders.post("/assess/familyName/TestBorderline")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		//.andExpect(jsonPath("$.[0]diabetesAssessment").value("None"))
		//.andExpect(jsonPath("$.[1]diabetesAssessment").value("In Danger"))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    
    }

}
