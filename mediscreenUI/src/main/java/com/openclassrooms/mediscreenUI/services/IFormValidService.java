package com.openclassrooms.mediscreenUI.services;

import com.openclassrooms.mediscreenUI.beans.NoteBean;
import com.openclassrooms.mediscreenUI.beans.PatientBean;

public interface IFormValidService {
    
    public boolean updatePatientFormValid (PatientBean patientUpdated);
    
    public boolean addPatientFormValid (PatientBean newPatient);
    
    public boolean addNoteFormValid (NoteBean newNote);

}
