package com.openclassrooms.msreport.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.msreport.model.Report;
import com.openclassrooms.msreport.service.ICountTriggerService;
import com.openclassrooms.msreport.service.IReportService;

@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    ICountTriggerService countTriggerService;

    private static Logger logger = LogManager.getLogger(ReportServiceImpl.class);

    @Override
    public Report getReport(int patientId, String firstName, String lastName, String gender, String date)
	    throws IOException {
	Locale.setDefault(Locale.ENGLISH);
	Report result = new Report();
	int numberOfTrigger = -1;
	numberOfTrigger = countTriggerService.numberOfKeyWords(patientId);
	Date dateFormat;
	Date dateNow = new Date();

	try {
	    dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(date);
	    int age = (int) ((dateNow.getTime() - dateFormat.getTime()) / 31557600000.0);

	    if (numberOfTrigger == 0 || numberOfTrigger == 1) {
		result.setFirstName(firstName);
		result.setLastName(lastName);
		result.setAge(age);
		result.setDiabetesAssessment("None");
	    } else if (age >= 30 && (numberOfTrigger > 1 && numberOfTrigger < 6)) {
		result.setFirstName(firstName);
		result.setLastName(lastName);
		result.setAge(age);
		result.setDiabetesAssessment("Borderline");
	    } else if ((age >= 30 && (numberOfTrigger > 5 && numberOfTrigger < 8))
		    || (age < 30 && gender.equals("M") && (numberOfTrigger > 2 && numberOfTrigger < 5))
		    || (age < 30 && gender.equals("F") && (numberOfTrigger > 3 && numberOfTrigger < 7))) {
		result.setFirstName(firstName);
		result.setLastName(lastName);
		result.setAge(age);
		result.setDiabetesAssessment("In Danger");
	    } else if ((age >= 30 && numberOfTrigger >= 8)
		    || (age < 30 && gender.equals("M") && numberOfTrigger >= 5)
		    || (age < 30 && gender.equals("F") && numberOfTrigger >= 7)) {
		result.setFirstName(firstName);
		result.setLastName(lastName);
		result.setAge(age);
		result.setDiabetesAssessment("Early onset");
	    } else {
		logger.error("An error occured for count the trigger");
	    }

	} catch (ParseException e) {
	    logger.error("Error when parsing birthdate", e);
	}

	return result;
    }

}
