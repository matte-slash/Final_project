package com.ITCube.Desk.service;

import com.ITCube.Data.model.Desk;
import com.ITCube.Data.model.Room;
import com.ITCube.Data.repository.DeskRepository;
import com.ITCube.Desk.exception.DeskNotFoundException;
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
public class DeskServiceUnitTest {
    @Mock
    private DeskRepository rep;
    @InjectMocks
    private DeskServiceImpl underTest;


    @Test
    void findAllDeskTest(){
        // When
        Room room=new Room("Stanza 1", "Via Roma 15",99);
        Desk expected=new Desk("A1",room);
        when(rep.findAll()).thenReturn(List.of(expected));

        // Action
        List<Desk> result=underTest.findAllDesk();

        // Assert
        assertFalse(result.isEmpty());
        assertThat(result.size(),equalTo(1));
        verify(rep,times(1)).findAll();
        verifyNoMoreInteractions(rep);
    }

    @Test
    void findAllDeskByRoomTest(){
        // When
        Room room=new Room("Stanza 1", "Via Roma 15", 99);
        Desk d1=new Desk("A1",room);
        Desk d2=new Desk("A2",room);
        when(rep.findDeskByRoom(anyLong())).thenReturn(List.of(d1,d2));

        // Action
        List<Desk> result=underTest.findAllDeskByRoom(anyLong());

        // Assert
        assertFalse(result.isEmpty());
        assertThat(result.size(), equalTo(2));
        verify(rep,times(1)).findDeskByRoom(anyLong());
        verifyNoMoreInteractions(rep);
    }

    @Test
    void findDeskByIdTest(){
        // When
        Desk expected=new Desk("A1", new Room("Stanza 1", "Via Roma 15", 99));
        when(rep.findById(anyLong())).thenReturn(Optional.of(expected));

        // Action
        Desk result=underTest.findDeskById(anyLong());

        // Assert
        assertNotNull(result);
        assertThat(result.getDeskName(),equalTo(expected.getDeskName()));
        verify(rep,times(1)).findById(anyLong());
        verifyNoMoreInteractions(rep);
    }

    @Test
    void findDeskByIdFailTest(){
        // When
        when(rep.findById(anyLong())).thenReturn(Optional.empty());

        // Assert and Action
        assertThrows(DeskNotFoundException.class ,()->underTest.findDeskById(anyLong()));
    }


    @Test
    void createDeskTest(){
        // When
        Desk expected=new Desk("A1", new Room("Stanza 1", "Via Roma 15", 99));
        when(rep.save(any(Desk.class))).thenReturn(expected);

        // Action
        Desk result= underTest.createDesk(expected);

        // Assert
        assertThat(result.getDeskName(),equalTo(expected.getDeskName()));
        verify(rep,times(1)).save(any(Desk.class));
        verifyNoMoreInteractions(rep);
    }

    @Test
    void deleteDeskTest(){
        // When
        Desk expected=new Desk("A1", new Room("Stanza 1", "Via Roma 15", 99));
        when(rep.findById(anyLong())).thenReturn(Optional.of(expected));
        doNothing().when(rep).deleteById(anyLong());

        // Action
        underTest.deleteDesk(anyLong());

        // Assert
        verify(rep,times(1)).findById(anyLong());
        verify(rep,times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(rep);
    }

    @Test
    void updateDeskTest(){
        // When
        Desk expected=new Desk("A1", new Room("Stanza 1", "Via Roma 15", 99));
        Desk new_d=new Desk("AA1", new Room("Stanza 1","Via Roma 15", 99));
        when(rep.findById(anyLong())).thenReturn(Optional.of(expected));
        when(rep.save(any(Desk.class))).thenReturn(new_d);

        // Action
        Desk result=underTest.updateDesk(anyLong(), new_d);

        // Assert
        assertThat(result.getDeskName(),equalTo(new_d.getDeskName()));
        verify(rep,times(1)).findById(anyLong());
        verify(rep,times(1)).save(any(Desk.class));
        verifyNoMoreInteractions(rep);
    }


}
