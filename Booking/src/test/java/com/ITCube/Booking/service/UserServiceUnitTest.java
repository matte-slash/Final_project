package com.ITCube.Booking.service;

import com.ITCube.Booking.exception.UserNotFoundException;
import com.ITCube.Data.model.User;
import com.ITCube.Data.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Matteo Rosso
 */

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {
    @Mock
    private UserRepository rep;

    @InjectMocks
    private UserServiceImpl underTest;

    @Test
    void findAllUsersTest() {
        // When
        User expected=new User(1L,"Matteo","Rosso",
                "m@gmail.com","password", "USER");
        when(rep.findAll()).thenReturn(List.of(expected));

        // Action
        List<User> result=underTest.findAllUsers();

        // Assert
        assertFalse(result.isEmpty());
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getFirstName(),equalTo(expected.getFirstName()));
        verify(rep,times(1)).findAll();
        verifyNoMoreInteractions(rep);
    }

    @Test
    void findUserByIdTest() {
        // When
        User expected=new User(1L,"Matteo","Rosso",
                "m@gmail.com","password", "USER");
        when(rep.findById(anyLong())).thenReturn(Optional.of(expected));

        // Action
        User result=underTest.findUserById(anyLong());

        // Assert
        assertNotNull(result);
        assertEquals(result.getFirstName(),(expected.getFirstName()));
        verify(rep,times(1)).findById(anyLong());
        verifyNoMoreInteractions(rep);
    }

    @Test
    void findUserByIdFailTest(){
        // When
        when(rep.findById(anyLong())).thenThrow(UserNotFoundException.class);

        // Action and Assert
        assertThrows(UserNotFoundException.class, ()->underTest.findUserById(anyLong()));
    }


    @Test
    void createUserTest() {
        // When
        User expected=new User(1L,"Matteo","Rosso",
                "m@gmail.com","password", "USER");
        when(rep.save(any(User.class))).thenReturn(expected);

        // Action
        User result=underTest.createUser(expected);

        // Assert
        assertThat(result.getFirstName(),equalTo(expected.getFirstName()));
        verify(rep,times(1)).save(any(User.class));
        verifyNoMoreInteractions(rep);
    }

    @Test
    void updateUserTest() {
        // When
        User expected=new User(1L,"Matteo","Rosso",
                "m@gmail.com","password", "USER");
        User new_user = new User("Luca", "Rosso",
                "l@gmail.com", "password", "USER");
        when(rep.findById(anyLong())).thenReturn(Optional.of(expected));
        when(rep.save(any(User.class))).thenReturn(new_user);

        // Action
        User result=underTest.updateUser(anyLong(), new_user);

        // Assert
        assertEquals(result.getFirstName(),(new_user.getFirstName()));
        verify(rep, times(1)).save(any(User.class));
        verifyNoMoreInteractions(rep);
    }

    @Test
    void deleteUserTest() {
        // When
        User expected=new User(1L,"Matteo","Rosso",
                "m@gmail.com","password", "USER");
        when(rep.findById(anyLong())).thenReturn(Optional.of(expected));
        doNothing().when(rep).deleteById(anyLong());

        // Action
        underTest.deleteUser(anyLong());

        // Assert
        verify(rep,times(1)).findById(anyLong());
        verify(rep,times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(rep);
    }
}