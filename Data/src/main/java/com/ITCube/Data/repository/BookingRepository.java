package com.ITCube.Data.repository;

import com.ITCube.Data.model.Booking;
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

    @Query(value= "select * from booking as b "
            + "where b.desk_id= ?1 ", nativeQuery = true)
    List<Booking> findAllBookingsByDesk(long id_Desk);

    @Query(value= "select * from booking as b "
            + "where b.user_id = ?1 ", nativeQuery = true)
    List<Booking> findAllBookingsByUser(long id_User);


    @Query(value= "select * from booking as b "
            + "where b.desk_id= ?1 AND b.user_id= ?2 ", nativeQuery = true)
    List<Booking> query(Long deskID, Long userID);


    @Query(value = "select * from booking as b "
            + "where b.user_id = ?1 AND ((b.start_date BETWEEN ?2 and ?3)  "
            + "or (b.end_date BETWEEN ?2 and ?3)) ", nativeQuery = true)
    List<Booking> checkUserBookings(Long user, LocalDateTime start, LocalDateTime end);


    @Query(value = "select * from booking as b "
            + "where b.desk_id = ?1 AND ((b.start_date BETWEEN ?2 and ?3)  "
            + "or (b.end_date BETWEEN ?2 and ?3)) ", nativeQuery = true)
    List<Booking> concurrentBookings(Long desk, LocalDateTime start, LocalDateTime end);

}