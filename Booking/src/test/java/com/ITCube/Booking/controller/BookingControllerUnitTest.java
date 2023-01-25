package com.ITCube.Booking.controller;

import com.ITCube.Booking.service.BookingService;
import com.ITCube.Booking.util.Interval;
import com.ITCube.Data.model.Booking;
import com.ITCube.Data.model.Desk;
import com.ITCube.Data.model.Room;
import com.ITCube.Data.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * @author Matteo Rosso
 */

@ExtendWith(MockitoExtension.class)
class BookingControllerUnitTest {

    @Mock
    private BookingService service;

    @InjectMocks
    private BookingController underTest;


    @Test
    void findAllBookingsTest() {
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Desk d=new Desk(1L,"A1",r);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u,d);
        when(service.findAllBookings()).thenReturn(List.of(expected));

        // Action
        List<Booking> result= underTest.findAllBookings(null,null);

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getStartDate(), equalTo(st));
        verify(service, times(1)).findAllBookings();
        verifyNoMoreInteractions(service);
    }

    @Test
    void findBookingsByDeskTest() {
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Desk d=new Desk(1L,"A1",r);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u,d);
        when(service.findAllBookingByDesk(anyLong())).thenReturn(List.of(expected));

        // Action
        List<Booking> result= underTest.findAllBookings(1L,null);

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getStartDate(), equalTo(st));
        verify(service, times(1)).findAllBookingByDesk(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void findBookingByUserTest(){
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Desk d=new Desk(1L,"A1",r);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u,d);
        when(service.findAllBookingsByUser(anyLong())).thenReturn(List.of(expected));

        // Action
        List<Booking> result= underTest.findAllBookings(null,1L);

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getStartDate(), equalTo(st));
        verify(service, times(1)).findAllBookingsByUser(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void findBookingsByDeskAndByUser(){
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Desk d=new Desk(1L,"A1",r);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u,d);
        when(service.query(anyLong(),anyLong())).thenReturn(List.of(expected));

        // Action
        List<Booking> result=underTest.findAllBookings(1L,1L);

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getStartDate(), equalTo(st));
        verify(service, times(1)).query(anyLong(),anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void findBookingByIdTest() {
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Desk d=new Desk(1L,"A1",r);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u,d);
        when(service.findBookingById(anyLong())).thenReturn(expected);

        // Action
        Booking result= underTest.findBookingById(anyLong());

        // Assert
        assertNotNull(result);
        assertThat(result.getEndDate(), equalTo(en));
        verify(service,times(1)).findBookingById(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void findAllDeskAvailableTest() {
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Desk expected=new Desk(1L,"A1",r);
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        when(service.findAllDeskAvailable(st,en)).thenReturn(List.of(expected));

        // Action
        List<Desk> result=underTest.findAllDeskAvailable(new Interval(start,end));

        // Assert
        assertNotNull(result);
        assertThat(result.size(),equalTo(1));
        assertThat(result.get(0).getDeskName(), equalTo("A1"));
        verify(service, times(1)).findAllDeskAvailable(st,en);
        verifyNoMoreInteractions(service);
    }


    @Test
    void createBookingTest() {
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Desk d=new Desk(1L,"A1",r);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u,d);
        when(service.createBooking(any(Booking.class))).thenReturn(expected);

        // Action
        Booking result=underTest.createBooking(expected);

        // Assert
        assertNotNull(result);
        assertThat(result.getStartDate(), equalTo(st));
        verify(service, times(1)).createBooking(any(Booking.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void deleteBookingTest() {
        // Arrange
        doNothing().when(service).deleteBookingById(anyLong());

        // Action
        underTest.deleteBooking(anyLong());

        // Assert
        verify(service, times(1)).deleteBookingById(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void findAllAvailableByRoomTest(){
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Desk d=new Desk(1L,"A1",r);
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Interval interval=new Interval(start,end);
        when(service.findAllAvailableByRoom(1L, st, en)).thenReturn(List.of(d));

        // Action
        List<Desk> result= underTest.findAllAvailableByRoom(1L,interval);

        // Assert
        assertNotNull(result);
        assertThat(result.size(),equalTo(1));
        assertThat(result.get(0).getDeskName(),equalTo(d.getDeskName()));
        verify(service, times(1)).findAllAvailableByRoom(1L,st,en);
        verifyNoMoreInteractions(service);
    }

    @Test
    void updateBookingTest(){
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Desk d=new Desk(1L,"A1",r);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u,d);
        when(service.updateBooking(1L,expected)).thenReturn(expected);

        // Action
        Booking result= underTest.updateBooking(1L,expected);

        // Assert
        assertNotNull(result);
        assertThat(result.getStartDate(), equalTo(st));
        verify(service,times(1)).updateBooking(1L,expected);
        verifyNoMoreInteractions(service);
    }
}