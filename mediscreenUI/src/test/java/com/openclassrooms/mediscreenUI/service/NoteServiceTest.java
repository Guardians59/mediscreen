package com.openclassrooms.mediscreenUI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.openclassrooms.mediscreenUI.beans.NoteBean;
import com.openclassrooms.mediscreenUI.proxies.IMicroServiceNoteProxy;
import com.openclassrooms.mediscreenUI.services.INoteService;

@SpringBootTest
public class NoteServiceTest {
    
    @MockBean
    IMicroServiceNoteProxy noteProxy;
    
    @Autowired
    INoteService noteService;
    
    @Test
    public void getNoteByPatientIdTest() {
	//GIVEN
	List<NoteBean> result = new ArrayList<>();
	NoteBean note = new NoteBean();
	note.setPatientId(20);
	note.setPatient("NoteTest");
	note.setNote("Test get note by id patient");
	List<NoteBean> list = new ArrayList<>();
	list.add(note);
	//WHEN
	when(noteProxy.getNoteByPatientId(20)).thenReturn(list);
	result = noteService.getNoteByPatientId(20);
	//THEN
	assertEquals(result.get(0).getPatient(), list.get(0).getPatient());
	
    }
    
    @Test
    public void addNoteTest() {
	//GIVEN
	boolean result = false;
	NoteBean note = new NoteBean();
	note.setNote("Test add note service");
	ResponseEntity resultAdd = Mockito.mock(ResponseEntity.class);
	Mockito.when(resultAdd.getStatusCode()).thenReturn(HttpStatus.CREATED);
	//WHEN
	when(noteProxy.addNote(30, "TestAdd", note)).thenReturn(resultAdd);
	result = noteService.addNote(note, 30, "TestAdd");
	//THEN
	assertEquals(result, true);
    }
    
    @Test
    public void addNoteErrorTest() {
	//GIVEN
	boolean result;
	NoteBean note = new NoteBean();
	note.setNote("");
	ResponseEntity resultAdd = Mockito.mock(ResponseEntity.class);
	Mockito.when(resultAdd.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);
	//WHEN
	when(noteProxy.addNote(30, "TestErrorAdd", note)).thenReturn(resultAdd);
	result = noteService.addNote(note, 30, "TestErrorAdd");
	//THEN
	assertEquals(result, false);
    }
    
    @Test
    public void updateNoteByIdTest() {
	//GIVEN
	int result =-1;
	NoteBean note = new NoteBean();
	note.setId("634d7ac0b14dfa6be13e4200");
	note.setPatientId(30);
	note.setPatient("TestUpdate");
	note.setNote("Test update");
	NoteBean noteUpdated = new NoteBean();
	noteUpdated.setNote("Test Note updated");
	ResponseEntity resultAdd = Mockito.mock(ResponseEntity.class);
	Mockito.when(resultAdd.getStatusCode()).thenReturn(HttpStatus.OK);
	//WHEN
	when(noteProxy.getNoteById("634d7ac0b14dfa6be13e4200")).thenReturn(note);
	when(noteProxy.updateNoteById("634d7ac0b14dfa6be13e4200", noteUpdated)).thenReturn(resultAdd);
	result = noteService.updateNoteById("634d7ac0b14dfa6be13e4200", noteUpdated);
	//THEN
	assertEquals(result, 1);
    }
    
    @Test
    public void updateNoteByIdSameInfosTest() {
	//GIVEN
	int result =-1;
	NoteBean note = new NoteBean();
	note.setId("634d7ac0b14dfa6be13e4200");
	note.setPatientId(30);
	note.setPatient("TestUpdate");
	note.setNote("Test update");
	NoteBean noteUpdated = new NoteBean();
	noteUpdated.setNote("Test update");
	ResponseEntity resultAdd = Mockito.mock(ResponseEntity.class);
	Mockito.when(resultAdd.getStatusCode()).thenReturn(HttpStatus.OK);
	//WHEN
	when(noteProxy.getNoteById("634d7ac0b14dfa6be13e4200")).thenReturn(note);
	when(noteProxy.updateNoteById("634d7ac0b14dfa6be13e4200", noteUpdated)).thenReturn(resultAdd);
	result = noteService.updateNoteById("634d7ac0b14dfa6be13e4200", noteUpdated);
	//THEN
	assertEquals(result, 0);
    }

}
