package com.emse.spring.faircorp.DAO;

import com.emse.spring.faircorp.model.Building;
import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Window;

import java.util.List;

public interface BuildingDAOCustom {
    List<Window> findWindowsByBuildingId(Long id);
    List<Heater> findHeatersByBuildingId(Long id);
    List<Room> findRoomsByBuildingId(Long id);
}
