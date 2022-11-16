package com.openclassrooms.msreport.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.msreport.model.Note;
import com.openclassrooms.msreport.repository.IReportRepository;

@SpringBootTest
public class CountTriggerServiceTest {
    
    @Autowired
    ICountTriggerService countTriggerService;
    
    @Test
    public void getCountTriggerNoneByIdTest() throws IOException {
	int result = -1;
	result = countTriggerService.numberOfTriggerById(1);
	assertEquals(result, 0);
    }
    
    @Test
    public void getCountTriggerBorderlineByIdTest() throws IOException {
	int result = -1;
	result = countTriggerService.numberOfTriggerById(2);
	assertEquals(result, 2);
    }
    
    @Test
    public void getCountTriggerInDangerByIdTest() throws IOException {
	int result = -1;
	result = countTriggerService.numberOfTriggerById(3);
	assertEquals(result, 3);
    }
    
    @Test
    public void getCountTriggerEarlyOnsetByIdTest() throws IOException {
	int result = -1;
	result = countTriggerService.numberOfTriggerById(4);
	assertEquals(result, 7);
    }
    
    @Test
    public void getCountTriggerNoneByNameTest() throws IOException {
	HashMap<Integer, Integer> result = new HashMap<>();
	result = countTriggerService.numberOfTriggerByName("TestNone");
	assertEquals(result.containsKey(1), true);
	assertEquals(result.get(1).intValue(), 0);
    }
    
    @Test
    public void getCountTriggerBorderlineByNameTest() throws IOException {
	HashMap<Integer, Integer> result = new HashMap<>();
	result = countTriggerService.numberOfTriggerByName("TestBorderline");
	assertEquals(result.containsKey(2), true);
	assertEquals(result.get(2).intValue(), 2);
    }
    
    @Test
    public void getCountTriggerInDangerByNameTest() throws IOException {
	HashMap<Integer, Integer> result = new HashMap<>();
	result = countTriggerService.numberOfTriggerByName("TestInDanger");
	assertEquals(result.containsKey(3), true);
	assertEquals(result.get(3).intValue(), 3);
    }
    
    @Test
    public void getCountTriggerEarlyOnsetByNameTest() throws IOException {
	HashMap<Integer, Integer> result = new HashMap<>();
	result = countTriggerService.numberOfTriggerByName("TestEarlyOnset");
	assertEquals(result.containsKey(4), true);
	assertEquals(result.get(4).intValue(), 7);
    }
    
    @Test
    public void getMultipleCountTriggerByNameTest() throws IOException {
	IReportRepository repositoryMock = Mockito.mock(IReportRepository.class);
	Note note = new Note();
	note.setId("634d7ac0b14dfa6be13e4300");
	note.setPatientId(20);
	note.setPatient("TestMultiple");
	note.setNote("Reaction Dizziness Abnormal");
	Note note2 = new Note();
	note.setId("634d7ac0b14dfa6be13e4301");
	note2.setPatientId(21);
	note2.setPatient("TestMultiple");
	note2.setNote("Reaction Dizziness ");
	List<Note> listNote = new ArrayList<>();
	listNote.add(note);
	listNote.add(note2);
	System.out.println(listNote.toString());
	when(repositoryMock.findAllByName("TestMultiple")).thenReturn(listNote);
	HashMap<Integer, Integer> result = new HashMap<>();
	result = countTriggerService.numberOfTriggerByName("TestMultiple");
    }

}
