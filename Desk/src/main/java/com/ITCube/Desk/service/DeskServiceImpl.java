package com.ITCube.Desk.service;

import com.ITCube.Data.model.Desk;
import com.ITCube.Data.model.Room;
import com.ITCube.Data.repository.DeskRepository;
import com.ITCube.Data.repository.RoomRepository;
import com.ITCube.Desk.exception.DeskNotFoundException;
import com.ITCube.Desk.exception.RoomNotFoundException;
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
public class DeskServiceImpl implements DeskService {

    private final DeskRepository rep;

    private final RoomRepository roomRep;

    @Autowired
    public DeskServiceImpl(DeskRepository rep, RoomRepository roomRep) {
        this.rep = rep;
        this.roomRep = roomRep;
    }

    @Override
    public List<Desk> findAllDesk() {
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
    public Desk createDesk(Desk desk) {
        log.info("Save new desk");
        long room_id=desk.getRoom().getId();
        Room r=roomRep.findById(room_id)
                .orElseThrow(()-> new RoomNotFoundException("Room "+ room_id+" not found"));
        if(!r.equals(desk.getRoom())){
            throw new RoomNotFoundException("Room insert is wrong");
        }
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
