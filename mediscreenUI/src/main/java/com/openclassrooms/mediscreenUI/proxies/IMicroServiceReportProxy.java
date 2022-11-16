package com.openclassrooms.mediscreenUI.proxies;

import java.util.HashMap;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.openclassrooms.mediscreenUI.beans.ReportBean;

@FeignClient(name = "microservice-report", url = "localhost:8083")
public interface IMicroServiceReportProxy {
    
    @GetMapping(value = "/assess/id/{id}")
    ReportBean getReportById (@PathVariable("id") int patientId, HashMap<String, Object> mapParams);

    @GetMapping(value = "/assess/familyName/{lastName}")
    List<ReportBean> getReportByLastName (@PathVariable("lastName") String lastName, HashMap<String, List<?>> mapParams);
}
