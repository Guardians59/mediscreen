package com.openclassrooms.mediscreenUI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.openclassrooms.mediscreenUI.beans.NoteBean;
import com.openclassrooms.mediscreenUI.beans.PatientBean;
import com.openclassrooms.mediscreenUI.services.INoteService;
import com.openclassrooms.mediscreenUI.services.IPatientService;

@Controller
public class NoteController {
    
    @Autowired
    INoteService noteService;
    
    @Autowired
    IPatientService patientService;
    
    @GetMapping("/note/getByPatientId/{id}")
    public String getNoteByPatientId(@PathVariable("id") int id, Model model) {
	List<NoteBean> listNote = noteService.getNoteByPatientId(id);
	if(listNote.isEmpty()) {
	    PatientBean patient = patientService.getPatientById(id);
	    model.addAttribute("patientBean", patient);
	    return "noHistoriqueNote";
	} else {
	model.addAttribute("noteBean", listNote);
	model.addAttribute("name", listNote.get(0));
	return "historiqueNote";
       }
    }

}
