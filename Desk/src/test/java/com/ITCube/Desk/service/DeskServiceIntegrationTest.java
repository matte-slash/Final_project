package com.ITCube.Desk.service;

import com.ITCube.Data.model.Desk;
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
public class DeskServiceIntegrationTest {
    @Autowired
    private DeskService underTest;

    @Test
    void findAllDeskTest(){
        // Arrange
        Room room=new Room("Stanza 1", "Via Roma 11", 99);
        Desk expected= new Desk("A1", room);
        underTest.createDesk(expected);

        // Action
        List<Desk> result=underTest.findAllDesk();

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getDeskName(), equalTo(expected.getDeskName()));
    }

    @Test
    void findDeskByIdTest(){
        // Arrange
        Room room=new Room("Stanza 1", "Via Roma 11", 99);
        Desk expected= new Desk("A1", room);
        Desk d=underTest.createDesk(expected);

        // Action
        Desk result=underTest.findDeskById(d.getId());

        // Assert
        assertNotNull(result);
        assertThat(result.getDeskName(),equalTo("A1"));
    }

    @Test
    void createDeskTest(){
        // Arrange
        Room room=new Room("Stanza 1", "Via Roma 11", 99);
        Desk expected= new Desk("A1", room);

        // Action
        Desk result=underTest.createDesk(expected);

        // Assert
        assertNotNull(result);
        assertThat(result.getDeskName(),equalTo("A1"));
    }

    @Test
    void deleteDeskTest(){
        // Arrange
        Room room=new Room("Stanza 1", "Via Roma 11", 99);
        Desk expected= new Desk("A1", room);
        Desk d=underTest.createDesk(expected);

        // Action
        underTest.deleteDesk(d.getId());
        List<Desk> result=underTest.findAllDesk();

        // Assert
        assertNotNull(result);
        assertThat(result.size(),equalTo(0));
    }

    @Test
    void updateDeskTest(){
        // Arrange
        Room room=new Room("Stanza 1", "Via Roma 11", 99);
        Desk d1=new Desk("A1", room);
        Desk d2=new Desk("A2", room);
        Desk expected=underTest.createDesk(d1);

        // Action
        expected=underTest.updateDesk(expected.getId(), d2);

        // Assert
        assertNotNull(expected);
        assertThat(expected.getDeskName(),equalTo("A2"));
    }
}
