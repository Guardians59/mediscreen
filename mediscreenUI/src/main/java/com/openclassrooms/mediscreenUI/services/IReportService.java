package com.openclassrooms.mediscreenUI.services;

import java.util.List;

import com.openclassrooms.mediscreenUI.beans.PatientBean;
import com.openclassrooms.mediscreenUI.beans.ReportBean;

/**
 * L'interface IReportService est le service de gestion des rapports de diabète de
 * notre application.
 * 
 * @author Dylan
 *
 */
public interface IReportService {

    /**
     * La méthode getReportById permet de généré un rapport de diabète sur un patient via
     * son id.
     * 
     * @param patientId l'id du patient.
     * @param firstName le prénom du patient.
     * @param lastName le nom du patient.
     * @param gender le sexe du patient.
     * @param birthday la date de naissance du patient.
     * @return ReportBean le rapport généré.
     */
    public ReportBean getReportById(int patientId, String firstName, String lastName, String gender, String birthday);

    /**
     * La méthode getReportByName permet de généré une liste de rapport de diabète familiaux.
     * 
     * @param lastName le nom de famille recherché.
     * @param listPatient la liste des patients correspondant.
     * @return List ReportBean les rapports générés.
     */
    public List<ReportBean> getReportByName(String lastName, List<PatientBean> listPatient);
}
