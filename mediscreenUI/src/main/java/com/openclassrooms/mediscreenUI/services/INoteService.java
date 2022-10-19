package com.openclassrooms.mediscreenUI.services;

import java.util.List;

import com.openclassrooms.mediscreenUI.beans.NoteBean;

public interface INoteService {
    
    public List<NoteBean> getNoteByPatientId(int patientId);

}
