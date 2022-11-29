package com.openclassrooms.mediscreenUI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.openclassrooms.mediscreenUI.beans.NoteBean;
import com.openclassrooms.mediscreenUI.beans.PatientBean;
import com.openclassrooms.mediscreenUI.services.IFormValidService;
import com.openclassrooms.mediscreenUI.services.INoteService;
import com.openclassrooms.mediscreenUI.services.IPatientService;

/**
 * La classe NoteController est le controller qui permet de gérer les URL de gestion de
 * note de notre application.
 * 
 * @author Dylan
 *
 */
@Controller
public class NoteController {
    
    @Autowired
    INoteService noteService;
    
    @Autowired
    IPatientService patientService;
    
    @Autowired
    IFormValidService formValidService;
    
    /**
     * La méthode getNoteByPatientId nous permet d'afficher la liste des notes médicales
     * d'un patient via son id.
     * 
     * @param id l'id du patient.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue historiqueNote soit l'historique des notes, ou la vue
     * noHistoriqueNote si la liste est vide.
     */
    @GetMapping("/note/getByPatientId/{id}")
    public String getNoteByPatientId(@PathVariable("id") int id, Model model) {
	List<NoteBean> listNote = noteService.getNoteByPatientId(id);
	if(listNote.isEmpty()) {
	    PatientBean patient = patientService.getPatientById(id);
	    model.addAttribute("patientBean", patient);
	    return "noHistoriqueNote";
	} else {
	    PatientBean patient = patientService.getPatientById(id);
	    model.addAttribute("patientBean", patient);
	    model.addAttribute("noteBean", listNote);
	    model.addAttribute("name", listNote.get(0));
	    return "historiqueNote";
       }
    }
    
    /**
     * La méthode addNotePage nous permet d'afficher la page d'ajout de note.
     * 
     * @param id l'id du patient.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue addNote.
     */
    @GetMapping("/note/add/{id}")
    public String addNotePage(@PathVariable("id") int id, Model model) {
	PatientBean patient = new PatientBean();
	patient = patientService.getPatientById(id);
	model.addAttribute("patientBean", patient);
	NoteBean note = new NoteBean();
	model.addAttribute("newNote", note);
	return "addNote";
    }
    
    /**
     * La méthode addNote permet d'ajouter une note médicale sur un patient.
     * 
     * @param id l'id du patient.
     * @param newNote la note à ajouter.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue historiqueNote si l'ajout à été effectué avec succès,
     * ou la vue addNote si une erreur est survenue.
     */
    @PostMapping("/note/add/{id}")
    public String addNote(@PathVariable("id") int id, @ModelAttribute NoteBean newNote, Model model) {
	
	//On vérifie que les champs d'informations obligatoires soient bien remplis.
	boolean formValidResult = formValidService.addNoteFormValid(newNote);
	PatientBean patient = new PatientBean();
	patient = patientService.getPatientById(id);
	
	/*
	 * Si les informations obligatoires sont présentes, alors nous faisons appel
	 * au service d'ajout de note, auquel cas nous renvoyons la page addNote en indiquant
	 * qu'ils manquent des informations nécéssaires à l'ajout d'une note.
	 */
	if (formValidResult == true) {
	    
	    /*
	     * Si le service d'ajout de note renvoit true nous retournons la page
	     * d'historique des notes, si false est renvoyé alors ce sera la page addNote
	     * avec un message d'erreur qui sera retourné.
	     */
	    boolean result = noteService.addNote(newNote, id, patient.getLastName());
	    if (result == true) {
		List<NoteBean> listNote = noteService.getNoteByPatientId(id);
		model.addAttribute("patientBean", patient);
		model.addAttribute("noteBean", listNote);
		model.addAttribute("name", listNote.get(0));
		model.addAttribute("addSuccess",
			    "Note médicale ajoutée avec succès");
		return "historiqueNote";
	    } else {
		model.addAttribute("patientBean", patient);
		model.addAttribute("newNote", newNote);
		model.addAttribute("addError",
			    "Une erreur est survenue, veuillez réessayer");
		return "addNote";
	    }
	}
	model.addAttribute("patientBean", patient);
	model.addAttribute("newNote", newNote);
	model.addAttribute("addError",
		    "Vérifier à remplir toutes les informations nécessaires");
	return "addNote";
    }
    
