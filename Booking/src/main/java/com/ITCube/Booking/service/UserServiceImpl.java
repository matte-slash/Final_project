package com.ITCube.Booking.service;

import com.ITCube.Booking.exception.UserNotFoundException;
import com.ITCube.Booking.repository.UserRepository;
import com.ITCube.Data.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matteo Rosso
 */
@Slf4j @RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private UserRepository rep;

    @Override
    public List<User> findAllUsers() {
        log.info("Find all users");
        List<User> result=new ArrayList<User>();
        rep.findAll().forEach(result::add);
        return result;
    }

    @Override
    public User findUserById(long id) {
        log.info("Find user by id "+id);
        return rep.findById(id).orElseThrow(()->new UserNotFoundException("User "+id+" not found"));
    }

    @Override
    public User findUserByNameAndBySurname(String name, String surname) {
        log.info("Find user by name"+ name+" and surname"+ surname);
        return rep.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(name, surname);
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
        result.setRole(user.getRole());
        return rep.save(result);
    }

    @Override
    public void deleteUser(long id) {
        log.info("Delete user "+ id);
        rep.findById(id).orElseThrow(()->new UserNotFoundException("User "+id+" not found"));
        rep.deleteById(id);
    }
}
