package com.openclassrooms.mediscreenUI.services;

import java.util.List;

import com.openclassrooms.mediscreenUI.beans.PatientBean;
import com.openclassrooms.mediscreenUI.beans.ReportBean;

public interface IReportService {

    public ReportBean getReportById(int patientId, String firstName, String lastName, String gender, String date);

    public List<ReportBean> getReportByName(String lastName, List<PatientBean> listPatient);
}
