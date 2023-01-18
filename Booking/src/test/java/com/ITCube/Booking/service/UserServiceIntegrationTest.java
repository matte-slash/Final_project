package com.ITCube.Booking.service;

import com.ITCube.Data.model.User;
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
class UserServiceIntegrationTest {

    @Autowired
    private UserService underTest;

    @Test
    void findAllUsersTest() {
        // Arrange
        User expected=new User("Matteo","Rosso","Dev");
        underTest.createUser(expected);

        // Action
        List<User> result=underTest.findAllUsers();

        // Assert
        assertNotNull(result);
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getFirstName(),equalTo("Matteo"));
    }

    @Test
    void findUserByIdTest() {
        // Arrange
        User expected=new User("Matteo","Rosso","Dev");
        User u=underTest.createUser(expected);

        // Action
        User result=underTest.findUserById(u.getId());

        // Assert
        assertNotNull(result);
        assertThat(result.getFirstName(),equalTo("Matteo"));
    }



    @Test
    void createUserTest() {
        // Arrange
        User expected=new User("Matteo","Rosso","Dev");

        // Action
        User result=underTest.createUser(expected);

        // Assert
        assertNotNull(result);
        assertThat(result.getFirstName(),equalTo("Matteo"));
    }

    @Test
    void updateUserTest() {
        // Arrange
        User expected=new User("Matteo","Rosso","Dev");
        User u_new=new User("Matteo","Rosso", "NUOVO");
        User u=underTest.createUser(expected);

        // Action
        User result=underTest.updateUser(u.getId(),u_new);

        // Assert
        assertNotNull(result);
        assertThat(result.getRole(),equalTo("NUOVO"));
    }

    @Test
    void deleteUserTest() {
        // Arrange
        User expected=new User("Matteo","Rosso","Dev");
        User u=underTest.createUser(expected);

        // Action
        underTest.deleteUser(u.getId());
        List<User> result=underTest.findAllUsers();

        // Assert
        assertNotNull(result);
        assertThat(result.size(),equalTo(0));
    }
}