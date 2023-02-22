package com.ITCube.Booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value= HttpStatus.CONFLICT)
public class ConcurrentBookingCreationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID=1L;

    public ConcurrentBookingCreationException(String message) {
        super(message);
    }
}
