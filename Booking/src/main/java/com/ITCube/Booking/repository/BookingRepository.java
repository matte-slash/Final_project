package com.ITCube.Booking.repository;

import com.ITCube.Data.model.Booking;
import com.ITCube.Data.model.Desk;
import com.ITCube.Data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Matteo Rosso
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllBookingsByDesk(long id_Desk);

    List<Booking> findAllBookingsByUser(long id_User);

}
