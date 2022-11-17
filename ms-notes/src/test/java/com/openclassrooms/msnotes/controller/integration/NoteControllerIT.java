package com.openclassrooms.msnotes.controller.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.openclassrooms.msnotes.model.Note;
import com.openclassrooms.msnotes.repository.INoteRepository;
import com.openclassrooms.msnotes.service.INoteService;

@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    INoteService noteService;

    @Autowired
    INoteRepository noteRepository;

    @BeforeEach
    public void setUp() {
	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getNoteByPatientIdTest() throws Exception {
	mockMvc.perform(MockMvcRequestBuilders.get("/note/getByPatientId/4")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.[0].patient").value("TestEarlyOnset"))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void addAndDeleteNoteTest() throws Exception {
	Note note = new Note();
	note.setNote("Test controller adding the note");
	Gson gson = new Gson();
	String json = gson.toJson(note);

	mockMvc.perform(MockMvcRequestBuilders.post("/note/add/12?patientName=TestAdd")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andDo(MockMvcResultHandlers.print());

	mockMvc.perform(MockMvcRequestBuilders.delete("/note/patientId/12").content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void addNoteErrorTest() throws Exception {
	Note note = new Note();
	note.setNote("");
	Gson gson = new Gson();
	String json = gson.toJson(note);

	mockMvc.perform(MockMvcRequestBuilders.post("/note/add/12?patientName=TestErrorAdd")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
    }

    @Test
    public void updateNoteTest() throws Exception {
	// GIVEN
	List<Note> noteRegister = noteRepository.findByPatientId(2);
	Note note = noteRegister.get(0);
	String medicalNote = note.getNote();
	String id = note.getId();
	Note noteUpdated = new Note();
	noteUpdated.setNote("Test integration update");
	Gson gson = new Gson();
	String json = gson.toJson(noteUpdated);

	mockMvc.perform(MockMvcRequestBuilders.put("/note/update/" + id).content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	// AFTER
	note.setNote(medicalNote);
	noteRepository.save(note);
    }

    @Test
    public void updateNoteErrorTest() throws Exception {
	List<Note> noteRegister = noteRepository.findByPatientId(2);
	Note note = noteRegister.get(0);
	String id = note.getId();
	Note noteUpdated = new Note();
	noteUpdated.setNote("");
	Gson gson = new Gson();
	String json = gson.toJson(noteUpdated);

	mockMvc.perform(MockMvcRequestBuilders.put("/note/update/" + id).content(json)
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());

    }

    @Test
    public void getNoteByIdTest() throws Exception {
	List<Note> noteRegister = noteRepository.findByPatientId(2);
	String id = noteRegister.get(0).getId();
	String medicalNote = noteRegister.get(0).getNote();
	
	mockMvc.perform(MockMvcRequestBuilders.get("/note/get/" + id)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.note").value(medicalNote))
		.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

}
