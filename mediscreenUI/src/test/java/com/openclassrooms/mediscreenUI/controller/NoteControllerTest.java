package com.openclassrooms.mediscreenUI.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.openclassrooms.mediscreenUI.beans.NoteBean;
import com.openclassrooms.mediscreenUI.beans.PatientBean;
import com.openclassrooms.mediscreenUI.proxies.IMicroServiceNoteProxy;
import com.openclassrooms.mediscreenUI.services.IFormValidService;
import com.openclassrooms.mediscreenUI.services.INoteService;
import com.openclassrooms.mediscreenUI.services.IPatientService;

@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;
    
    @MockBean
    IMicroServiceNoteProxy noteProxy;
    
    @MockBean
    INoteService noteService;

    @MockBean
    IPatientService patientService;
    
    @MockBean
    IFormValidService formValidService;
    
    @BeforeEach
    public void setUp() {
	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void getNoteByPatientIdTest() throws Exception {
	//GIVEN
	NoteBean note = new NoteBean();
	note.setPatientId(20);
	note.setPatient("NoteTest");
	note.setNote("Test get note by id patient");
	PatientBean patient = new PatientBean();
	patient.setId(20);
	patient.setFirstName("Test");
	patient.setLastName("TestNote");
	patient.setGender("M");
	patient.setBirthday("1980-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	List<NoteBean> list = new ArrayList<>();
	list.add(note);
	//WHEN
	when(noteProxy.getNoteByPatientId(20)).thenReturn(list);
	when(noteService.getNoteByPatientId(20)).thenReturn(list);
	when(patientService.getPatientById(20)).thenReturn(patient);
	//THEN
	mockMvc.perform(get("/note/getByPatientId/20")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("noteBean", list)
		.flashAttr("name", list.get(0)))
		.andExpect(view().name("historiqueNote"));
    }
    
    @Test
    public void getNoteByPatientIdNoHistoriqueTest() throws Exception {
	//GIVEN
	List<NoteBean> list = new ArrayList<>();
	PatientBean patient = new PatientBean();
	patient.setId(21);
	patient.setFirstName("Test");
	patient.setLastName("TestNote");
	patient.setGender("M");
	patient.setBirthday("1980-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	//WHEN
	when(noteProxy.getNoteByPatientId(21)).thenReturn(list);
	when(noteService.getNoteByPatientId(21)).thenReturn(list);
	when(patientService.getPatientById(21)).thenReturn(patient);
	//THEN
	mockMvc.perform(get("/note/getByPatientId/21")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("patientBean", patient))
		.andExpect(view().name("noHistoriqueNote"));
    }
    
    @Test
    public void addNoteTest() throws Exception {
	//GIVEN
	NoteBean note = new NoteBean();
	note.setPatient("TestAdd");
	note.setNote("Test add note service");
	ResponseEntity resultAdd = Mockito.mock(ResponseEntity.class);
	Mockito.when(resultAdd.getStatusCode()).thenReturn(HttpStatus.CREATED);
	List<NoteBean> list = new ArrayList<>();
	list.add(note);
	PatientBean patient = new PatientBean();
	patient.setId(30);
	patient.setFirstName("Test");
	patient.setLastName("TestAddNote");
	patient.setGender("M");
	patient.setBirthday("1980-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	//WHEN
	when(noteProxy.addNote(30, note)).thenReturn(resultAdd);
	when(formValidService.addNoteFormValid(note)).thenReturn(true);
	when(noteService.addNote(note, 30)).thenReturn(true);
	when(noteService.getNoteByPatientId(30)).thenReturn(list);
	when(patientService.getPatientById(30)).thenReturn(patient);
	//THEN
	mockMvc.perform(post("/note/add/30")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("noteBean", note))
		.andExpect(model().attributeExists("addSuccess"))
		.andExpect(view().name("historiqueNote"));
    }
    
    @Test
    public void addNoteErrorTest() throws Exception {
	//GIVEN
	NoteBean note = new NoteBean();
	note.setPatient("");
	note.setNote("Test add note service");
	PatientBean patient = new PatientBean();
	patient.setId(31);
	patient.setFirstName("Test");
	patient.setLastName("TestAddErrorNote");
	patient.setGender("M");
	patient.setBirthday("1980-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	//WHEN
	when(formValidService.addNoteFormValid(note)).thenReturn(false);
	when(noteService.addNote(note, 31)).thenReturn(false);
	when(patientService.getPatientById(31)).thenReturn(patient);
	//THEN
	mockMvc.perform(post("/note/add/31")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("noteBean", note))
		.andExpect(model().attributeExists("addError"))
		.andExpect(view().name("addNote"));
    }

}
