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

/**
 * La classe ReportController est le controller qui gère les URL de génération des rapports
 * de diabète.
 * 
 * @author Dylan
 *
 */
@Controller
public class ReportController {
    
    @Autowired
    IReportService reportService;
    
    @Autowired
    IPatientService patientService;
    
    /**
     * La méthode getReportById permet de générer un rapport de diabète sur un patient via son
     * id.
     * 
     * @param id l'id du patient.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue getReport, ou la vue getPatient si une erreur est rencontrée.
     */
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
    
    /**
     * La méthode getReportByName permet de générer les rapports de diabète familiaux selon le
     * nom de famille.
     * 
     * @param id l'id du patient actuellement recherché.
     * @param lastName le nom de famille.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue getAllReport, ou la vue getPatient si une erreur est rencontrée.
     */
    @GetMapping("/assess/{id}/familyName/{lastName}")
    public String getReportByName(@PathVariable("id") int id, @PathVariable("lastName") String lastName, Model model) {
	
	/*
	 * Nous récupérons les informations du patient via son id afin de pouvoir retourner
	 * sur sa page d'information si une erreur est rencontrée lors de la génération
	 * des rapports de diabète pour sa famille.
	 */
	PatientBean patient = new PatientBean();
	patient = patientService.getPatientById(id);
	model.addAttribute("patientBean", patient);
	
	/*
	 * Nous récupérons la liste de tous les patients portant le même nom que le patient,
	 * pour faire appel au service de génération des rapports de diabète familiaux.
	 */
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
