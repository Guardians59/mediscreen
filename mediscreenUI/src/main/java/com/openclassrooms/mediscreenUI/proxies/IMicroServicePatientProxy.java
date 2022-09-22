package com.openclassrooms.mediscreenUI.proxies;

import java.util.HashMap;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.openclassrooms.mediscreenUI.beans.PatientBean;

@FeignClient(name = "microservice-patient", url = "localhost:8081")
public interface IMicroServicePatientProxy {
    
    @GetMapping(value = "/patient/get")
    PatientBean getPatient (HashMap<String, Object> mapParams);

}
