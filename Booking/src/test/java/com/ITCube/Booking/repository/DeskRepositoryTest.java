package com.ITCube.Booking.repository;

import com.ITCube.Data.model.Desk;
import com.ITCube.Data.model.Room;
import com.ITCube.Data.repository.DeskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Matteo Rosso
 */
@DataJpaTest
@ActiveProfiles("test")
@EnableJpaRepositories(basePackages = "com.ITCube.Data.repository")
class DeskRepositoryTest {

    @Autowired
    public DeskRepository underTest;

    @Test
    void findDeskAvailableTest() {
        // Arrange
        Room r=new Room(1L, "Stanza 1", "Via Roma 11", 99);
        Room r2=new Room(2L, "Stanza 2","V", 11);
        Desk d=new Desk(1L,"A1",r);
        Desk d2=new Desk(2L, "C1", r2);
        String start = "2023-02-21T10:30";
        LocalDateTime st = LocalDateTime.parse(start);
        String end = "2023-02-21T11:30";
        LocalDateTime en = LocalDateTime.parse(end);

        // Action
        List<Desk> result=underTest.findDeskAvailable(st,en);

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0).getDeskName(), equalTo(d.getDeskName()));
        assertThat(result.get(1).getDeskName(), equalTo(d2.getDeskName()));
    }

}