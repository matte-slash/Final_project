package com.ITCube.Booking.repository;

import com.ITCube.Data.model.Booking;
import com.ITCube.Data.model.Desk;
import com.ITCube.Data.model.Room;
import com.ITCube.Data.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class BookingRepositoryTest {
    @Autowired
    public BookingRepository underTest;

    @Test
    void findDeskAvailableTest() {
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        List<Desk> result= underTest.findDeskAvailable(st,en);

        assertNotNull(result);
    }

    @Test
    void findAllBookingsByDeskTest() {
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Room r2=new Room(2L, "Stanza 2","V", 11);
        Desk d=new Desk(1L,"A1",r);
        Desk d2=new Desk(2L, "C1", r2);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u,d);
        Booking b2=new Booking(st, en,u,d2);
        underTest.save(expected);
        underTest.save(b2);

        // Action
        List<Booking> result= underTest.findAllBookingsByDesk(1L);

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getDesk().getDeskName(), equalTo("A1"));
    }

    @Test
    void findAllBookingsByUserTest() {
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Room r2=new Room(2L, "Stanza 2","V", 11);
        Desk d=new Desk(1L,"A1",r);
        Desk d2=new Desk(2L, "C1", r2);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        User u2=new User(2L, "Luca", "Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u,d);
        Booking b2=new Booking(st, en,u2,d2);
        underTest.save(expected);
        underTest.save(b2);

        // Action
        List<Booking> result=underTest.findAllBookingsByUser(1L);

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getUser().getFirstName(), equalTo("Matteo"));
    }

    @Test
    void queryTest() {
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Room r2=new Room(2L, "Stanza 2","V", 11);
        Desk d=new Desk(1L,"A1",r);
        Desk d2=new Desk(2L, "C1", r2);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u,d);
        Booking b2=new Booking(st, en,u,d2);
        underTest.save(expected);
        underTest.save(b2);

        // Action
        List<Booking> result=underTest.query(1L, 1L);

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getUser().getFirstName(), equalTo("Matteo"));
        assertThat(result.get(0).getDesk().getDeskName(), equalTo("A1"));
    }

    @Test
    void checkUserBookingsTest() {
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Room r2=new Room(2L, "Stanza 2","V", 11);
        Desk d=new Desk(1L,"A1",r);
        Desk d2=new Desk(2L, "C1", r2);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        User u2=new User(2L, "Luca", "Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u,d);
        Booking b2=new Booking(st, en,u2,d2);
        underTest.save(expected);
        underTest.save(b2);
        LocalDateTime begin=LocalDateTime.parse("2023-02-21T09:30");
        LocalDateTime fin=LocalDateTime.parse("2023-02-21T10:31");

        // Action
        List<Booking> result=underTest.checkUserBookings(1L,begin,fin);

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getUser().getFirstName(), equalTo("Matteo"));
    }

    @Test
    void CheckReturnEmptyTest(){
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Room r2=new Room(2L, "Stanza 2","V", 11);
        Desk d=new Desk(1L,"A1",r);
        Desk d2=new Desk(2L, "C1", r2);
        User u=new User(1L,"Matteo","Rosso", "Dev");
        User u2=new User(2L, "Luca", "Rosso", "Dev");
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        Booking expected=new Booking(st,en,u,d);
        Booking b2=new Booking(st, en,u2,d2);
        underTest.save(expected);
        underTest.save(b2);
        LocalDateTime begin=LocalDateTime.parse("2023-02-21T09:30");
        LocalDateTime fin=LocalDateTime.parse("2023-02-21T10:29");

        // Action
        List<Booking> result=underTest.checkUserBookings(1L,begin,fin);

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(0));
    }
}