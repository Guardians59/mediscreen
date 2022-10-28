package com.openclassrooms.msnotes.service;

import java.util.List;

import com.openclassrooms.msnotes.model.Note;

public interface INoteService {
    
    public List<Note> getNoteByPatientId(int patientId);
    
    public boolean addNoteByPatientId(Note newNote, int patientId, String patientName);
    
    public boolean updateNote(String id, Note noteUpdated);
    
    public Note getNoteById(String id);

}
