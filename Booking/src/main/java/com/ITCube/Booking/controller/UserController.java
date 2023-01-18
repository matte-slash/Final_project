package com.ITCube.Booking.controller;

import com.ITCube.Booking.service.UserService;
import com.ITCube.Data.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Matteo Rosso
 */

@RestController
@RequestMapping(path="/users")
@Slf4j
@Validated
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @ResponseStatus(value= HttpStatus.OK)
    @GetMapping
    public List<User> findAllUser(){
        log.info("Find all user");
        return service.findAllUsers();
    }


    @ResponseStatus(value= HttpStatus.FOUND)
    @GetMapping("/{id}")
    public User findUserById(@PathVariable long id){
        log.info("Find User "+ id);
        return service.findUserById(id);
    }

    @ResponseStatus(value=HttpStatus.CREATED)
    @PostMapping
    public User createNewUser(@RequestBody @Valid User user){
        log.info("Create new user");
        return service.createUser(user);
    }

    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id){
        log.info("Delete user "+ id);
        service.deleteUser(id);
    }

    @ResponseStatus(value=HttpStatus.OK)
    @PutMapping("/{id}")
    public User updateUser(@PathVariable long id, @RequestBody @Valid User user){
        log.info("Update user "+ id);
        return service.updateUser(id, user);
    }

}
