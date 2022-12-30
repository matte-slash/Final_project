package com.ITCube.Room.service;

import com.ITCube.Data.model.Room;
import com.ITCube.Room.exception.RoomNotFoundException;
import com.ITCube.Room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
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
public class RoomServiceImpl implements RoomService{

    private RoomRepository rep;

    @Autowired
    public RoomServiceImpl(RoomRepository rep) {
        this.rep = rep;
    }

    @Override
    public List<Room> findAllRooms() {
        log.info("Find All Rooms");
        List<Room> result=new ArrayList<Room>();
        rep.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Room findRoomById(Long id) {
        log.info("FindRoomById"+ id);
        return rep.findById(id).orElseThrow(()->new RoomNotFoundException("Room "+ id+" not found"));
    }

    @Override
    public Room saveRoom(Room room) {
        log.info("Save Room "+ room.toString());
        return rep.save(room);
    }

    @Override
    public void deleteRoom(Long id) {
        log.info("Delete room "+id);
        rep.findById(id).orElseThrow(()->new RoomNotFoundException("Room "+ id+" not found"));
        rep.deleteById(id);
    }

    @Override
    public Room updateRoom(long id, Room room) {
        log.info("Update room "+id);
        Room result= rep.findById(id).orElseThrow(()->new RoomNotFoundException("Room "+ id+ " not found"));
        result.setAddress(room.getAddress());
        result.setName(room.getName());
        result.setTotalDesk(room.getTotalDesk());
        return rep.save(result);
    }

}
