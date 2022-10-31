package com.openclassrooms.msnotes.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ErrorGetNote extends RuntimeException {

    public ErrorGetNote(String s) {
	super(s);
    }
}
