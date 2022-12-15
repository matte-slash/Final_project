package com.ITCube.Desk.repository;


import com.ITCube.Data.model.Desk;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Matteo Rosso
 */

@Repository
public interface DeskRepository extends CrudRepository<Desk,Long> {
}
