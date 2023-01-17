package com.ITCube.Desk.controller;

import com.ITCube.Data.model.Desk;
import com.ITCube.Desk.service.DeskService;
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
@RequestMapping(path="/desks")
@Slf4j
@Validated
public class DeskController {

    private final DeskService service;

    @Autowired
    public DeskController(DeskService service) {
        this.service = service;
    }

    @ResponseStatus(value= HttpStatus.OK)
    @GetMapping
    public List<Desk> findAllDesks(@RequestParam(name="room",required = false) Long room){
        if(room!=null){
            log.info("Find all desk by room " + room);
            return service.findAllDeskByRoom(room);
        }

        log.info("Find all desk");
        return service.findAllDesk();
    }

    @ResponseStatus(value= HttpStatus.FOUND)
    @GetMapping("/{id")
    public Desk findDeskById(@PathVariable long id){
        log.info("findDeskById " +id);
        return service.findDeskById(id);
    }

    @ResponseStatus(value= HttpStatus.CREATED)
    @PostMapping
    public Desk createDesk(@RequestBody @Valid Desk desk){
        log.info("Create new desk");
        return service.createDesk(desk);
    }

    @ResponseStatus(value= HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteDesk(@PathVariable long id){
        log.info("Deleting desk "+ id);
        service.deleteDesk(id);
    }

    @ResponseStatus(value= HttpStatus.OK)
    @PutMapping("/{id")
    public Desk update(@PathVariable long id, @RequestBody @Valid Desk desk){
        log.info("Update desk "+id);
        return service.updateDesk(id, desk);
    }

}
