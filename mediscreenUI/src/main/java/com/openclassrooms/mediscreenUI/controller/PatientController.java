package com.openclassrooms.mediscreenUI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.mediscreenUI.beans.PatientBean;
import com.openclassrooms.mediscreenUI.models.GetPatientModel;
import com.openclassrooms.mediscreenUI.services.IFormValidService;
import com.openclassrooms.mediscreenUI.services.IPatientService;

@Controller
public class PatientController {

    @Autowired
    IPatientService patientService;
    
    @Autowired
    IFormValidService formValid;

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

	if (patientInfos.getGender() != null) {
	    return "getPatient";
	} else {
	    return "searchPatient";
	}
    }
    
    @GetMapping("/patient/getPatient/{id}")
    public String getPatientById(@PathVariable("id") int id, Model model) {
	PatientBean patientBean = new PatientBean();
	patientBean = patientService.getPatientById(id);
	model.addAttribute("patientBean", patientBean);
	return "getPatient";
    }

    @GetMapping("/patient/update/{id}")
    public String getUpdatePatientPage(@PathVariable("id") int id, Model model) {
	PatientBean patientBean = new PatientBean();
	patientBean = patientService.getPatientById(id);

	if (patientBean.getFirstName() != null) {
	    model.addAttribute("patientBean", patientBean);
	    return "updatePatient";
	} else {
	    return "getPatient";
	}
    }

    @PostMapping("/patient/update/{id}")
    public String updatePatient(@PathVariable("id") int id, @ModelAttribute PatientBean patientUpdated, Model model) {
	boolean formValidResult = formValid.updateFormValid(patientUpdated);
	if (formValidResult == true) {
	    int result = patientService.updatePatient(id, patientUpdated);
		if (result == 1) {
		    PatientBean patient = patientService.getPatientById(id);
		    model.addAttribute("patientBean", patient);
		    model.addAttribute("updateSuccess", "La mise à jour à été effectuée avec succès");
		    return "getPatient";
		} else {
		    PatientBean patient = patientService.getPatientById(id);
		    model.addAttribute("patientBean", patient);
		    model.addAttribute("noUpdate", "Aucune information n'a été modifiée");
		    return "getPatient";
		}
	} else {
	    PatientBean patientBean = patientService.getPatientById(id);
	    model.addAttribute("patientBean", patientBean);
	    model.addAttribute("updateError",
		    "Une erreur est survenue, vérifier que vous remplissez tous les champs d'informations");
	    return "updatePatient";
	}
    }

}
