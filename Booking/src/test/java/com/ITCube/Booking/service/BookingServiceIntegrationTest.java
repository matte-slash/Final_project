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
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    @Commit
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
        Desk d=new Desk(1L,"A1",r);
        Desk d2=new Desk(2L,"C", r);
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
        Desk d2=new Desk(2L, "A2", r2);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u,d);
        underTest.createBooking(expected);

        // Action
        List<Desk> result=underTest.findAllAvailableByRoom(2L, st, en);

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
    void createBookingFailTest(){
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
}