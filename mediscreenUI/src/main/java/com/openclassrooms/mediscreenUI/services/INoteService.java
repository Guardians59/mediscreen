package com.openclassrooms.mediscreenUI.services;

import java.util.List;

import com.openclassrooms.mediscreenUI.beans.NoteBean;

public interface INoteService {
    
    public List<NoteBean> getNoteByPatientId(int patientId);
    
    public boolean addNote(NoteBean note, int patientId, String patientName);
    
    public int updateNoteById(String id, NoteBean noteUpdated);
    
    public NoteBean getNoteById(String id);

}
