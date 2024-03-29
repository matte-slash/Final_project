package com.ITCube.Booking.service;

import com.ITCube.Booking.exception.UserNotFoundException;
import com.ITCube.Data.model.User;
import com.ITCube.Data.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matteo Rosso
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository rep;

    @Autowired
    public UserServiceImpl(UserRepository rep) {
        this.rep = rep;
    }

    @Override
    public List<User> findAllUsers() {
        log.info("Find all users");
        List<User> result= new ArrayList<>();
        rep.findAll().forEach(result::add);
        return result;
    }

    @Override
    public User findUserById(long id) {
        log.info("Find user by id "+id);
        return rep.findById(id).orElseThrow(()->new UserNotFoundException("User "+id+" not found"));
    }


    @Override
    public User createUser(User user) {
        log.info("Create new user");
        return rep.save(user);
    }

    @Override
    public User updateUser(long id, User user) {
        log.info("Update user " + id);
        User result=rep.findById(id).orElseThrow(()->new UserNotFoundException("User "+id+" not found"));
        result.setFirstName(user.getFirstName());
        result.setLastName(user.getLastName());
        result.setAuthority(user.getAuthority());
        return rep.save(result);
    }

    @Override
    public void deleteUser(long id) {
        log.info("Delete user "+ id);
        rep.findById(id).orElseThrow(()->new UserNotFoundException("User "+id+" not found"));
        rep.deleteById(id);
    }
}
