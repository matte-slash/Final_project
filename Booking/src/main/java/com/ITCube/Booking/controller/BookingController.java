package com.ITCube.Booking.controller;

import com.ITCube.Booking.service.BookingService;
import com.ITCube.Booking.util.Interval;
import com.ITCube.Booking.util.UtilObject;
import com.ITCube.Data.model.Booking;
import com.ITCube.Data.model.Desk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Matteo Rosso
 */

@RestController
@RequestMapping(path="/bookings")
@Slf4j
@Validated
public class BookingController {

    private final BookingService service;

    @Autowired
    public BookingController(BookingService service) {
        this.service = service;
    }

    @ResponseStatus(value= HttpStatus.OK)
    @GetMapping
    public List<Booking> findAllBookings(UtilObject ids){
        if(ids==null){
            log.info("Find all bookings");
            return service.findAllBookings();
        }
        if(ids.getIdDesk()!=null){
            log.info("Find all booking by desk "+ ids.getIdDesk());
            return service.findAllBookingByDesk(ids.getIdDesk());
        }else{
            log.info("Find all bookings by user "+ ids.getIdUser());
            return service.findAllBookingsByUser(ids.getIdUser());
        }

    }


    @ResponseStatus(value= HttpStatus.FOUND)
    @GetMapping("/{id}")
    public Booking findBookingById(@PathVariable long id){
        log.info("find booking "+ id);
        return service.findBookingById(id);
    }

    @ResponseStatus(value= HttpStatus.FOUND)
    @GetMapping("/desks")
    public List<Desk> findAllDeskAvailable(Interval interval){
        log.info("find desk available");
        return service.findAllDeskAvailable(interval.getStart(), interval.getEnd());
    }

    @ResponseStatus(value=HttpStatus.CREATED)
    @PostMapping
    public Booking createBooking(@RequestBody @Valid Booking b){
        log.info("create booking");
        return service.createBooking(b);
    }

    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable long id){
        log.info("delete booking "+ id);
        service.deleteBookingById(id);
    }

}
