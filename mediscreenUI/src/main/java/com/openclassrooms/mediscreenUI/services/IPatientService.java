package com.openclassrooms.mediscreenUI.services;

import com.openclassrooms.mediscreenUI.beans.PatientBean;

public interface IPatientService {
    
    public PatientBean getPatient(String firstName, String lastName, String birthday);
    
    public PatientBean getPatientById(int id);
    
    public int updatePatient (int id, PatientBean patientUpdated);

}
