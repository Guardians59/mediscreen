package com.openclassrooms.mediscreenUI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

}
