package com.ITCube.Desk.service;

import com.ITCube.Data.model.Desk;
import com.ITCube.Data.model.Room;


import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Matteo Rosso
 */

public interface DeskService {

    List<Desk> findAlDesk();

    List<Desk> findAllDeskByRoom(Room room);

    Desk findDeskById(long id);

    List<Desk> findAllDeskAvailable(LocalDateTime start, LocalDateTime end);

    Desk createDesk(Desk desk);

    void deleteDesk(long id);

    Desk updateDesk(long id, Desk desk);

}
