package com.openclassrooms.msreport.controller.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

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
	mockMvc.perform(MockMvcRequestBuilders.get("/assess/id/4?firstName=Test&lastName=TestEarlyOnset&gender=F&date=2002-06-28")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.diabetesAssessment").value("Early onset"))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void getReportByIdProxyTest() throws Exception {
	HashMap<String, Object> mapParams = new HashMap<>();
	mapParams.put("firstName", "Test");
	mapParams.put("lastName", "TestNone");
	mapParams.put("gender", "F");
	mapParams.put("date", "1966-12-31");
	Gson gson = new Gson();
	String json = gson.toJson(mapParams);
	
	mockMvc.perform(MockMvcRequestBuilders.post("/assess/id/1")
		.content(json)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.diabetesAssessment").value("None"))
		.andExpect(status().isOk())
		.andDo(MockMvcResultHandlers.print());
    }

}
