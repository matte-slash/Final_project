package com.ITCube.Booking.service;

import com.ITCube.Booking.exception.BookingNotFoundException;
import com.ITCube.Booking.exception.IllegalDateTimeException;
import com.ITCube.Booking.repository.BookingRepository;
import com.ITCube.Data.model.Booking;
import com.ITCube.Data.model.Desk;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Matteo Rosso
 */
@Slf4j @RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService{

    private BookingRepository rep;
    private Clock clock;
    @Override
    public List<Booking> findAllBookings() {
        log.info("Find all bookings");
        return rep.findAll();
    }

    @Override
    public List<Booking> findAllBookingsByUser(long userId) {
        log.info("Find all bookings by user id: " + userId);
        return rep.findAllBookingsByUser(userId);
    }

    @Override
    public List<Booking> findAllBookingByDesk(long deskId) {
        log.info("Find all bookings by desk id: " + deskId);
        return rep.findAllBookingsByDesk(deskId);
    }

    @Override
    public Booking findBookingById(long id) {
        log.info("Find booking by id: " + id);
        return rep.findById(id)
                .orElseThrow(()->new BookingNotFoundException("Booking "+ id+" not found"));
    }

    @Override
    public List<Desk> findAllDeskAvailable(LocalDateTime start, LocalDateTime end) {
        log.info("Find all desk available");
        if(end.isBefore(start) || start.isBefore(LocalDateTime.now(clock))){
            throw new IllegalDateTimeException("Date insert error");
        }
        return rep.findDeskAvailable(start,end);
    }

    @Override
    public Booking createBooking(Booking booking) {
        log.info("Create Booking "+ booking.toString());
        if(booking.getEndDate().isBefore(booking.getStartDate()) ||
                booking.getStartDate().isBefore(LocalDateTime.now(clock))){
            throw new IllegalDateTimeException("Date are wrong");
        }
        return rep.save(booking);
    }

    @Override
    public void deleteBookingById(long id) {
        log.info("Delete booking "+id);
        rep.findById(id).orElseThrow(()->new BookingNotFoundException("Booking "+id+ " not found"));
        rep.deleteById(id);
    }
}
