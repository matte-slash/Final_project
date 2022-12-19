package com.ITCube.Room.service;

import com.ITCube.Data.model.Room;

import java.util.List;

/**
 * @author Matteo Rosso
 */

public interface RoomService {

    List<Room> findAllRooms();

    Room findRoomById(Long id);

    Room saveRoom(Room room);

    void deleteRoom(Long id);

    Room updateRoom(long id, Room room);



}
