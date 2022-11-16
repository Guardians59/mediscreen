package com.openclassrooms.mspatient.service;

import java.util.List;

import com.openclassrooms.mspatient.model.Patient;

public interface IPatientService {
    
    public boolean addPatient(Patient patient);
    
    public Patient getPatient(String firstName, String lastName, String birthday);
    
    public Patient getPatientById(int id);
    
    public boolean updatePatient(int id, Patient patientUpdated);
    
    public boolean deletePatient(int id);

    public List<Patient> getAllByName(String lastName);
}
