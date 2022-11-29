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

/**
 * La classe PatientController est le controller qui permet de gérer les URL de gestion
 * des patients de notre application.
 * 
 * @author Dylan
 *
 */
@Controller
public class PatientController {

    @Autowired
    IPatientService patientService;

    @Autowired
    INoteService noteService;

    @Autowired
    IFormValidService formValid;

    /**
     * La méthode getSearchPage nous permet d'afficher la page de recherche d'un patient.
     * 
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue searchPatient.
     */
    @GetMapping("/patient/search")
    public String getSearchPage(Model model) {
	GetPatientModel patientModel = new GetPatientModel();
	model.addAttribute("getPatientModel", patientModel);
	return "searchPatient";
    }

    /**
     * La méthode postSearchPatient permet de rechercher un patient selon son prénom, son nom
     * et sa date de naissance.
     * 
     * @param patient le model des informations nécéssaires à la recherche du 
     * patient(firstName, lastName, birthday).
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la page getPatient si un patient est trouvé, la page searchPatient si aucun
     * patient ne correspond.
     */
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

    /**
     * La méthode getPatient nous permet de récupérer la page affichant les informations d'un patient
     * retrouvé via son prénom, son nom et sa date de naissance.
     * 
     * @param patient le model des informations nécéssaires à la recherche du 
     * patient(firstName, lastName, birthday).
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue getPatient si le patient est retrouvé, la vue searchPatient si aucun
     * patient ne correspond.
     */
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

    /**
     * La méthode getPatientById permet d'afficher les informations d'un patient retrouvé via son id.
     * 
     * @param id l'id du patient.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue getPatient.
     */
    @GetMapping("/patient/getPatient/{id}")
    public String getPatientById(@PathVariable("id") int id, Model model) {
	PatientBean patientBean = new PatientBean();
	patientBean = patientService.getPatientById(id);
	model.addAttribute("patientBean", patientBean);
	return "getPatient";
    }

    /**
     * La méthode getUpdatePatientPage permet d'afficher la page de modification d'un patient
     * via son id.
     * 
     * @param id l'id du patient.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue updatePatient si le patient est bien récupéré, la vue
     * getPatient si une erreur est rencontrée.
     */
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
    
    /**
     * La méthode updatePatient permet de mettre à jour les informations d'un patient via son
     * id.
     * 
     * @param id l'id du patient.
     * @param patientUpdated les nouvelles informations du patient.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue getPatient si la mise à jour est validée, la vue updatePatient
     * si une erreur est rencontrée.
     */
    @PostMapping("/patient/update/{id}")
    public String updatePatient(@PathVariable("id") int id, @ModelAttribute PatientBean patientUpdated, Model model) {
	
	//On vérifie que les champs d'informations obligatoires soient remplis.
	boolean formValidResult = formValid.updatePatientFormValid(patientUpdated);
	
	/**
	 * Si les champs d'informations obligatoires sont valides alors on fait appel
	 * au service de mise à jour du patient, sinon nous renvoyons la page updatePatient
	 * avec un message d'information.
	 */
	if (formValidResult == true) {
	    
	    /*
	     * Si le service de mise à jour renvoit le chiffre un, la mise à jour
	     * est validée, et nous renvoyons la page getPatient.
	     * Le service renvoit le chiffre zéro, aucune modification n'a été apportée,
	     * nous renvoyons la page getPatient.
	     */
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

    /**
     * La méthode getAddPatientPage permet d'afficher la page d'ajout d'un patient.
     * 
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue addPatient.
     */
    @GetMapping("/patient/add")
    public String getAddPatientPage(Model model) {
	PatientBean patient = new PatientBean();
	model.addAttribute("newPatient", patient);
	return "addPatient";
    }

    /**
     * La méthode addPatient permet d'ajouter un patient en base de données.
     * 
     * @param newPatient le model avec les informations du patient.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue getPatient si l'ajout est validée, ou la vue addPatient
     * si une erreur est rencontrée.
     */
    @PostMapping("/patient/add")
    public String addPatient(@ModelAttribute("newPatient") PatientBean newPatient, Model model) {
	
	//On vérifie que les informations obligatoires soient présentes.
	boolean formValidResult = formValid.addPatientFormValid(newPatient);
	
	/*
	 * Si les informations sont valides, alors nous faisons appel au service d'ajout
	 * d'un patient, sinon nous renvoyons la page addPatient avec un message d'information.
	 */
	if (formValidResult == true) {
	    
	    /*
	     * Si le service renvoit true, l'ajout est validé et nous renvoyons la page getPatient.
	     * Le service renvoit false, une erreur est survenue, nous renvoyons la page addPatient
	     * avec un message d'erreur.
	     */
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

    /**
     * La méthode deletePatientPage permet d'afficher la page de confirmation de suppression
     * d'un patient via son id.
     * 
     * @param id l'id du patient.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue deletePatient.
     */
    @GetMapping("/patient/delete/confirm/{id}")
    public String deletePatientPage(@PathVariable("id") int id, Model model) {
	PatientBean patient = patientService.getPatientById(id);
	model.addAttribute("patientBean", patient);
	return "deletePatient";
    }

    /**
     * La méthode deletePatient permet de supprimer un patient via son id.
     * 
     * @param id l'id du patient.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue home si la suppression est confirmée, ou la vue deletePatient
     * si une erreur est survenue.
     */
    @GetMapping("/patient/delete/{id}")
    public String deletePatient(@PathVariable("id") int id, Model model) {
	
	//On fait appel au service de suppression d'un patient.
	boolean result = patientService.deletePatient(id);
	
	/*
	 * Si le service de suppression d'un patient renvoit true, alors nous faisons appel
	 * au service de suppression des notes médicales du patient.
	 * Le service renvoit false, nous renvoyons la page deletePatient avec un
	 * message d'erreur.
	 */
	if (result == true) {
	   /*
	    * Si le service de suppression des notes médicales d'un patient renvoit true
	    * alors nous renvoyons la page home avec un message d'information.
	    * Le service renvoit false, une erreur est survenue lors de la suppression
	    * des notes, nous renvoyons la page home avec un message indiquant que la suppression
	    * du patient est validé, mais pas sa liste de note.
	    */
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
