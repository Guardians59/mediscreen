package com.openclassrooms.mediscreenUI.services.impl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.openclassrooms.mediscreenUI.beans.PatientBean;
import com.openclassrooms.mediscreenUI.proxies.IMicroServicePatientProxy;
import com.openclassrooms.mediscreenUI.services.IPatientService;

@Service
public class PatientServiceImpl implements IPatientService {

    private Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);
    private final IMicroServicePatientProxy patientProxy;

    public PatientServiceImpl(IMicroServicePatientProxy patientProxy) {
	this.patientProxy = patientProxy;
    }

    @Override
    public PatientBean getPatient(String firstName, String lastName, String birthday) {
	logger.debug("Search patient with firstname : " + firstName + " lastname : " + lastName + " and birthday : "
		+ birthday);
	PatientBean patient = new PatientBean();
	HashMap<String, Object> mapParams = new HashMap<>();
	mapParams.put("firstName", firstName);
	mapParams.put("lastName", lastName);
	mapParams.put("birthday", birthday);
	patient = patientProxy.getPatient(mapParams);
	if(patient.getFirstName() != null) {
	    logger.info("Patient successfully found");
	} else {
	    logger.error("No patients found");
	}
	return patient;
    }

    @Override
    public PatientBean getPatientById(int id) {
	logger.debug("Search patient with id : " + id);
	PatientBean patient = new PatientBean();
	patient = patientProxy.getPatientById(id);
	if(patient.getFirstName() != null) {
	    logger.info("Patient successfully found");
	} else {
	    logger.error("No patients found");
	} 
	return patient;
    }

    @Override
    public int updatePatient(int id, PatientBean patientUpdated) {
	int result = -1;
	logger.debug("Update patient with id : " + id);
	PatientBean patient = patientProxy.getPatientById(id);
	String patientInfos = patient.toString();
	String patientInfosUpdated = patientUpdated.toString();
	if (patientInfos.equals(patientInfosUpdated)) {
	    result = 0;
	    logger.info("No information has been changed");
	} else {
	    patientProxy.updatePatient(id, patientUpdated);
	    result = 1;
	    logger.info("Patient information has been updated successfully");
	}
	return result;
    }

}
