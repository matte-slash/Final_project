package com.ITCube.Booking.controller;

import com.ITCube.Booking.exception.BookingNotFoundException;
import com.ITCube.Booking.service.BookingService;
import com.ITCube.Booking.util.LocalDateTimeAdapter;
import com.ITCube.Data.model.Booking;
import com.ITCube.Data.model.Desk;
import com.ITCube.Data.model.Room;
import com.ITCube.Data.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * @author Matteo Rosso
 */

@ExtendWith(MockitoExtension.class)
@WebMvcTest(BookingController.class)
class BookingControllerIntegrationTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public BookingService service;


    @Test
    void findAllBookingsTest() throws Exception {
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

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/bookings"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].startDate").value(start));
    }

    @Test
    void findAllBookingByUserTest() throws Exception {
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

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/bookings?userID=77"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].startDate").value(start));
    }

    @Test
    void findAllBookingByDeskTest() throws Exception {
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

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/bookings?deskID=77"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].startDate").value(start));
    }

    @Test
    void findAllBookingByUserAndDeskTest() throws Exception {
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

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/bookings?deskID=77&userID=99"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].startDate").value(start));
    }

    @Test
    void findBookingByIdTest() throws Exception {
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

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/bookings/77"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.startDate").value(start));
    }


    @Test
    void findAllDeskAvailableTest() throws Exception {
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Desk d=new Desk(1L,"A1",r);
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);
        when(service.findAllDeskAvailable(st,en)).thenReturn(List.of(d));


        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/bookings/desks?start=2023-02-21T10:30&end=2023-02-21T11:30"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].deskName").value(d.getDeskName()));

    }

    @Test
    void createBookingTest() throws Exception {
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


        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(expected)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.startDate").value(expected.getStartDate()));
    }

    @Test
    void deleteBookingTest() throws Exception {
        // Arrange
        doNothing().when(service).deleteBookingById(anyLong());

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/bookings/77"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBookingFailTest() throws Exception {
        // Arrange
        doThrow(new BookingNotFoundException("Booking 77 not found"))
                .when(service).deleteBookingById(anyLong());

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/bookings/77"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}