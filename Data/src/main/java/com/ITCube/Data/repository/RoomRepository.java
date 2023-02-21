package com.ITCube.Data.repository;

import com.ITCube.Data.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * author Matteo Rosso
 */
@Repository
public interface RoomRepository extends CrudRepository<Room,Long> {
}
