package com.emse.spring.faircorp.DAO;

import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.Window;
import java.util.List;

public interface WindowDAOCustom {
    List<Window> findRoomOpenWindows(Long id);
    List<Heater> findHeatersByWindowId(Long id);

}
