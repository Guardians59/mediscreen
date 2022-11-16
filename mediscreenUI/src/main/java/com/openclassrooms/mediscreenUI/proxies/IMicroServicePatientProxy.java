package com.openclassrooms.mediscreenUI.proxies;

import java.util.HashMap;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.openclassrooms.mediscreenUI.beans.PatientBean;

@FeignClient(name = "microservice-patient", url = "localhost:8081")
public interface IMicroServicePatientProxy {
    
    @GetMapping(value = "/patient/get")
    PatientBean getPatient (HashMap<String, Object> mapParams);
    
    @GetMapping(value = "/patient/get/{id}")
    PatientBean getPatientById (@PathVariable("id") int id);
    
    @PutMapping(value = "/patient/update/{id}")
    void updatePatient (@PathVariable("id") int id, @RequestBody PatientBean patientUpdated);
    
    @PostMapping(value = "/patient/add") 
    ResponseEntity<?> addPatient (@RequestBody PatientBean newPatient);
    
    @DeleteMapping(value = "/patient/delete/{id}")
    ResponseEntity<?> deletePatient (@PathVariable("id") int id);
    
    @GetMapping(value = "/patient/getAllByName/{lastName}")
    List<PatientBean> getAllByName (@PathVariable("lastName") String lastName);
} 
