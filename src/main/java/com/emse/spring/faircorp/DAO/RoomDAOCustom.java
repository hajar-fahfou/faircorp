package com.emse.spring.faircorp.DAO;

import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Window;

import java.util.List;

public interface RoomDAOCustom {
    List<Room> findRoomByName(String name);
    List<Heater> findHeatersByRoomId(Long id);
    List<Window> findWindowsByRoomId(Long id);
}
