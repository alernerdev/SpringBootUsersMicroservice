package com.pragmaticbitbucket.app.ws.exceptions;

import com.pragmaticbitbucket.app.ws.ui.model.response.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice // makes it listen for all Exceptions thrown in controllers
public class AppExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        // can return the entire exception with stack trace
        // return new ResponseEntity<>(ex, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        String errorMessage = ex.getLocalizedMessage();
        if (errorMessage == null)
            errorMessage = ex.toString();

        ErrorMessage em = new ErrorMessage(new Date(), errorMessage);
        return new ResponseEntity<>(em, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // mu
    @ExceptionHandler(value={NullPointerException.class, UserServiceException.class})
    public ResponseEntity<Object> handleSpecificException(Exception ex, WebRequest request) {
        String errorMessage = ex.getLocalizedMessage();
        if (errorMessage == null)
            errorMessage = ex.toString();

        ErrorMessage em = new ErrorMessage(new Date(), errorMessage);
        return new ResponseEntity<>(em, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
