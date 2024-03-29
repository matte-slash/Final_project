package com.ITCube.Data.repository;

import com.ITCube.Data.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Matteo Rosso
 */

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    User findUserByEmail(String email);

}
