package com.openclassrooms.msreport.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.msreport.model.Report;
import com.openclassrooms.msreport.service.ICountTriggerService;
import com.openclassrooms.msreport.service.IReportService;

/**
 * La classe ReportServiceImpl est l'implémentation de l'interface
 * IReportService.
 * 
 * @see IReportService
 * @author Dylan
 *
 */
@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    ICountTriggerService countTriggerService;

    private static Logger logger = LogManager.getLogger(ReportServiceImpl.class);
    private HashMap<Integer, Integer> mapPatient = new HashMap<>();
    private int index;

    @Override
    public Report getReportByPatientId(int patientId, String firstName, String lastName, String gender, String birthday)
	    throws IOException {
	Locale.setDefault(Locale.ENGLISH);
	Report result = new Report();
	int numberOfTrigger = -1;
	/*
	 * On calcul le nombre d'occurence retrouvées dans les notes médicales du
	 * patient.
	 */
	numberOfTrigger = countTriggerService.numberOfTriggerById(patientId);
	Date dateFormat;
	Date dateNow = new Date();
	logger.debug("Search the risk level for the patient with id " + patientId);

	/*
	 * On initie un try catch afin de formater la date de naissance du patient, pour
	 * pouvoir calculer son age. Ensuite nous recherchons dans quelle catégorie se
	 * retrouve le patient selon son âge, son sexe et le nombre d'occurence présente
	 * dans ses notes médicales.
	 */
	try {
	    dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
	    int age = (int) ((dateNow.getTime() - dateFormat.getTime()) / 31557600000.0);

	    if (numberOfTrigger == -1) {
		logger.error("An error occured for count the trigger");
	    } else if ((numberOfTrigger == 0 || numberOfTrigger == 1)
		    || (age < 30 && gender.equals("M") && (numberOfTrigger <= 2))
		    || (age < 30 && gender.equals("F") && (numberOfTrigger <= 3))) {
		result.setFirstName(firstName);
		result.setLastName(lastName);
		result.setAge(age);
		result.setDiabetesAssessment("None");
		logger.info("The patient risk level is none");
	    } else if (age >= 30 && (numberOfTrigger > 1 && numberOfTrigger < 6)) {
		result.setFirstName(firstName);
		result.setLastName(lastName);
		result.setAge(age);
		result.setDiabetesAssessment("Borderline");
		logger.info("The patient risk level is borderline");
	    } else if ((age >= 30 && (numberOfTrigger > 5 && numberOfTrigger < 8))
		    || (age < 30 && gender.equals("M") && (numberOfTrigger > 2 && numberOfTrigger < 5))
		    || (age < 30 && gender.equals("F") && (numberOfTrigger > 3 && numberOfTrigger < 7))) {
		result.setFirstName(firstName);
		result.setLastName(lastName);
		result.setAge(age);
		result.setDiabetesAssessment("In Danger");
		logger.info("The patient risk level is in danger");
	    } else if ((age >= 30 && numberOfTrigger >= 8) || (age < 30 && gender.equals("M") && numberOfTrigger >= 5)
		    || (age < 30 && gender.equals("F") && numberOfTrigger >= 7)) {
		result.setFirstName(firstName);
		result.setLastName(lastName);
		result.setAge(age);
		result.setDiabetesAssessment("Early onset");
		logger.info("The patient risk level is early onset");
	    } else {
		logger.info("No informations for the risk level with " + numberOfTrigger + " trigger and " + age
			+ " years old");
	    }

	} catch (ParseException e) {
	    logger.error("Error when parsing birthdate", e);
	}

	return result;
    }

    @Override
    public List<Report> getReportByName(String lastName, List<Integer> patientIdList, List<String> firstNameList,
	    List<String> genderList, List<String> birthdayList) throws IOException {
	Locale.setDefault(Locale.ENGLISH);
	List<Report> result = new ArrayList<>();
	index = 0;
	/*
	 * On récupére la map contenant les id des patients et le nombre d'occurence
	 * correspondante. On initie une boucle forEach sur les id, afin de récupérer
	 * pour chaque patient le nombre d'occurence retrouvées.
	 */
	mapPatient = countTriggerService.numberOfTriggerByName(lastName);
	Date dateNow = new Date();
	logger.debug("Search the risk level for the patient with familyName " + lastName);

	patientIdList.forEach(id -> {
	    Report report = new Report();
	    int numberOfTrigger = -1;
	    Date dateFormat = new Date();
	    if (mapPatient.containsKey(id)) {
		numberOfTrigger = mapPatient.get(id).intValue();
		String firstName = firstNameList.get(index);
		String gender = genderList.get(index);
		String date = birthdayList.get(index);

		/*
		 * On initie un try catch afin de formater la date de naissance du patient, pour
		 * pouvoir calculer son age. Ensuite nous recherchons dans quelle catégorie se
		 * retrouve le patient selon son âge, son sexe et le nombre d'occurence présente
		 * dans ses notes médicales.
		 */
		try {
		    dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		    int age = (int) ((dateNow.getTime() - dateFormat.getTime()) / 31557600000.0);

		    if (numberOfTrigger == -1) {
			logger.error("An error occured for count the trigger");
		    } else if ((numberOfTrigger == 0 || numberOfTrigger == 1)
			    || (age < 30 && gender.equals("M") && (numberOfTrigger <= 2))
			    || (age < 30 && gender.equals("F") && (numberOfTrigger <= 3))) {
			report.setFirstName(firstName);
			report.setLastName(lastName);
			report.setAge(age);
			report.setDiabetesAssessment("None");
			logger.info("The patient risk level is none");
			result.add(report);
		    } else if (age >= 30 && (numberOfTrigger > 1 && numberOfTrigger < 6)) {
			report.setFirstName(firstName);
			report.setLastName(lastName);
			report.setAge(age);
			report.setDiabetesAssessment("Borderline");
			logger.info("The patient risk level is borderline");
			result.add(report);
		    } else if ((age >= 30 && (numberOfTrigger > 5 && numberOfTrigger < 8))
			    || (age < 30 && gender.equals("M") && (numberOfTrigger > 2 && numberOfTrigger < 5))
			    || (age < 30 && gender.equals("F") && (numberOfTrigger > 3 && numberOfTrigger < 7))) {
			report.setFirstName(firstName);
			report.setLastName(lastName);
			report.setAge(age);
			report.setDiabetesAssessment("In Danger");
			logger.info("The patient risk level is in danger");
			result.add(report);
		    } else if ((age >= 30 && numberOfTrigger >= 8)
			    || (age < 30 && gender.equals("M") && numberOfTrigger >= 5)
			    || (age < 30 && gender.equals("F") && numberOfTrigger >= 7)) {
			report.setFirstName(firstName);
			report.setLastName(lastName);
			report.setAge(age);
			report.setDiabetesAssessment("Early onset");
			logger.info("The patient risk level is early onset");
			result.add(report);
		    } else {
			logger.info("No informations for the risk level with " + numberOfTrigger + " trigger and " + age
				+ " years old");
		    }

		} catch (ParseException e) {
		    logger.error("Error when parsing birthdate", e);
		}

	    }
	    // On ajoute un à l'index permettant de retrouver les informations du prochain
	    // patient dans les listes.
	    index++;
	});

	return result;
    }

}
