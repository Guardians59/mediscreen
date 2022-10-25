package com.openclassrooms.mediscreenUI.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.openclassrooms.mediscreenUI.beans.NoteBean;

@FeignClient(name = "microservice-note", url = "localhost:8082")
public interface IMicroServiceNoteProxy {
    
    @GetMapping(value = "/note/getByPatientId/{id}")
    List<NoteBean> getNoteByPatientId (@PathVariable("id") int patientId);
    
    @PostMapping(value = "/note/add/{id}")
    ResponseEntity<?> addNote (@PathVariable("id") int patientId, @RequestBody NoteBean note);

}
