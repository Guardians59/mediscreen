package com.openclassrooms.mspatient.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mspatient.model.Patient;

@Repository
public interface IPatientRepository extends CrudRepository<Patient, Integer> {

    @Query(value = "SELECT * FROM patient WHERE firstname = ?1 AND lastname = ?2 AND birthday = ?3",
	    nativeQuery = true)
    Optional<Patient> findPatient(String firstName, String lastName, String birthday);
    
    @Query(value = "SELECT * FROM patient WHERE lastname = ?1", nativeQuery = true)
    List<Patient> findAllPatientByName(String lastName);

}
