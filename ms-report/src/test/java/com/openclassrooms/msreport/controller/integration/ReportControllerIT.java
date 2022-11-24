package com.openclassrooms.msreport.controller.integration;

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

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerIT {
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    WebApplicationContext webApplicationContext;
    
    @BeforeEach
    public void setUp() {
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void getReportByIdTest() throws Exception {
	mockMvc.perform(MockMvcRequestBuilders.get("/assess/id/4?firstName=Test&lastName=TestEarlyOnset&gender=F&birthday=2002-06-28")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.diabetesAssessment").value("Early onset"))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }

}
