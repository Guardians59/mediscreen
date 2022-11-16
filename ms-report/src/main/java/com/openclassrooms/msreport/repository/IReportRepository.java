package com.openclassrooms.msreport.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.openclassrooms.msreport.model.Note;

@Repository
public interface IReportRepository extends MongoRepository<Note, String> {
    
    @Query("{ 'patientId' : ?0, 'note' : { $regex: ?1, $options: 'i' } }")
    public List<Note> findNumberNoteWithTriggerById(int patientId, String regex);
    
    @Query("{ 'patient' : ?0, 'note' : { $regex: ?1, $options: 'i' } }")
    public List<Note> findNumberNoteWithTriggerByName(String patientLastName, String regex);
    
    @Query("{ 'patient' : ?0 }")
    public List<Note> findAllByName(String patientLastName);

}
