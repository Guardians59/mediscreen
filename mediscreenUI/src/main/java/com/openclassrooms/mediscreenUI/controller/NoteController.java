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

@Controller
public class NoteController {
    
    @Autowired
    INoteService noteService;
    
    @Autowired
    IPatientService patientService;
    
    @Autowired
    IFormValidService formValidService;
    
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
    
    @GetMapping("/note/add/{id}")
    public String addNotePage(@PathVariable("id") int id, Model model) {
	PatientBean patient = new PatientBean();
	patient = patientService.getPatientById(id);
	model.addAttribute("patientBean", patient);
	NoteBean note = new NoteBean();
	model.addAttribute("newNote", note);
	return "addNote";
    }
    
    @PostMapping("/note/add/{id}")
    public String addNote(@PathVariable("id") int id, @ModelAttribute NoteBean newNote, Model model) {
	boolean formValidResult = formValidService.addNoteFormValid(newNote);
	if (formValidResult == true) {
	    boolean result = noteService.addNote(newNote, id);
	    if (result == true) {
		List<NoteBean> listNote = noteService.getNoteByPatientId(id);
		PatientBean patient = new PatientBean();
		patient = patientService.getPatientById(id);
		model.addAttribute("patientBean", patient);
		model.addAttribute("noteBean", listNote);
		model.addAttribute("name", listNote.get(0));
		model.addAttribute("addSuccess",
			    "Note médicale ajoutée avec succès");
		return "historiqueNote";
	    } else {
		PatientBean patient = new PatientBean();
		patient = patientService.getPatientById(id);
		model.addAttribute("patientBean", patient);
		model.addAttribute("newNote", newNote);
		model.addAttribute("addError",
			    "Une erreur est survenue, veuillez réessayer");
		return "addNote";
	    }
	}
	PatientBean patient = new PatientBean();
	patient = patientService.getPatientById(id);
	model.addAttribute("patientBean", patient);
	model.addAttribute("newNote", newNote);
	model.addAttribute("addError",
		    "Vérifier à remplir toutes les informations nécessaires");
	return "addNote";
    }

}
