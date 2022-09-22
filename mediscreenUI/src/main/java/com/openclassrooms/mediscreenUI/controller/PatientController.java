package com.openclassrooms.mediscreenUI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.mediscreenUI.beans.PatientBean;
import com.openclassrooms.mediscreenUI.models.GetPatientModel;
import com.openclassrooms.mediscreenUI.services.IPatientService;

@Controller
public class PatientController {
    
    @Autowired
    IPatientService patientService;
    
    @GetMapping("/patient/search")
    public String getSearchPage(Model model) {
	GetPatientModel patientModel = new GetPatientModel();
	model.addAttribute("getPatientModel", patientModel);
	return "searchPatient";
    }
    
    @PostMapping("/patient/getPatient")
    public String postSearchPatient(@ModelAttribute("getPatientModel") GetPatientModel patient, Model model) {
	PatientBean patientInfos = new PatientBean();
	patientInfos = patientService.getPatient(patient.getFirstName(), patient.getLastName(), patient.getBirthday());
	model.addAttribute("patientBean", patientInfos);
	System.out.println(patientInfos.toString());
	if (patientInfos.getGender() != null) {
	    return "getPatient";
	} else {
	    return "searchPatient";
	}
    }
    
    @GetMapping("/patient/getPatient")
    public String getPatient(@ModelAttribute("getPatientModel") GetPatientModel patient, Model model) {
	PatientBean patientInfos = new PatientBean();
	patientInfos = patientService.getPatient(patient.getFirstName(), patient.getLastName(), patient.getBirthday());
	model.addAttribute("patientBean", patientInfos);
	System.out.println("--------" + patientInfos.toString());
	if (patientInfos.getGender() != null) {
	    return "getPatient";
	} else {
	    return "searchPatient";
	}
    }

}
