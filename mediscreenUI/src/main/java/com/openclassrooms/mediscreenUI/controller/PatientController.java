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
import com.openclassrooms.mediscreenUI.services.INoteService;
import com.openclassrooms.mediscreenUI.services.IPatientService;

@Controller
public class PatientController {

    @Autowired
    IPatientService patientService;

    @Autowired
    INoteService noteService;

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
	    GetPatientModel patientModel = new GetPatientModel();
	    model.addAttribute("getPatientModel", patientModel);
	    model.addAttribute("searchError",
		    "Une erreur est survenue lors de la recherche du patient, vérifiez les informations renseignées");
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
	    GetPatientModel patientModel = new GetPatientModel();
	    model.addAttribute("getPatientModel", patientModel);
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
	    model.addAttribute("patientBean", patientBean);
	    return "getPatient";
	}
    }

    @PostMapping("/patient/update/{id}")
    public String updatePatient(@PathVariable("id") int id, @ModelAttribute PatientBean patientUpdated, Model model) {
	boolean formValidResult = formValid.updatePatientFormValid(patientUpdated);
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

    @GetMapping("/patient/add")
    public String getAddPatientPage(Model model) {
	PatientBean patient = new PatientBean();
	model.addAttribute("newPatient", patient);
	return "addPatient";
    }

    @PostMapping("/patient/add")
    public String addPatient(@ModelAttribute("newPatient") PatientBean newPatient, Model model) {
	boolean formValidResult = formValid.addPatientFormValid(newPatient);
	if (formValidResult == true) {
	    boolean result = patientService.addPatient(newPatient);
	    if (result == true) {
		PatientBean patient = patientService.getPatient(newPatient.getFirstName(), newPatient.getLastName(),
			newPatient.getBirthday());
		model.addAttribute("patientBean", patient);
		model.addAttribute("addSuccess", "Le patient à été ajouté avec succès");
		return "getPatient";
	    } else {
		PatientBean patient = new PatientBean();
		model.addAttribute("newPatient", patient);
		model.addAttribute("addError", "Une erreur est survenue, veuillez réessayer");
		return "addPatient";
	    }
	} else {
	    model.addAttribute("addInfosError",
		    "Vérifier à remplir toutes les informations nécessaires, seul l'adresse et le téléphone peuvent ne pas être renseignés");
	    return "addPatient";
	}
    }

    @GetMapping("/patient/delete/confirm/{id}")
    public String deletePatientPage(@PathVariable("id") int id, Model model) {
	PatientBean patient = patientService.getPatientById(id);
	model.addAttribute("patientBean", patient);
	return "deletePatient";
    }

    @GetMapping("/patient/delete/{id}")
    public String deletePatient(@PathVariable("id") int id, Model model) {
	boolean result = patientService.deletePatient(id);
	if (result == true) {
	    boolean deleteNote = noteService.deleteNoteByPatientId(id);
	    if (deleteNote == true) {
		model.addAttribute("deleteSuccess",
			"Le patient et son historique médical ont été supprimés avec succès");
		return "home";
	    } else {
	    model.addAttribute("onlyDeletePatient",
		    "Le patient à été supprimé avec succès, mais une erreur est survenue lors de la tentative de suppression de son historique medical");
	    return "home";
	    }
	} else {
	    PatientBean patient = patientService.getPatientById(id);
	    model.addAttribute("patientBean", patient);
	    model.addAttribute("deleteError",
		    "Une erreur est survenue lors de la tentative de suppression du patient, veuillez réessayer");
	    return "deletePatient";
	}
    }
}
