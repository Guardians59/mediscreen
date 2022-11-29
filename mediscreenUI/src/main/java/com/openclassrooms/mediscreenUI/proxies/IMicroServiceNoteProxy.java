package com.openclassrooms.mediscreenUI.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.openclassrooms.mediscreenUI.beans.NoteBean;

/**
 * L'interface IMicroServiceNoteProxy est le service qui nous permet d'utiliser le
 * microService Note depuis feign.
 * 
 * @author Dylan
 *
 */
@FeignClient(name = "microservice-note", url = "localhost:8082")
public interface IMicroServiceNoteProxy {

    /**
     * La méthode getNoteByPatientId permet de récupérer la liste de note médicales
     * d'un patient via son id.
     * 
     * @param patientId l'id du patient.
     * @return List des notes médicales.
     */
    @GetMapping(value = "/note/getByPatientId/{id}")
    List<NoteBean> getNoteByPatientId(@PathVariable("id") int patientId);

    /**
     * La méthode addNote permet d'ajouter une note en base de données.
     * 
     * @param patientId l'id du patient.
     * @param patientName le nom du patient.
     * @param note la note à ajouter.
     * @return ResponseEntity le status 201 si l'ajout est validé, le status 404 si une erreur
     * est survenue.
     */
    @PostMapping(value = "/note/add/{id}")
    ResponseEntity<?> addNote(@PathVariable("id") int patientId, @RequestParam String patientName,
	    @RequestBody NoteBean note);

    /**
     * La méthode updateNoteById permet de mettre à jour une note médicale via son id.
     * 
     * @param id l'id de la note.
     * @param noteUpdated les informations mises à jour de la note.
     * @return ResponseEntity le status 200 si la mise à jour est validée, le status 404 si une
     * erreur est survenue.
     */
    @PutMapping(value = "/note/update/{id}")
    ResponseEntity<?> updateNoteById(@PathVariable("id") String id, @RequestBody NoteBean noteUpdated);

    /**
     * La méthode getNoteById permet de récupérer une note via son id.
     * 
     * @param id l'id de la note.
     * @return NoteBean la note récupérée.
     */
    @GetMapping(value = "/note/get/{id}")
    NoteBean getNoteById(@PathVariable("id") String id);
    
    /**
     * La méthode deleteNoteByPatientId permet de supprimer les notes médicales d'un patient
     * via son id.
     * 
     * @param id l'id du patient.
     * @return ResponseEntity le status 200 si la suppression est validée, le status 404 si une
     * erreur est survenue.
     */
    @DeleteMapping(value = "/note/patientId/{id}")
    ResponseEntity<?> deleteNoteByPatientId(@PathVariable("id") int id);

}
