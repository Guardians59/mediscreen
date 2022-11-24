package com.openclassrooms.mediscreenUI.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.openclassrooms.mediscreenUI.beans.ReportBean;

@FeignClient(name = "microservice-report", url = "localhost:8083")
public interface IMicroServiceReportProxy {

    @GetMapping(value = "/assess/id/{id}")
    ReportBean getReportByPatientId(@PathVariable("id") int patientId, @RequestParam String firstName,
	    @RequestParam String lastName, @RequestParam String gender, @RequestParam String birthday);

    @GetMapping(value = "/assess/familyName/{lastName}")
    List<ReportBean> getReportByLastName(@PathVariable("lastName") String lastName,
	    @RequestParam List<Integer> patientIdList, @RequestParam List<String> firstNameList,
	    @RequestParam List<String> genderList, @RequestParam List<String> birthdayList);
}
