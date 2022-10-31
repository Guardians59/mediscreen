package com.openclassrooms.msnotes.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ErrorUpdateNote extends RuntimeException {
    
    public ErrorUpdateNote(String s) {
	super(s);
    }

}
