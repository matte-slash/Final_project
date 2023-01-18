package com.ITCube.Booking.controller;

import com.ITCube.Booking.service.UserService;
import com.ITCube.Data.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Matteo Rosso
 */

@ExtendWith(MockitoExtension.class)
class UserControllerUnitTest {

    @Mock
    private UserService service;

    @InjectMocks
    private UserController underTest;

    @Test
    void findAllUserTest() {
        // Arrange
        User expected=new User("Matteo", "Rosso","Dev");
        when(service.findAllUsers()).thenReturn(List.of(expected));

        // Action
        List<User> result=underTest.findAllUser();

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        verify(service,times(1)).findAllUsers();
        verifyNoMoreInteractions(service);
    }


    @Test
    void findUserByIdTest() {
        // Arrange
        User expected=new User("Matteo", "Rosso","Dev");
        when(service.findUserById(anyLong())).thenReturn(expected);

        // Action
        User result=underTest.findUserById(anyLong());

        // Assert
        assertNotNull(result);
        assertThat(result.getFirstName(),equalTo(expected.getFirstName()));
        verify(service,times(1)).findUserById(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void createNewUserTest() {
        // Arrange
        User expected=new User("Matteo", "Rosso","Dev");
        when(service.createUser(expected)).thenReturn(expected);

        // Action
        User result=underTest.createNewUser(expected);

        // Assert
        assertNotNull(result);
        assertThat(result.getFirstName(),equalTo(expected.getFirstName()));
        verify(service,times(1)).createUser(expected);
        verifyNoMoreInteractions(service);
    }

    @Test
    void deleteUserTest() {
        // Arrange
        doNothing().when(service).deleteUser(anyLong());

        // Action
        underTest.deleteUser(anyLong());

        // Assert
        verify(service,times(1)).deleteUser(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void updateUserTest() {
        // Arrange
        User expected=new User("Matteo", "Rosso","Dev");
        when(service.updateUser(1L,expected)).thenReturn(expected);

        // Action
        User result= underTest.updateUser(1L,expected);

        // Assert
        assertNotNull(result);
        assertThat(result.getFirstName(), equalTo(expected.getFirstName()));
        verify(service,times(1)).updateUser(1L,expected);
        verifyNoMoreInteractions(service);
    }
}