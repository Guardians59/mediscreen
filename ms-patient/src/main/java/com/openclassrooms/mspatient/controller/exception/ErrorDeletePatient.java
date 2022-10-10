package com.openclassrooms.mspatient.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ErrorDeletePatient extends RuntimeException {
    
    public ErrorDeletePatient(String s) {
	super(s);
    }

}
