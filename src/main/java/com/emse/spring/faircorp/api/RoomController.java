package com.emse.spring.faircorp.api;


import com.emse.spring.faircorp.DAO.HeaterDAO;
import com.emse.spring.faircorp.DAO.RoomDAO;
import com.emse.spring.faircorp.DAO.WindowDAO;
import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.Window;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "http://localhost:8081")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController // (1)
@RequestMapping("/api/rooms") // (2)
@Transactional // (3)
public class RoomController {
    RoomDAO roomDao;
    HeaterDAO heaterDAO;
    WindowDAO windowDAO;

    public RoomController(RoomDAO roomDao, HeaterDAO heaterDAO, WindowDAO windowDAO) {

        this.roomDao = roomDao;
        this.heaterDAO = heaterDAO;
        this.windowDAO = windowDAO;
    }

    @GetMapping // (5)
    public List<RoomDto> finAll(){
        return roomDao.findAll().stream().map(RoomDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public RoomDto finById(@PathVariable Long id){
        return roomDao.findById(id).map(RoomDto::new).orElse(null);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {


        List<Heater> heaters = roomDao.findHeatersByRoomId(id);
        if (heaters.size()>0) {
            for (int i = 0; i < heaters.size(); i++) {
                heaterDAO.deleteById(heaters.get(i).getId());
            }
        }
        List<Window> windows = roomDao.findWindowsByRoomId(id);
        if (windows.size()>0) {
            for (int i = 0; i < windows.size(); i++) {
                windowDAO.deleteById(windows.get(i).getId());
            }
        }
        roomDao.deleteById(id);
    }
}
