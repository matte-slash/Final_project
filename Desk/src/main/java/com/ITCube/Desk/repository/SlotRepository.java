package com.ITCube.Desk.repository;


import com.ITCube.Data.model.Slot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Matteo Rosso
 */

@Repository
public interface SlotRepository extends CrudRepository<Slot,Long> {
}
