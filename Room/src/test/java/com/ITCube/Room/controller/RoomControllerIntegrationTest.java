package com.ITCube.Room.controller;

import com.ITCube.Data.model.Room;
import com.ITCube.Room.exception.RoomNotFoundException;
import com.ITCube.Room.service.RoomService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Matteo Rosso
 */

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RoomController.class)
class RoomControllerIntegrationTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public RoomService service;


    @Test
    void findAllRoomsTest() throws Exception {
        // Arrange
        Room expected=new Room("Stanza 1", "Via",43);
        when(service.findAllRooms()).thenReturn(List.of(expected));

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/rooms"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value(expected.getName()))
                .andExpect(jsonPath("$[0].totalDesk").value(expected.getTotalDesk()));
    }

    @Test
    void findRoomByIdTest() throws Exception {
        // Arrange
        Room expected=new Room("Stanza 1", "Via",43);
        when(service.findRoomById(anyLong())).thenReturn((expected));

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/rooms/77"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.totalDesk").value(expected.getTotalDesk()));
    }

    @Test
    void findRoomByIdFailTest() throws Exception {
        // Arrange
        doThrow(new RoomNotFoundException("room 77 not found"))
                .when(service).deleteRoom(anyLong());

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/rooms/77"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void createRoomTest() throws Exception {
        // Arrange
        Room expected=new Room("Stanza 1", "Via",43);
        when(service.saveRoom(any(Room.class))).thenReturn((expected));
        Gson gson = new Gson();

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(expected)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(expected.getName()));
    }

    @Test
    void deleteRoomTest() throws Exception {
        // Arrange
        doNothing().when(service).deleteRoom(anyLong());

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/rooms/77"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void updateRoom() throws Exception {
        // Arrange
        Room expected=new Room("Stanza 1", "Via",43);
        when(service.updateRoom(anyLong(),any(Room.class))).thenReturn((expected));
        Gson gson = new Gson();

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/rooms/77")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(expected)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(expected.getName()));
    }
}