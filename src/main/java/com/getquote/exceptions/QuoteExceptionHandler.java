package com.getquote.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class QuoteExceptionHandler extends RuntimeException {

    private static final String errorString = " It is not possible to provide a quote. ";

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<String>  handleException(Exception ex) {
        log.error("Exception: "+ex.getMessage());
        return new ResponseEntity<String>  ("An error Occurred while generating quote",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { FundsNotAvailable.class })
    public ResponseEntity<String> handleFundsNotAvailableException(FundsNotAvailable ex) {
        log.info("FundsNotAvailable Exception: ",ex.getMessage());
        return new ResponseEntity<String>  ( errorString,HttpStatus.OK );
    }

    @ExceptionHandler(value = { InvalidRequestAmount.class })
    public ResponseEntity<String>  handleInvalidRequestAmount(InvalidRequestAmount ex) {
        log.info("InvalidRequestAmount Exception: ",ex.getMessage());
        return new ResponseEntity<String>   (ex.getMessage(),HttpStatus.OK);
    }

    @ExceptionHandler(value = { GenericException.class })
    public ResponseEntity<String>  handleGenericException(GenericException ex) {
        log.error("InvalidRequestAmount Exception: ",ex.getMessage());
        return  new ResponseEntity<String> (errorString, HttpStatus.BAD_REQUEST);
    }

}


