package com.openclassrooms.mediscreenUI.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.openclassrooms.mediscreenUI.beans.PatientBean;
import com.openclassrooms.mediscreenUI.beans.ReportBean;
import com.openclassrooms.mediscreenUI.proxies.IMicroServiceReportProxy;
import com.openclassrooms.mediscreenUI.services.IPatientService;
import com.openclassrooms.mediscreenUI.services.IReportService;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;
    
    @MockBean
    IMicroServiceReportProxy reportProxyMock;
    
    @MockBean
    IReportService reportService;
    
    @MockBean
    IPatientService patientService;
    
    @BeforeEach
    public void setUp() {
	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void getReportByIdTest() throws Exception {
	//GIVEN
	PatientBean patient = new PatientBean();
	patient.setId(50);
	patient.setFirstName("Test");
	patient.setLastName("TestController");
	patient.setGender("M");
	patient.setBirthday("1980-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	ReportBean report = new ReportBean();
	report.setFirstName("Test");
	report.setLastName("TestController");
	report.setAge(50);
	report.setDiabetesAssessment("Borderline");
	//WHEN
	when(patientService.getPatientById(50)).thenReturn(patient);
	when(reportService.getReportById(50, "Test", "TestController", "M", "1980-10-20")).thenReturn(report);
	//THEN
	mockMvc.perform(get("/assess/id/50")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("patientBean", patient)
		.flashAttr("reportBean", report))
		.andExpect(view().name("getReport"));
    }
    
    @Test
    public void getReportByIdErrorReportTest() throws Exception {
	//GIVEN
	PatientBean patient = new PatientBean();
	patient.setId(50);
	patient.setFirstName("Test");
	patient.setLastName("TestController");
	patient.setGender("M");
	patient.setBirthday("1980-10-20");
	patient.setAddress("2 rue test");
	patient.setPhoneNumber("032536");
	ReportBean report = new ReportBean();
	//WHEN
	when(patientService.getPatientById(50)).thenReturn(patient);
	when(reportService.getReportById(50, "Test", "TestController", "M", "1980-10-20")).thenReturn(report);
	//THEN
	mockMvc.perform(get("/assess/id/50")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("patientBean", patient)
		.flashAttr("reportBean", report))
		.andExpect(view().name("getPatient"));
    }
    @Test
    public void getReportByIdErrorTest() throws Exception {
	//GIVEN
	PatientBean patient = new PatientBean();
	ReportBean report = new ReportBean();
	//WHEN
	when(patientService.getPatientById(50)).thenReturn(patient);
	when(reportService.getReportById(50, "Test", "TestController", "M", "1980-10-20")).thenReturn(report);
	//THEN
	mockMvc.perform(get("/assess/id/50")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("patientBean", patient)
		.flashAttr("reportBean", report))
		.andExpect(view().name("home"));
    }
    
    @Test
    public void getAllReportByNameTest() throws Exception {
	//GIVEN
	PatientBean patient1 = new PatientBean();
	patient1.setId(60);
	patient1.setFirstName("TestFamily");
	patient1.setLastName("TestFamilyName");
	patient1.setGender("F");
	patient1.setBirthday("1980-02-10");
	PatientBean patient2 = new PatientBean();
	patient2.setId(61);
	patient2.setFirstName("TestFamilyBis");
	patient2.setLastName("TestFamilyName");
	patient2.setGender("M");
	patient2.setBirthday("1978-04-10");
	List<PatientBean> listPatient = new ArrayList<>();
	listPatient.add(patient1);
	listPatient.add(patient2);
	List<ReportBean> listReport = new ArrayList<>();
	ReportBean report = new ReportBean();
	report.setFirstName("TestFamily");
	report.setLastName("TestFamilyName");
	report.setAge(42);
	report.setDiabetesAssessment("Borderline");
	ReportBean reportBis = new ReportBean();
	reportBis.setFirstName("TestFamilyBis");
	reportBis.setLastName("TestFamilyName");
	reportBis.setAge(44);
	reportBis.setDiabetesAssessment("In Danger");
	listReport.add(report);
	listReport.add(reportBis);
	//WHEN
	when(patientService.getPatientById(60)).thenReturn(patient1);
	when(reportService.getReportByName("TestFamilyName", listPatient)).thenReturn(listReport);
	when(patientService.getAllByName("TestFamilyName")).thenReturn(listPatient);
	//THEN
	mockMvc.perform(get("/assess/60/familyName/TestFamilyName")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("patientBean", patient1)
		.flashAttr("reportBean", listReport))
		.andExpect(view().name("getAllReport"));
    }
    
    @Test
    public void getAllReportByNameErrorTest() throws Exception {
	//GIVEN
	PatientBean patient1 = new PatientBean();
	patient1.setId(60);
	patient1.setFirstName("TestFamily");
	patient1.setLastName("TestFamilyName");
	patient1.setGender("F");
	patient1.setBirthday("1980-02-10");
	PatientBean patient2 = new PatientBean();
	patient2.setId(61);
	patient2.setFirstName("TestFamilyBis");
	patient2.setLastName("TestFamilyName");
	patient2.setGender("M");
	patient2.setBirthday("1978-04-10");
	List<PatientBean> listPatient = new ArrayList<>();
	//WHEN
	when(patientService.getPatientById(60)).thenReturn(patient1);
	when(patientService.getAllByName("TestFamilyName")).thenReturn(listPatient);
	//THEN
	mockMvc.perform(get("/assess/60/familyName/TestFamilyName")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("patientBean", patient1))
		.andExpect(view().name("getPatient"));
    }

}
