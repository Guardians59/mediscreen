package com.openclassrooms.msreport.service;

import java.io.IOException;

import com.openclassrooms.msreport.model.Report;

public interface IReportService {

    public Report getReport(int patientId, String firstName, String lastName, String gender, String date)
	    throws IOException;

}
