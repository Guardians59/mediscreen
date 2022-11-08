package com.openclassrooms.msreport.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.openclassrooms.msreport.model.Note;

@Repository
public interface IReportRepository extends MongoRepository<Note, String> {
    
    @Query("{ 'patientId' : ?0, 'note' : { $regex: ?1, $options: 'xi' } }")
    public List<Note> findKeyWords(int patientId, String regex);

}
