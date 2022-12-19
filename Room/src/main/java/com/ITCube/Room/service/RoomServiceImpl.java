package com.ITCube.Room.service;

import com.ITCube.Data.model.Room;
import com.ITCube.Room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matteo Rosso
 */

@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService{

    private RoomRepository rep;

    @Override
    public List<Room> findAllRooms() {
        List<Room> result=new ArrayList<Room>();
        rep.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Room findRoomById(Long id) {
        return null;
    }

    @Override
    public Room saveRoom(Room room) {
        return null;
    }

    @Override
    public void deleteRoom(Long id) {

    }

    @Override
    public Room updateRoom(long id, Room room) {
        return null;
    }

}
