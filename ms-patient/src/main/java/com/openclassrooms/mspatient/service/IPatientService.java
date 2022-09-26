package com.openclassrooms.mspatient.service;

import com.openclassrooms.mspatient.model.Patient;

public interface IPatientService {
    
    public boolean addPatient(Patient patient);
    
    public Patient getPatient(String firstName, String lastName, String birthday);
    
    public boolean updatePatient(int id, Patient patientUpdated);

}
