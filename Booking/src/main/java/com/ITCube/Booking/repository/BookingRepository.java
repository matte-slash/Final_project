package com.ITCube.Booking.repository;

import com.ITCube.Data.model.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Matteo Rosso
 */
@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {
}
