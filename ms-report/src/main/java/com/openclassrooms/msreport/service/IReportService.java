package com.openclassrooms.msreport.service;

import java.io.IOException;
import java.util.List;

import com.openclassrooms.msreport.model.Report;

/**
 * L'interface IReportService est le service qui permet de générer les rapports
 * sur le diabète.
 * 
 * @author Dylan
 *
 */
public interface IReportService {

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
    public Report getReportByPatientId(int patientId, String firstName, String lastName, String gender, String birthday)
	    throws IOException;

    /**
     * La méthode getReportByName permet de générer les rapports de diabète d'une
     * famille.
     * 
     * @param lastName      le nom de famille.
     * @param patientIdList les id des patients.
     * @param firstNameList les prénoms des patients.
     * @param genderList    les sexes des patients.
     * @param birthdayList      les dates de naissance des patients.
     * @return List la liste des rapports générés.
     * @throws IOException si une erreur est rencontrée lors de la génération des
     *                     rapports.
     */
    public List<Report> getReportByName(String lastName, List<Integer> patientIdList, List<String> firstNameList,
	    List<String> genderList, List<String> birthdayList) throws IOException;

}
