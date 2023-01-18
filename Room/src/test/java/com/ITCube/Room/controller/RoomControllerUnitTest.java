package com.ITCube.Room.controller;

import com.ITCube.Data.model.Room;
import com.ITCube.Room.exception.RoomNotFoundException;
import com.ITCube.Room.service.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * @author Matteo Rosso
 */

@ExtendWith(MockitoExtension.class)
class RoomControllerUnitTest {

    @Mock
    private RoomService service;

    @InjectMocks
    private RoomController underTest;

    @Test
    void findAllRoomsTest() {
        // Arrange
        Room expected=new Room("Stanza 1", "Via",90);
        when(service.findAllRooms()).thenReturn(List.of(expected));

        // Action
        List<Room> result=underTest.findAllRooms();

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getName(), equalTo("Stanza 1"));
        verify(service,times(1)).findAllRooms();
        verifyNoMoreInteractions(service);
    }

    @Test
    void findRoomByIDTest() {
        // Arrange
        Room expected=new Room("Stanza 1", "Via",90);
        when(service.findRoomById(anyLong())).thenReturn(expected);

        // Action
        Room result=underTest.findRoomById(anyLong());

        // Assert
        assertNotNull(result);
        assertThat(result.getName(), equalTo("Stanza 1"));
        verify(service, times(1)).findRoomById(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void findRoomByIdFailTest(){
        // Arrange
        when(service.findRoomById(anyLong())).thenThrow(RoomNotFoundException.class);

        // Action and Assert
        assertThrows(RoomNotFoundException.class,()->underTest.findRoomById(anyLong()));
        verify(service,times(1)).findRoomById(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void createRoomTest() {
        // Arrange
        Room expected=new Room("Stanza 1", "Via",90);
        when(service.saveRoom(expected)).thenReturn(expected);

        // Action
        Room result= underTest.createRoom(expected);

        // Assert
        assertNotNull(result);
        assertThat(result.getName(),equalTo("Stanza 1"));
        verify(service, times(1)).saveRoom(expected);
        verifyNoMoreInteractions(service);
    }

    @Test
    void deleteRoomTest() {
        // Arrange
        doNothing().when(service).deleteRoom(anyLong());

        // Action
        underTest.deleteRoom(anyLong());

        // Assert
        verify(service, times(1)).deleteRoom(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void updateRoomTest() {
        // Arrange
        Room expected=new Room("Stanza 1", "Via",90);
        when(service.updateRoom(1L, expected)).thenReturn(expected);

        // Action
        Room result=underTest.updateRoom(1L, expected);

        // Assert
        assertNotNull(result);
        assertThat(result.getAddress(), equalTo(expected.getAddress()));
        verify(service,times(1)).updateRoom(1L,expected);
        verifyNoMoreInteractions(service);
    }
}