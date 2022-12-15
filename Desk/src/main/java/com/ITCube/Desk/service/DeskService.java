package com.ITCube.Desk.service;

import com.ITCube.Data.model.Desk;
import com.ITCube.Data.model.Room;



import java.util.List;

/**
 * @author Matteo Rosso
 */

public interface DeskService {

    List<Desk> findAlSlot();

    List<Desk> findAllSlotByRoom(Room room);


    Desk findDeskById(long id);

    Desk createSlot(Desk slot);

    void deleteSlot(Desk slot);

    void deleteAllSlot();

    Desk updateSlot(long id, Desk slot);

}
