package com.ITCube.Desk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Matteo Rosso
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class IllegalDateTimeException extends RuntimeException{
    private static final long serialVersionUID=1L;

    public IllegalDateTimeException(String message) {
        super(message);
    }
}
