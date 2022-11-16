package com.openclassrooms.mediscreenUI.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.openclassrooms.mediscreenUI.beans.PatientBean;
import com.openclassrooms.mediscreenUI.beans.ReportBean;
import com.openclassrooms.mediscreenUI.proxies.IMicroServiceReportProxy;
import com.openclassrooms.mediscreenUI.services.IReportService;

@Service
public class ReportServiceImpl implements IReportService {

    private Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    private final IMicroServiceReportProxy reportProxy;

    public ReportServiceImpl(IMicroServiceReportProxy reportProxy) {
	this.reportProxy = reportProxy;
    }

    @Override
    public ReportBean getReportById(int patientId, String firstName, String lastName, String gender, String date) {
	ReportBean result = new ReportBean();
	HashMap<String, Object> mapParams = new HashMap<>();
	mapParams.put("firstName", firstName);
	mapParams.put("lastName", lastName);
	mapParams.put("gender", gender);
	mapParams.put("date", date);
	logger.info("Search the report for the patient with id " + patientId);
	result = reportProxy.getReportById(patientId, mapParams);
	if (result.getDiabetesAssessment() == null) {
	    logger.error("An error occured while searching the report for the patient with id " + patientId);
	} else {
	    logger.info("The report was retrieved successfully");
	}
	return result;
    }

    @Override
    public List<ReportBean> getReportByName(String lastName, List<PatientBean> listPatient) {
	List<ReportBean> result = new ArrayList<>();
	HashMap<String, List<?>> mapParams = new HashMap<>();
	List<Integer> patientId = new ArrayList<>();
	List<String> firstNameList = new ArrayList<>();
	List<String> genderList = new ArrayList<>();
	List<String> dateList = new ArrayList<>();
	listPatient.forEach(patient -> {
	    patientId.add(patient.getId());
	    firstNameList.add(patient.getFirstName());
	    genderList.add(patient.getGender());
	    dateList.add(patient.getBirthday());
	});
	mapParams.put("patientId", patientId);
	mapParams.put("firstName", firstNameList);
	mapParams.put("gender", genderList);
	mapParams.put("date", dateList);
	logger.info("Search the family report for the patient with lastName " + lastName);
	result = reportProxy.getReportByLastName(lastName, mapParams);
	if(result.isEmpty()) {
	    logger.error("An error occured while searching the report for the patient with lastName " + lastName);
	} else {
	    logger.info("The report was retrieved successfully");
	}
	return result;
    }

}
