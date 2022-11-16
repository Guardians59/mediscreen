package com.openclassrooms.mediscreenUI.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.openclassrooms.mediscreenUI.beans.PatientBean;
import com.openclassrooms.mediscreenUI.beans.ReportBean;
import com.openclassrooms.mediscreenUI.services.IPatientService;
import com.openclassrooms.mediscreenUI.services.IReportService;

@Controller
public class ReportController {
    
    @Autowired
    IReportService reportService;
    
    @Autowired
    IPatientService patientService;
    
    @GetMapping("/assess/id/{id}")
    public String getReportById(@PathVariable("id") int id, Model model) {
	PatientBean patient = new PatientBean();
	patient = patientService.getPatientById(id);
	model.addAttribute("patientBean", patient);
	if(patient.getLastName() != null) {
	    ReportBean report = new ReportBean();
	    report = reportService.getReportById(id, patient.getFirstName(), patient.getLastName(), patient.getGender(), patient.getBirthday());
	    if(report.getDiabetesAssessment() != null) {
		model.addAttribute("reportBean", report);
		return "getReport";
	    } else {
		model.addAttribute("getReportError", "Une erreur est survenue lors de la recherche du rapport");
		return "getPatient";
	    }
	} else {
	    model.addAttribute("getPatientError", "Une erreur est survenue veuillez réessayer");
	    return "home";
	}
    }
    
    @GetMapping("/assess/{id}/familyName/{lastName}")
    public String getReportByName(@PathVariable("id") int id, @PathVariable("lastName") String lastName, Model model) {
	PatientBean patient = new PatientBean();
	patient = patientService.getPatientById(id);
	model.addAttribute("patientBean", patient);
	List<PatientBean> listPatient = new ArrayList<>();
	listPatient = patientService.getAllByName(lastName);
	if(!listPatient.isEmpty()) {
	    List<ReportBean> result = new ArrayList<>();
	    result = reportService.getReportByName(lastName, listPatient);
	    model.addAttribute("reportBean", result);
	    return "getAllReport";
	} else {
	    model.addAttribute("getReportError", "Une erreur est survenue lors de la recherche du rapport");
	    return "getPatient";
	}
	
    }

}
