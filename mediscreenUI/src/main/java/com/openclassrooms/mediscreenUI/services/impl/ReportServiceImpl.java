package com.openclassrooms.mediscreenUI.services.impl;

import java.util.ArrayList;
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
    public ReportBean getReportById(int patientId, String firstName, String lastName, String gender, String birthday) {
	ReportBean result = new ReportBean();
	logger.info("Search the report for the patient with id " + patientId);
	result = reportProxy.getReportByPatientId(patientId, firstName, lastName, gender, birthday);
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
	List<Integer> patientIdList = new ArrayList<>();
	List<String> firstNameList = new ArrayList<>();
	List<String> genderList = new ArrayList<>();
	List<String> birthdayList = new ArrayList<>();
	listPatient.forEach(patient -> {
	    patientIdList.add(patient.getId());
	    firstNameList.add(patient.getFirstName());
	    genderList.add(patient.getGender());
	    birthdayList.add(patient.getBirthday());
	});
	logger.info("Search the family report for the patient with lastName " + lastName);
	result = reportProxy.getReportByLastName(lastName, patientIdList, firstNameList, genderList, birthdayList);
	if(result.isEmpty()) {
	    logger.error("An error occured while searching the report for the patient with lastName " + lastName);
	} else {
	    logger.info("The report was retrieved successfully");
	}
	return result;
    }

}
