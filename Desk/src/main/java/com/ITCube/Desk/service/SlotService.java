package com.ITCube.Desk.service;

import com.ITCube.Data.model.Room;
import com.ITCube.Data.model.Slot;
import com.ITCube.Data.model.Type;


import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Matteo Rosso
 */

public interface SlotService {

    List<Slot> findAlSlot();

    List<Slot> findAllSlotByRoom(Room room);

    List<Slot> findAllSlotAvailableByRoom(Room room);

    List<Slot> findAllSlotByType(Type type);

    List<Slot> findAllSlotByDate(LocalDateTime date);

    List<Slot> findAllSlotByDesk(String desk);   // TODO check if is relevant

    Slot findSlotById(long id);

    Slot createSlot(Slot slot);

    void deleteSlot(Slot slot);

    void deleteAllSlot();

    Slot updateSlot(long id, Slot slot);

}
