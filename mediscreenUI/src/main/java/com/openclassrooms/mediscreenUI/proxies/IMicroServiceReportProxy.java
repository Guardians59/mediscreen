package com.openclassrooms.mediscreenUI.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.openclassrooms.mediscreenUI.beans.ReportBean;

/**
 * L'interface IMicroServiceReportProxy est le service qui nous permet d'utiliser le
 * microService Report depuis feign.
 * 
 * @author Dylan
 *
 */
@FeignClient(name = "microservice-report", url = "localhost:8083")
public interface IMicroServiceReportProxy {

    /**
     * La méthode getReportByPatientId permet de générer le rapport de diabète du patient
     * via son id.
     * 
     * @param patientId l'id du patient.
     * @param firstName le prénom du patient.
     * @param lastName le nom du patient.
     * @param gender le sexe du patient.
     * @param birthday la date de naissance du patient.
     * @return ReportBean le rapport généré.
     */
    @GetMapping(value = "/assess/id/{id}")
    ReportBean getReportByPatientId(@PathVariable("id") int patientId, @RequestParam String firstName,
	    @RequestParam String lastName, @RequestParam String gender, @RequestParam String birthday);

    /**
     * La méthode getReportByLastName permet de générer les rapports de diabète familiaux.
     * 
     * @param lastName le nom de famille.
     * @param patientIdList la liste des id des patients.
     * @param firstNameList la liste des prénoms des patients.
     * @param genderList la liste des sexes des patients.
     * @param birthdayList la liste des dates de naissances.
     * @return List des rapports générés pour chaque patient.
     */
    @GetMapping(value = "/assess/familyName/{lastName}")
    List<ReportBean> getReportByLastName(@PathVariable("lastName") String lastName,
	    @RequestParam List<Integer> patientIdList, @RequestParam List<String> firstNameList,
	    @RequestParam List<String> genderList, @RequestParam List<String> birthdayList);
}
