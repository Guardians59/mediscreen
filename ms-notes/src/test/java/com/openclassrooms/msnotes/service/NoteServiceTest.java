package com.openclassrooms.msnotes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.msnotes.model.Note;
import com.openclassrooms.msnotes.repository.INoteRepository;

@SpringBootTest
public class NoteServiceTest {
    
    @Autowired
    INoteService noteService;
    
    @Autowired
    INoteRepository noteRepository;
    
    @Test
    public void getNoteByPatientIdTest() {
	//GIVEN
	List<Note> result = new ArrayList<>();
	//WHEN
	result = noteService.getNoteByPatientId(3);
	//THEN
	assertEquals(result.size(), 2);
    }
    
    @Test
    public void getNoteByPatientIdErrorTest() {
	//GIVEN
	List<Note> result = new ArrayList<>();
	//WHEN
	result = noteService.getNoteByPatientId(300);
	//THEN
	assertEquals(result.isEmpty(), true);
    }
    
    @Test
    public void addNoteTest() {
	//GIVEN
	boolean result = false;
	Note note = new Note();
	//note.setPatientId(10);
	note.setPatient("TestAdd");
	note.setNote("Test for the add note");
	List<Note> list = new ArrayList<>();
	//WHEN
	result = noteService.addNoteByPatientId(note, 10);
	list = noteRepository.findByPatientId(10);
	//THEN
	assertEquals(result, true);
	assertEquals(list.isEmpty(), false);
	//AFTER
	Note noteRegister = list.get(0);
	noteRepository.delete(noteRegister);
    }
    
    @Test
    public void addNoteErrorTest() {
	//GIVEN
	boolean result = false;
	Note note = new Note();
	List<Note> list = new ArrayList<>();
	//note.setPatientId(10);
	note.setPatient("TestAdd");
	note.setNote("");
	//WHEN
	result = noteService.addNoteByPatientId(note, 11);
	list = noteRepository.findByPatientId(11);
	//THEN
	assertEquals(result, false);
	assertTrue(list.isEmpty());
    }

}
