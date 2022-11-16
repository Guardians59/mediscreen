package com.openclassrooms.msreport.service;

import java.io.IOException;
import java.util.HashMap;

public interface ICountTriggerService {

    public int numberOfTriggerById(int patientId) throws IOException;

    public HashMap<Integer, Integer> numberOfTriggerByName(String lastName) throws IOException;

}
