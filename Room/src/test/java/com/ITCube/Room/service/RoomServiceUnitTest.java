package com.ITCube.Room.service;

import com.ITCube.Data.model.Room;
import com.ITCube.Data.repository.RoomRepository;
import com.ITCube.Room.exception.RoomNotFoundException;
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
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * @author Matteo Rosso
 */

@ExtendWith(MockitoExtension.class)
public class RoomServiceUnitTest {
    @Mock
    private RoomRepository rep;

    @InjectMocks
    private RoomServiceImpl underTest;

    @Test
    void findAllRoomsTest(){
        // When
        Room expected=new Room("Stanza 1", "Via Roma 15", 99);
        when (rep.findAll()).thenReturn(List.of(expected));

        // Action
        List<Room> result=underTest.findAllRooms();

        // Assert
        assertFalse(result.isEmpty());
        assertThat(result.size(),equalTo(1));
        verify(rep,times(1)).findAll();
        verifyNoMoreInteractions(rep);
    }

    @Test
    void findRoomById(){
        // When
        Room expected=new Room("Stanza 1", "Via Roma 15", 99);
        when(rep.findById(anyLong())).thenReturn(Optional.of(expected));

        // Action
        Room result=underTest.findRoomById(anyLong());

        // Assert
        assertNotNull(result);
        assertThat(result.getName(),equalTo("Stanza 1"));
        verify(rep,times(1)).findById(anyLong());
        verifyNoMoreInteractions(rep);
    }

    @Test
    void findOneRoomFailTest() {
        // When
        when(rep.findById(anyLong())).thenReturn(Optional.empty());

        // Assert
        assertThrows(RoomNotFoundException.class,()->underTest.findRoomById(anyLong()));
    }

    @Test
    void saveRoomTest(){
        // When
        Room expected=new Room("Stanza 1", "Via Roma 15", 99);
        when(rep.save(any(Room.class))).thenReturn(expected);

        // Action
        Room result=underTest.saveRoom(expected);

        // Assert
        assertEquals(expected.getName(),result.getName());
        verify(rep,times(1)).save(any(Room.class));
        verifyNoMoreInteractions(rep);
    }

    @Test
    void deleteRoomTest(){
        // When
        Room expected=new Room("Stanza 1", "Via Roma 15", 99);
        when(rep.findById(anyLong())).thenReturn(Optional.of(expected));
        doNothing().when(rep).deleteById(anyLong());

        // Action
        underTest.deleteRoom(anyLong());

        // Assert
        verify(rep,times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(rep);
    }

    @Test
    void updateRoomTest(){
        // When
        Room expected=new Room("Stanza 1", "Via Roma 15", 99);
        Room new_r=new Room("Stanza 1", "Via Roma 15",88);
        when(rep.findById(anyLong())).thenReturn(Optional.of(expected));
        when(rep.save(any(Room.class))).thenReturn(new_r);

        // Action
        Room result=underTest.updateRoom(anyLong(),new_r);

        // Assert
        assertEquals(result.getTotalDesk(), new_r.getTotalDesk());
        verify(rep,times(1)).findById(anyLong());
        verify(rep,times(1)).save(any(Room.class));
        verifyNoMoreInteractions(rep);
    }

}
