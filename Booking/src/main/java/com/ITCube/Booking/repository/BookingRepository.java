package com.ITCube.Booking.repository;

import com.ITCube.Data.model.Booking;
import com.ITCube.Data.model.Desk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Matteo Rosso
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value= "select * from booking as b"
            + "where b.desk_id.id= ?1 ", nativeQuery = true)        // todo: check
    List<Booking> findAllBookingsByDesk(long id_Desk);

    @Query(value= "select * from booking as b"
            + "where b.user_id.id = ?1 ", nativeQuery = true)       //todo: check
    List<Booking> findAllBookingsByUser(long id_User);

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
