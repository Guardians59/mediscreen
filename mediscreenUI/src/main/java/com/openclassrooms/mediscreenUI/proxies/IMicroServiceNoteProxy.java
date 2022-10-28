package com.openclassrooms.mediscreenUI.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.openclassrooms.mediscreenUI.beans.NoteBean;

@FeignClient(name = "microservice-note", url = "localhost:8082")
public interface IMicroServiceNoteProxy {

    @GetMapping(value = "/note/getByPatientId/{id}")
    List<NoteBean> getNoteByPatientId(@PathVariable("id") int patientId);

    @PostMapping(value = "/note/add/{id}")
    ResponseEntity<?> addNote(@PathVariable("id") int patientId, @RequestParam String patientName,
	    @RequestBody NoteBean note);

    @PutMapping(value = "/note/update/{id}")
    ResponseEntity<?> updateNoteById(@PathVariable("id") String id, @RequestBody NoteBean noteUpdated);

    @GetMapping(value = "/note/get/{id}")
    NoteBean getNoteById(@PathVariable("id") String id);

}
