package com.getquote.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
public class GenericException extends RuntimeException {

    private static final String errorString = " It is not possible to provide a quote. ";

    public GenericException() {
        super(errorString);
    }

    public GenericException(String message) {
        super(errorString + message );
    }

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericException(Throwable cause) {
        super(cause);
    }

    public GenericException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
