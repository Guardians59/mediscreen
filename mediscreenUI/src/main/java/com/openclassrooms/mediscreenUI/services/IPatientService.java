package com.openclassrooms.mediscreenUI.services;

import java.util.List;

import com.openclassrooms.mediscreenUI.beans.PatientBean;

public interface IPatientService {
    
    public PatientBean getPatient(String firstName, String lastName, String birthday);
    
    public PatientBean getPatientById(int id);
    
    public int updatePatient(int id, PatientBean patientUpdated);
    
    public boolean addPatient(PatientBean newPatient);
    
    public boolean deletePatient(int id);
    
    public List<PatientBean> getAllByName(String lastName);

}
