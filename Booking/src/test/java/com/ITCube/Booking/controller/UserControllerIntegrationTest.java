package com.ITCube.Booking.controller;

import com.ITCube.Booking.exception.UserNotFoundException;
import com.ITCube.Booking.service.UserService;
import com.ITCube.Data.model.User;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author Matteo Rosso
 */

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
class UserControllerIntegrationTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public UserService service;

    @Test
    void findAllUserTest() throws Exception {
        // Arrange
        User expected=new User("Matteo", "Rosso", "Dev");
        when(service.findAllUsers()).thenReturn(List.of(expected));

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].firstName").value(expected.getFirstName()))
                .andExpect(jsonPath("$[0].role").value(expected.getRole()));
    }


    @Test
    void findUserByIdTest() throws Exception {
        // Arrange
        User expected=new User("Matteo", "Rosso", "Dev");
        when(service.findUserById(anyLong())).thenReturn(expected);

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/users/77"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(expected.getFirstName()));
    }

    @Test
    void createNewUserTest() throws Exception {
        // Arrange
        User expected=new User("Matteo", "Rosso", "Dev");
        when(service.createUser(any(User.class))).thenReturn(expected);
        Gson gson = new Gson();

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(expected)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(expected.getFirstName()));
    }

    @Test
    void deleteUserTest() throws Exception {
        // Arrange
        doNothing().when(service).deleteUser(anyLong());

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/77"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    void deleteUserFailTest() throws Exception {
        // Arrange
        doThrow(new UserNotFoundException("User 77 not found"))
                .when(service).deleteUser(anyLong());

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/77"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    void updateUserTest() throws Exception {
        // Arrange
        User expected=new User("Matteo", "Rosso", "Dev");
        when(service.updateUser(anyLong(),any(User.class))).thenReturn(expected);
        Gson gson = new Gson();

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/users/77")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(expected)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(expected.getFirstName()));
    }

    @Test
    void updateUserFailTest() throws Exception {
        // Arrange
        User expected=new User("Matteo", "Rosso", "Dev");
        when(service.updateUser(anyLong(),any(User.class)))
                .thenThrow(UserNotFoundException.class);

        Gson gson=new Gson();

        // Action and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/users/77")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(expected)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}