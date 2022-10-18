package com.openclassrooms.msnotes.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.openclassrooms.msnotes.model.Note;

@Repository
public interface INoteRepository extends MongoRepository<Note, String> {
    
    @Query("{ 'patientId' : '?0' }")
    public List<Note> findByPatientId(int patientId);

}
