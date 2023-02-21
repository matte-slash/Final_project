package com.ITCube.Room.service;

import com.ITCube.Data.model.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Matteo Rosso
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RoomServiceIntegrationTest {
    @Autowired
    private RoomService underTest;


    @Test
    void findAllRoomsTest() {
        // Arrange
        Room expected=new Room("Stanza 1", "Via Roma 11", 99);
        underTest.saveRoom(expected);

        // Action
        List<Room> result=underTest.findAllRooms();

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getName(), equalTo("Stanza 1"));
    }

    @Test
    void findRoomByIdTest() {
        // Arrange
        Room expected=new Room("Stanza 1", "Via Roma 11", 99);
        Room r=underTest.saveRoom(expected);

        // Action
        Room result=underTest.findRoomById(r.getId());

        // Assert
        assertNotNull(result);
        assertThat(result.getName(), equalTo("Stanza 1"));
    }

    @Test
    void saveRoomTest() {
        // Arrange
        Room expected=new Room("Stanza 1", "Via Roma 11", 99);

        // Action
        Room result=underTest.saveRoom(expected);

        // Assert
        assertNotNull(result);
        assertThat(result.getName(), equalTo("Stanza 1"));
        assertThat(result.getTotalDesk(), equalTo(99));
    }

    @Test
    void deleteRoomTest() {
        // Arrange
        Room expected=new Room("Stanza 1", "Via Roma 11", 99);
        Room r=underTest.saveRoom(expected);

        // Action
        underTest.deleteRoom(r.getId());
        List<Room> result=underTest.findAllRooms();

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(0));
    }

    @Test
    void updateRoomTest() {
        Room r1=new Room("Stanza 1", "Via Roma 11", 99);
        Room r2=new Room("Stanza 1", "Via Roma 11", 77);
        Room expected=underTest.saveRoom(r1);

        // Action
        Room result=underTest.updateRoom(expected.getId(),r2);

        // Assert
        assertNotNull(result);
        assertThat(result.getTotalDesk(),equalTo(r2.getTotalDesk()));
    }
}