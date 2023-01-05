package com.ITCube.Desk.service;

import com.ITCube.Data.model.Desk;

import java.util.List;

/**
 * @author Matteo Rosso
 */

public interface DeskService {

    List<Desk> findAllDesk();

    List<Desk> findAllDeskByRoom(long roomId);

    Desk findDeskById(long id);

    Desk createDesk(Desk desk);

    void deleteDesk(long id);

    Desk updateDesk(long id, Desk desk);

}