    /**
     * La méthode updateNoteByIdPage nous permet d'afficher la page de modifications
     * d'une note.
     * 
     * @param id l'id de la note.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue updateNote si la note est bien récupérée pour être affichée,
     * ou la vue home si une erreur est survenue.
     */
    @GetMapping("/note/update/{id}")
    public String updateNoteByIdPage(@PathVariable("id") String id, Model model) {
	NoteBean noteBean = new NoteBean();
	noteBean = noteService.getNoteById(id);
	if(noteBean.getNote() != null) {
	    model.addAttribute("noteBean", noteBean);
	    PatientBean patient = new PatientBean();
	    patient = patientService.getPatientById(noteBean.getPatientId());
	    model.addAttribute("patientBean", patient);
	    return "updateNote";
	} else {
	    model.addAttribute("updateError",
		    "Une erreur est survenue, veuillez réessayer");
	    return "home";
	}
    }
    
    /**
     * La méthode updateNoteById nous permet de modifier une note médicale via son id.
     * 
     * @param id l'id de la note.
     * @param noteUpdated la note modifiée.
     * @param model pour définir les attributs nécéssaires à la vue.
     * @return String la vue historiqueNote si la mise à jour est validée, ou si la note
     * n'a pas été modifiée, la vue updateNote si une erreur est rencontrée ou si le
     * champs du texte de la note reste vide.
     */
    @PostMapping("/note/update/{id}")
    public String updateNoteById(@PathVariable("id") String id, @ModelAttribute NoteBean noteUpdated, Model model) {
	
	//On vérifie que le champ du texte de la note ne soit pas vide.
	boolean formResult = formValidService.updateNoteFormValid(noteUpdated);
	int idPatient = noteService.getNoteById(id).getPatientId();
	
	/*
	 * Si le texte de la note est présent nous faisons appel au service de
	 * modififaction, sinon on renvoit la vue updateNote.
	 */
	if(formResult == true) {
	    
	    /*
	     * Si le service de modification nous renvoit le chiffre un, la modification
	     * est validée dans ce cas nous renvoyons la vue historiqueNote.
	     * Le service de modification nous renvoit le chiffre zéro, le texte de la
	     * note n'a pas été modifié, nous renvoyons la vue historiqueNote.
	     * Le service renvoit un autre chiffre, alors une erreur est survenue, nous
	     * renvoyons la vue updateNote avec un message d'erreur.
	     */
	    int result = noteService.updateNoteById(id, noteUpdated);
	    if(result == 1) {
		List<NoteBean> listNote = noteService.getNoteByPatientId(idPatient);
		PatientBean patient = new PatientBean();
		patient = patientService.getPatientById(idPatient);
		model.addAttribute("patientBean", patient);
		model.addAttribute("noteBean", listNote);
		model.addAttribute("name", listNote.get(0));
		model.addAttribute("updateSuccess",
			    "Note médicale mise à jour avec succès");
		return "historiqueNote";
	    } else if(result == 0) {
		List<NoteBean> listNote = noteService.getNoteByPatientId(idPatient);
		PatientBean patient = new PatientBean();
		patient = patientService.getPatientById(idPatient);
		model.addAttribute("patientBean", patient);
		model.addAttribute("noteBean", listNote);
		model.addAttribute("name", listNote.get(0));
		model.addAttribute("noUpdate",
			    "Aucune information de la note n'a été modifié");
		return "historiqueNote";
	    } else {
		PatientBean patient = new PatientBean();
		patient = patientService.getPatientById(idPatient);
		model.addAttribute("patientBean", patient);
		model.addAttribute("noteBean", noteUpdated);
		model.addAttribute("updateError",
			    "Une erreur est survenue, veuillez réessayer");
		return "updateNote";
	    }
	} else {
	    PatientBean patient = new PatientBean();
	    patient = patientService.getPatientById(idPatient);
	    model.addAttribute("patientBean", patient);
	    model.addAttribute("noteBean", noteUpdated);
	    model.addAttribute("updateInfosError",
		    "Vérifier à remplir toutes les informations nécessaires");
	    return "updateNote";
	}
    }

}
