package com.openclassrooms.msreport.service;

import java.io.IOException;
import java.util.List;

import com.openclassrooms.msreport.model.Report;

public interface IReportService {

    public Report getReportById(int patientId, String firstName, String lastName, String gender, String date)
	    throws IOException;

    public List<Report> getReportByName(String lastName, List<Integer> patientId, List<String> firstName,
	    List<String> gender, List<String> date) throws IOException;

}
