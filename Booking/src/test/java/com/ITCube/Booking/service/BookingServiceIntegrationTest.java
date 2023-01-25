package com.ITCube.Booking.service;

import com.ITCube.Booking.exception.IllegalDateTimeException;
import com.ITCube.Booking.repository.BookingRepository;
import com.ITCube.Data.model.Booking;
import com.ITCube.Data.model.Desk;
import com.ITCube.Data.model.Room;
import com.ITCube.Data.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Matteo Rosso
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class BookingServiceIntegrationTest {

    @Autowired
    private BookingService underTest;
    @Autowired
    private BookingRepository rep;

    @BeforeEach
    void setUp() {
        rep.deleteAll();
    }

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
        underTest.createBooking(expected);

        // Action
        List<Booking> result=underTest.findAllBookings();

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
    }

    @Test
    void findAllBookingsByUserTest() {
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Desk d=new Desk(1L,"A1",r);
        User u1=new User(1L,"Matteo","Rosso", "Dev");
        User u2=new User(2L,"Luca", "Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u1,d);
        Booking b=underTest.createBooking(expected);

        start="2023-02-22T10:30";
        st = LocalDateTime.parse(start);
        end = "2023-02-22T11:30";
        en = LocalDateTime.parse(end);
        Booking wrong=new Booking(st, en, u2, b.getDesk());
        underTest.createBooking(wrong);


        // Arrange
        List<Booking> result=underTest.findAllBookingsByUser(b.getUser().getId());

        // Arrange
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getUser().getFirstName(), equalTo("Matteo"));
    }

    @Test
    void findAllBookingByDeskTest() {
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Desk d=new Desk(1L,"A1",r);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u,d);
        underTest.createBooking(expected);

        // Action
        List<Booking> result=underTest.findAllBookingByDesk(d.getId());

        // Arrange
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getDesk().getDeskName(), equalTo("A1"));
    }

    @Test
    void findAllBookingsByDeskAndByUserTest(){
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Desk d=new Desk(1L,"A1",r);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u,d);
        underTest.createBooking(expected);

        // Action
        List<Booking> result=underTest.query(d.getId(),u.getId());

        // Arrange
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getDesk().getDeskName(), equalTo("A1"));
        assertThat(result.get(0).getUser().getFirstName(), equalTo("Matteo"));
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
        underTest.createBooking(expected);

        // Action
        Booking result=underTest.findBookingById(expected.getId());

        // Assert
        assertNotNull(result);
        assertThat(result.getId(),equalTo(expected.getId()));
    }

    @Test
    void findAllDeskAvailableTest() {
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Room r2=new Room(2L, "Stanza 2", "V", 11);
        Desk d=new Desk(1L,"A1",r);
        Desk d2=new Desk(2L,"C1", r2);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u,d);
        underTest.createBooking(expected);

        // Action
        List<Desk> result=underTest.findAllDeskAvailable(st,en);

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getDeskName(), equalTo(d2.getDeskName()));
    }

    @Test
    void findAllDeskAvailableByRoom(){
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Room r2=new Room(2L, "Stanza 2", "V", 11);
        Desk d=new Desk(1L,"A1",r);
        Desk d2=new Desk(2L, "C1", r2);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u,d);
        underTest.createBooking(expected);
        LocalDateTime _try=LocalDateTime.parse("2024-02-21T10:30");

        // Action
        List<Desk> result=underTest.findAllAvailableByRoom(2L, _try, _try);

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getDeskName(), equalTo(d2.getDeskName()));
    }

    @Test
    void findAllDeskAvailableFailTest(){
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Desk d=new Desk(1L,"A1",r);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u,d);
        underTest.createBooking(expected);

        // Action and Assert
        assertThrows(IllegalDateTimeException.class, ()-> underTest.findAllDeskAvailable(en,st));
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

        // Action
        Booking result=underTest.createBooking(expected);

        // Assert
        assertNotNull(result);
    }

    @Test
    void createBookingFailTimeErrorTest(){
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Desk d=new Desk(1L,"A1",r);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(en,st,u,d);

        // Action and Assert
        assertThrows(IllegalDateTimeException.class, ()->underTest.createBooking(expected));
    }

    @Test
    void createBookingFailDeskNotAvailableTest(){
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Desk d=new Desk(1L,"A1",r);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        User u2=new User(2L, "Luca","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking booking=new Booking(st,en,u,d);
        underTest.createBooking(booking);
        Booking expected=new Booking(st,en,u2,d);

        // Action and Assert
        assertThrows(IllegalDateTimeException.class, ()-> underTest.createBooking(expected));
    }

    @Test
    void deleteBookingByIdTest() {
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Desk d=new Desk(1L,"A1",r);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u,d);
        underTest.createBooking(expected);

        // Action
        underTest.deleteBookingById(expected.getId());
        List<Booking> result=underTest.findAllBookings();

        // Assert
        assertNotNull(result);
        assertThat(result.size(),equalTo(0));
    }

    @Test
    void updateBookingTest(){
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Room r2=new Room(2L, "Stanza 2", "V", 11);
        Desk d=new Desk(1L,"A1",r);
        Desk d2=new Desk(2L, "C1", r2);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking booking=new Booking(st,en,u,d);
        underTest.createBooking(booking);
        Booking expected=new Booking(st,en,u,d2);

        // Action
        Booking result= underTest.updateBooking(booking.getId(), expected);

        // Assert
        assertNotNull(result);
        assertThat(result.getDesk().getDeskName(),equalTo(d2.getDeskName()));
    }

    @Test
    void updateBookingFailUserAlreadyHasBookingInThatTimeTest(){
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Room r2=new Room(2L, "Stanza 2", "V", 11);
        Desk d=new Desk(1L,"A1",r);
        Desk d2=new Desk(2L, "C1", r2);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking booking=new Booking(st,en,u,d);
        underTest.createBooking(booking);

        LocalDateTime s2=LocalDateTime.parse("2023-03-21T10:30");
        LocalDateTime e2=LocalDateTime.parse("2023-03-21T11:30");

        Booking booking2=new Booking(s2,e2,u,d2);
        underTest.createBooking(booking2);

        Booking expected= new Booking(s2,e2,u,d);

        // Action and Assert
        assertThrows(IllegalDateTimeException.class,()->underTest.updateBooking(booking.getId(),expected));
    }

}