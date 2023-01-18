package com.ITCube.Desk.controller;

import com.ITCube.Data.model.Desk;
import com.ITCube.Data.model.Room;
import com.ITCube.Desk.exception.DeskNotFoundException;
import com.ITCube.Desk.service.DeskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author Matteo Rosso
 */

@ExtendWith(MockitoExtension.class)
class DeskControllerUnitTest {

    @Mock
    private DeskService service;

    @InjectMocks
    private DeskController underTest;

    @Test
    void findAllDesksTest() {
        // Arrange
        Room r=new Room("Stanza1", "Via",90);
        Desk expected=new Desk("A1", r);
        when(service.findAllDesk()).thenReturn(List.of(expected));

        // Action
        List<Desk> result= underTest.findAllDesks(null);

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getDeskName(), equalTo("A1"));
        verify(service,times(1)).findAllDesk();
        verifyNoMoreInteractions(service);
    }

    @Test
    void findAllDeskByRoomTest(){
        // Arrange
        Room r=new Room("Stanza1", "Via",90);
        Desk expected=new Desk("A1", r);
        when(service.findAllDeskByRoom(anyLong())).thenReturn(List.of(expected));

        // Action
        List<Desk> result= underTest.findAllDesks(anyLong());

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getDeskName(), equalTo("A1"));
        verify(service,times(1)).findAllDeskByRoom(anyLong());
        verifyNoMoreInteractions(service);

    }

    @Test
    void findDeskByIdTest() {
        // Arrange
        Room r=new Room("Stanza1", "Via",90);
        Desk expected=new Desk("A1", r);
        when(service.findDeskById(anyLong())).thenReturn(expected);

        // Action
        Desk result=underTest.findDeskById(anyLong());

        // Assert
        assertNotNull(result);
        assertThat(result.getDeskName(),equalTo("A1"));
        verify(service,times(1)).findDeskById(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void findDeskByIdFailTest(){
        // Arrange
        when(service.findDeskById(anyLong())).thenThrow(DeskNotFoundException.class);

        // Action and Assert
        assertThrows(DeskNotFoundException.class, ()->underTest.findDeskById(anyLong()));
        verify(service,times(1)).findDeskById(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void createDeskTest() {
        // Arrange
        Room r=new Room("Stanza1", "Via",90);
        Desk expected=new Desk("A1", r);
        when(service.createDesk(any(Desk.class))).thenReturn(expected);

        // Action
        Desk result=underTest.createDesk(expected);

        // Assert
        assertNotNull(result);
        assertThat(result.getDeskName(),equalTo("A1"));
        verify(service,times(1)).createDesk(any(Desk.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void deleteDeskTest() {
        // Arrange
        doNothing().when(service).deleteDesk(anyLong());

        // Action
        underTest.deleteDesk(anyLong());

        // Assert
        verify(service,times(1)).deleteDesk(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void updateDeskTest() {
        // Arrange
        Room r=new Room("Stanza1", "Via",90);
        Desk expected=new Desk("A1", r);
        when(service.updateDesk(1L,expected)).thenReturn(expected);

        // Action
        Desk result=underTest.updateDesk(1L,expected);

        // Assert
        assertNotNull(result);
        assertThat(result.getDeskName(),equalTo("A1"));
        verify(service,times(1)).updateDesk(1L,expected);
        verifyNoMoreInteractions(service);
    }
}