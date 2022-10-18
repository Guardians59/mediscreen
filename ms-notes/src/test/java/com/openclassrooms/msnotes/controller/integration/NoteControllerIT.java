package com.openclassrooms.msnotes.controller.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    
    @BeforeEach
    public void setUp() {
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void getNoteByPatientIdTest() throws Exception {
	mockMvc.perform(MockMvcRequestBuilders.get("/note/getByPatientId/4")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.[0].patient").value("TestEarlyOnSet"))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }

}
