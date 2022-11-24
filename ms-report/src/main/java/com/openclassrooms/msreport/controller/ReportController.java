package com.openclassrooms.msreport.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.msreport.model.Report;
import com.openclassrooms.msreport.service.IReportService;

import io.swagger.annotations.ApiOperation;

/**
 * La classe ReportController est le controller qui permet de gérer les URL du
 * micro-service de gestion des rapports sur le diabète.
 * 
 * @author Dylan
 *
 */
@RestController
public class ReportController {

    @Autowired
    IReportService reportService;

    /**
     * La méthode getReportByPatientId permet de générer le rapport de diabète d'un patient
     * via son id.
     * 
     * @param patientId l'id du patient.
     * @param firstName le prénom du patient.
     * @param lastName  le nom du patient.
     * @param gender    le sexe du patient.
     * @param birthday      la date de naissance du patient.
     * @return report le rapport de diabète.
     * @throws IOException si une erreur est rencontrée lors de la génération du
     *                     rapport.
     */
    @ApiOperation(value = "Recupere le rapport de diabete via l'id du patient.")
    @GetMapping("/assess/id/{id}")
    public Report getReportByPatientId(@PathVariable("id") int patientId, @RequestParam String firstName,
	    @RequestParam String lastName, @RequestParam String gender, @RequestParam String birthday) throws IOException {
	Report result = new Report();
	result = reportService.getReportByPatientId(patientId, firstName, lastName, gender, birthday);
	return result;
    }

    /**
     * La méthode getReportByName permet de générer les rapports de diabète d'une
     * famille.
     * 
     * @param lastName      le nom de famille des patients.
     * @param patientIdList les id des patients.
     * @param firstNameList les prénoms des patients.
     * @param genderList    les sexes des patients.
     * @param birthdayList      les dates de naissance des patients.
     * @return List  la liste des rapports générés.
     * @throws IOException si une erreur est rencontrée lors de la génération des rapports.
     */
    @ApiOperation(value = "Recupere les rapports de diabete familiaux via le nom de famille.")
    @GetMapping("/assess/familyName/{lastName}")
    public List<Report> getReportByName(@PathVariable("lastName") String lastName,
	    @RequestParam List<Integer> patientIdList, @RequestParam List<String> firstNameList,
	    @RequestParam List<String> genderList, @RequestParam List<String> birthdayList) throws IOException {
	List<Report> result = new ArrayList<>();
	result = reportService.getReportByName(lastName, patientIdList, firstNameList, genderList, birthdayList);
	return result;

    }

}
