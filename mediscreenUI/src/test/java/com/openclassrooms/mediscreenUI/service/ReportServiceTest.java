package com.openclassrooms.mediscreenUI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.openclassrooms.mediscreenUI.beans.PatientBean;
import com.openclassrooms.mediscreenUI.beans.ReportBean;
import com.openclassrooms.mediscreenUI.proxies.IMicroServiceReportProxy;
import com.openclassrooms.mediscreenUI.services.IReportService;

@SpringBootTest
public class ReportServiceTest {
    
    @MockBean
    IMicroServiceReportProxy reportProxyMock;
    
    @Autowired
    IReportService reportService;
    
    @Test
    public void getReportByIdTest() {
	//GIVEN
	ReportBean result = new ReportBean();
	ReportBean report = new ReportBean();
	report.setFirstName("Test");
	report.setLastName("TestService");
	report.setAge(30);
	report.setDiabetesAssessment("Borderline");
	//WHEN
	when(reportProxyMock.getReportById(20, "Test", "TestService", "M", "1992-09-20")).thenReturn(report);
	result = reportService.getReportById(20, "Test", "TestService", "M", "1992-09-20");
	//THEN
	assertEquals(result.getDiabetesAssessment(), "Borderline");
    }
    
    @Test
    public void getAllReportByNameTest() {
	//GIVEN
	List<ReportBean> resultProxy = new ArrayList<>();
	List<Integer> patientIdList = new ArrayList<>();
	List<String> firstNameList = new ArrayList<>();
	List<String> genderList = new ArrayList<>();
	List<String> birthdayList = new ArrayList<>();
	patientIdList.add(50);
	patientIdList.add(51);
	firstNameList.add("TestFamily");
	firstNameList.add("TestFamilyBis");
	genderList.add("F");
	genderList.add("M");
	birthdayList.add("1980-02-10");
	birthdayList.add("1978-04-10");
	List<PatientBean> listPatient = new ArrayList<>();
	PatientBean patient1 = new PatientBean();
	patient1.setId(50);
	patient1.setFirstName("TestFamily");
	patient1.setLastName("TestFamilyName");
	patient1.setGender("F");
	patient1.setBirthday("1980-02-10");
	listPatient.add(patient1);
	PatientBean patient2 = new PatientBean();
	patient2.setId(51);
	patient2.setFirstName("TestFamilyBis");
	patient2.setLastName("TestFamilyName");
	patient2.setGender("M");
	patient2.setBirthday("1978-04-10");
	listPatient.add(patient2);
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
	resultProxy.add(report);
	resultProxy.add(reportBis);
	List<ReportBean> result = new ArrayList<>();
	//WHEN
	when(reportProxyMock.getReportByLastName("TestFamilyName", patientIdList, firstNameList, genderList, birthdayList)).thenReturn(resultProxy);
	result = reportService.getReportByName("TestFamilyName", listPatient);
	//THEN
	assertEquals(result.size(), 2);
	assertEquals(result.get(0).getDiabetesAssessment(), "Borderline");
	assertEquals(result.get(1).getDiabetesAssessment(), "In Danger");
    }

}
