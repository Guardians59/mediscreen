package com.openclassrooms.msnotes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.msnotes.model.Note;

@SpringBootTest
public class NoteServiceTest {
    
    @Autowired
    INoteService noteService;
    
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

}
