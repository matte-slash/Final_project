package com.ITCube.Booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class BookingNotFoundException extends RuntimeException {
    private static final long serialVersionUID=1L;

    public BookingNotFoundException(String message) {
        super(message);
    }
}
