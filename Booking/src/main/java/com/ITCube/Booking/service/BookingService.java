package com.ITCube.Booking.service;

import com.ITCube.Data.model.Booking;
import com.ITCube.Data.model.Desk;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Matteo Rosso
 */

public interface BookingService {

    List<Booking> findAllBookings();

    List<Booking> findAllBookingsByUser(long userId);

    List<Booking> findAllBookingByDesk(long deskId);

    Booking findBookingById(long id);

    List<Desk> findAllDeskAvailable(LocalDateTime start, LocalDateTime end);
                        //todo check if possible in Booking
    Booking createBooking(Booking booking);

    void deleteBookingById(long id);

}
