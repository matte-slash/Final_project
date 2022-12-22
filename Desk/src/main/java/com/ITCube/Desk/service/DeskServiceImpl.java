package com.ITCube.Desk.service;

import com.ITCube.Data.model.Desk;
import com.ITCube.Data.model.Room;
import com.ITCube.Desk.exception.DeskNotFoundException;
import com.ITCube.Desk.exception.IllegalDateTimeException;
import com.ITCube.Desk.repository.DeskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Matteo Rosso
 */
@Slf4j @RequiredArgsConstructor
@Service
public class DeskServiceImpl implements DeskService {

    private DeskRepository rep;
    private Clock clock;
    @Override
    public List<Desk> findAlDesk() {
        log.info("Find all desk");
        List<Desk> result=new ArrayList<>();
        rep.findAll().forEach(result::add);
        return result;
    }

    @Override
    public List<Desk> findAllDeskByRoom(long roomId) {
        log.info("Find all desk by ROOM "+ roomId);
        return rep.findDeskByRoom(roomId);
    }

    @Override
    public Desk findDeskById(long id) {
        log.info("Find desk by id " + id);
        return rep.findById(id).orElseThrow(()->new DeskNotFoundException("Desk "+ id + " not found"));
    }

    @Override
    public List<Desk> findAllDeskAvailable(LocalDateTime start, LocalDateTime end) {
        log.info("Find all desk available");
        if(end.isBefore(start) || start.isBefore(LocalDateTime.now(clock))){
            throw new IllegalDateTimeException("Date insert error");
        }
        return rep.findDeskAvailable(start,end);
    }

    @Override
    public Desk createDesk(Desk desk) {
        log.info("Save new desk");
        return rep.save(desk);
    }

    @Override
    public void deleteDesk(long id) {
        log.info("Delete desk "+id);
        rep.findById(id).orElseThrow(()->new DeskNotFoundException("Desk "+ id+ " not found"));
        rep.deleteById(id);
    }

    @Override
    public Desk updateDesk(long id, Desk desk) {
        log.info("Update desk "+id);
        Desk result=rep.findById(id).orElseThrow(()->new DeskNotFoundException("Desk "+ id+ " not found"));
        result.setDeskName(desk.getDeskName());
        result.setRoom(desk.getRoom());
        return rep.save(result);
    }
}
