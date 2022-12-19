package com.ITCube.Desk.repository;


import com.ITCube.Data.model.Desk;
import com.ITCube.Data.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Matteo Rosso
 */

@Repository
public interface DeskRepository extends JpaRepository<Desk,Long> {

    @Query(value= "select * from desk as d"
            + "where d.room_id = ?1 ", nativeQuery = true)
    List<Desk> findDeskByRoom(Room room);
    @Query(value = "select * from desk as d "
            + "where d.id not in "
            + "("
            + "select b.desk_id "
            + "from booking as b "
            + "where (b.start_date BETWEEN ?1 and ?2) "
            + "or (b.end_date BETWEEN ?1 and  ?2) "
            + ")", nativeQuery = true)
    List<Desk> findDeskAvailable(LocalDateTime start, LocalDateTime end);

}
