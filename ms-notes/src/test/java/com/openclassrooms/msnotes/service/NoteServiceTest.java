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
    public void addAndDeleteNoteTest() {
	//GIVEN
	boolean result = false;
	Note note = new Note();
	note.setNote("Test for the add note");
	List<Note> list = new ArrayList<>();
	//WHEN
	result = noteService.addNoteByPatientId(note, 100, "Test add");
	list = noteRepository.findByPatientId(100);
	//THEN
	assertEquals(result, true);
	assertEquals(list.isEmpty(), false);
	
	//WHEN
	boolean deleteNote = noteService.deleteByPatientId(100);
	list = noteRepository.findByPatientId(100);
	//THEN
	assertEquals(deleteNote, true);
	assertEquals(list.isEmpty(), true);
    }
    
    @Test
    public void addNoteErrorTest() {
	//GIVEN
	boolean result = false;
	Note note = new Note();
	List<Note> list = new ArrayList<>();
	note.setNote("");
	//WHEN
	result = noteService.addNoteByPatientId(note, 111, "Test error add");
	list = noteRepository.findByPatientId(111);
	//THEN
	assertEquals(result, false);
	assertTrue(list.isEmpty());
    }
    
    @Test
    public void updateNoteTest() {
	//GIVEN
	boolean result = false;
	List<Note> noteRegister = noteRepository.findByPatientId(1);
	Note note = noteRegister.get(0);
	String medicalNote = note.getNote();
	Note noteUpdated = new Note();
	noteUpdated.setNote("Test Note updated");
	//WHEN
	result = noteService.updateNote(note.getId(), noteUpdated);
	List<Note> noteRegisterUpdated = noteRepository.findByPatientId(1);
	Note updated = noteRegisterUpdated.get(0);
	//THEN
	assertEquals(result, true);
	assertEquals(updated.getNote().equals(noteUpdated.getNote()), true);
	
	//AFTER
	noteUpdated.setNote(medicalNote);
	noteService.updateNote(note.getId(), noteUpdated);
    }
    
    @Test
    public void updateNoteErrorTest() {
	//GIVEN
	boolean result = false;
	List<Note> noteRegister = noteRepository.findByPatientId(1);
	Note note = noteRegister.get(0);
	String medicalNote = note.getNote();
	Note noteUpdated = new Note();
	noteUpdated.setNote("");
	//WHEN
	result = noteService.updateNote(note.getId(), noteUpdated);
	List<Note> noteRegisterUpdated = noteRepository.findByPatientId(1);
	Note updated = noteRegisterUpdated.get(0);
	//THEN
	assertEquals(result, false);
	assertEquals(updated.getNote().equals(medicalNote), true);
    }
    
    @Test
    public void getNoteByIdTest() {
	//GIVEN
	List<Note> noteRegister = noteRepository.findByPatientId(1);
	String id = noteRegister.get(0).getId();
	Note noteFindById = new Note();
	//WHEN
	noteFindById = noteService.getNoteById(id);
	//THEN
	assertEquals(noteFindById.getNote().equals(noteRegister.get(0).getNote()), true);
    }
    
    @Test
    public void getNoteByIdErrorTest() {
	//GIVEN
	Note noteFindById = new Note();
	//WHEN
	noteFindById = noteService.getNoteById("IdError");
	//THEN
	assertEquals(noteFindById.getNote() == null, true);
    }

}
