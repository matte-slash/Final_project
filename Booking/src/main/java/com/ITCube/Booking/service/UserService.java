package com.ITCube.Booking.service;


import com.ITCube.Data.model.User;

import java.util.List;

/**
 * @author Matteo Rosso
 */

public interface UserService {

    List<User> findAllUsers();

    User findUserById(long id);

    User createUser(User user);

    User updateUser(long id, User user);

    void deleteUser(long id);

}
