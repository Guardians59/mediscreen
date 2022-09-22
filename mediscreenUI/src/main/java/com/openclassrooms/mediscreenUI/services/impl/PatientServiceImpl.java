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
	PatientBean patient = new PatientBean();
	HashMap<String, Object> mapParams = new HashMap<>();
	mapParams.put("firstName", firstName);
	mapParams.put("lastName", lastName);
	mapParams.put("birthday", birthday);
	patient = patientProxy.getPatient(mapParams);
	
	return patient;
    }

}
