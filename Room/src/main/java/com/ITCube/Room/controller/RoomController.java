package com.ITCube.Room.controller;

import com.ITCube.Data.model.Room;
import com.ITCube.Room.service.RoomService;
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
@RequestMapping(path="/rooms")
@Slf4j @Validated
public class RoomController {

    private final RoomService service;

    @Autowired
    public RoomController(RoomService service) {
        this.service = service;
    }

    @ResponseStatus(value= HttpStatus.OK)
    @GetMapping
    public List<Room> findAllRooms(){
        log.info("findAllRooms");
        return service.findAllRooms();
    }

    @ResponseStatus(value= HttpStatus.FOUND)
    @GetMapping("/{id}")
    public Room findRoomById(@PathVariable long id){
        log.info("Find Room "+ id);
        return service.findRoomById(id);
    }

    @ResponseStatus(value= HttpStatus.CREATED)
    @PostMapping
    public Room createRoom(@RequestBody @Valid Room room){
        log.info("Create ner Room");
        return service.saveRoom(room);
    }

    @ResponseStatus(value= HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable long id){
        log.info("Delete room "+ id);
        service.deleteRoom(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value= HttpStatus.OK)
    public Room updateRoom(@PathVariable long id, @RequestBody @Valid Room room){
        log.info("Update room "+ id);
        return service.updateRoom(id,room);
    }

}
