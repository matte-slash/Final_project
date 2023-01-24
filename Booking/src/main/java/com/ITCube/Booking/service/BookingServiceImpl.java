package com.ITCube.Booking.service;

import com.ITCube.Booking.exception.BookingNotFoundException;
import com.ITCube.Booking.exception.IllegalDateTimeException;
import com.ITCube.Booking.repository.BookingRepository;
import com.ITCube.Data.model.Booking;
import com.ITCube.Data.model.Desk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Matteo Rosso
 */
@Slf4j
@Service
public class BookingServiceImpl implements BookingService{

    private final BookingRepository rep;
    private final Clock clock;

    @Autowired
    public BookingServiceImpl(BookingRepository rep, Clock clock) {
        this.rep = rep;
        this.clock = clock;
    }

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
        checkTime(start, end);
        return rep.findDeskAvailable(start,end);
    }

    @Override
    public Booking createBooking(Booking booking) {
        log.info("Create Booking "+ booking.toString());
        checkTime(booking.getStartDate(), booking.getEndDate());

        if(rep.checkUserBookings(booking.getUser().getId(), booking.getStartDate(), booking.getEndDate()).isEmpty()){
            return rep.save(booking);
        }else{
            throw new IllegalDateTimeException("User "+
                    booking.getUser().getId()+ " already has a booking for that time");
        }
    }

    @Override
    public void deleteBookingById(long id) {
        log.info("Delete booking "+id);
        rep.findById(id).orElseThrow(()->new BookingNotFoundException("Booking "+id+ " not found"));
        rep.deleteById(id);
    }

    @Override
    public List<Booking> query(Long deskID, Long userID) {
        log.info("Query method");
        return rep.query(deskID, userID);
    }

    @Override
    public List<Desk> findAllAvailableByRoom(long id, LocalDateTime start, LocalDateTime end) {
        log.info("Find all desk available by room "+id);
        checkTime(start, end);
        List<Desk> list=rep.findDeskAvailable(start, end);
        List<Desk> result=new ArrayList<>();
        for(Desk desk : list){
            if(desk.getRoom().getId()==id){
                result.add(desk);
            }
        }
        return result;
    }

    @Override
    public Booking updateBooking(long id, Booking b) {
        log.info("Update booking "+ id);
        this.deleteBookingById(id);
        return this.createBooking(b);
    }

    private void checkTime(LocalDateTime start, LocalDateTime end){
        if(end.isBefore(start) || start.isBefore(LocalDateTime.now(clock)) || (start.getYear()!=end.getYear()) ||
                (start.getMonthValue()!=end.getMonthValue()) || (start.getDayOfMonth()!=end.getDayOfMonth()) ||
                (start.getHour()<9) || (end.getHour()>18 && end.getMinute()!=0)){
            throw new IllegalDateTimeException("Date insert error");
        }
    }
}
