package com.ITCube.Desk.controller;

import com.ITCube.Data.model.Desk;
import com.ITCube.Data.model.Room;
import com.ITCube.Desk.exception.DeskNotFoundException;
import com.ITCube.Desk.service.DeskService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
@WebMvcTest(DeskController.class)
@AutoConfigureMockMvc(addFilters = false)
class DeskControllerIntegrationTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public DeskService service;

    @Test
    void findAllDesksTest() throws Exception {
        // Arrange
        Room r=new Room("Stanza 1", "Via",43);
        Desk expected=new Desk("A1",r);
        when(service.findAllDesk()).thenReturn(List.of(expected));

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/desks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].deskName").value(expected.getDeskName()));
    }

    @Test
    void findAllDeskByRoomTest() throws Exception {
        // Arrange
        Room r1=new Room("Stanza 1", "Via",43);
        Desk expected=new Desk("A1",r1);
        when(service.findAllDeskByRoom(anyLong())).thenReturn(List.of(expected));

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/desks?room=77"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].deskName").value(expected.getDeskName()));
    }

    @Test
    void findDeskByIdTest() throws Exception {
        // Arrange
        Room r=new Room("Stanza 1", "Via",43);
        Desk expected=new Desk("A1",r);
        when(service.findDeskById(anyLong())).thenReturn(expected);

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/desks/77"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.deskName").value(expected.getDeskName()))
                .andExpect(jsonPath("$.room.totalDesk").value(expected.getRoom().getTotalDesk()));
    }

    @Test
    void createDeskTest() throws Exception {
        // Arrange
        Room r=new Room("Stanza 1", "Via",43);
        Desk expected=new Desk("A1",r);
        when(service.createDesk(any(Desk.class))).thenReturn(expected);
        Gson gson=new Gson();

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/desks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(expected)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.deskName").value(expected.getDeskName()));
    }

    @Test
    void deleteDeskTest() throws Exception {
        // Arrange
        doNothing().when(service).deleteDesk(anyLong());

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/desks/77"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteDeskFailTest() throws Exception {
        // Arrange
        doThrow(new DeskNotFoundException(" desk 77 not found")).when(service).deleteDesk(anyLong());

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/desks/77"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateDeskTest() throws Exception {
        // Arrange
        Room r=new Room("Stanza 1", "Via",43);
        Desk expected=new Desk("A1",r);
        when(service.updateDesk(anyLong(),any(Desk.class))).thenReturn(expected);
        Gson gson=new Gson();

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/desks/77")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(expected)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.deskName").value(expected.getDeskName()));
    }
}