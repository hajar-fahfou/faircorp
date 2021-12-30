package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.DAO.HeaterDAO;
import com.emse.spring.faircorp.DAO.RoomDAO;
import com.emse.spring.faircorp.DAO.WindowDAO;
import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Window;
import com.emse.spring.faircorp.model.WindowStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "http://localhost:8081")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/windows")
@Transactional
public class WindowController {

    private final WindowDAO windowDao;
    private final RoomDAO roomDao;
    HeaterDAO heaterDAO;

    public WindowController(WindowDAO windowDao, RoomDAO roomDao, HeaterDAO heaterDAO) { // (4)
        this.windowDao = windowDao;
        this.roomDao = roomDao;
        this.heaterDAO = heaterDAO;

    }

    @GetMapping // (5)
    public List<WindowDto> findAll() {
        return windowDao.findAll().stream().map(WindowDto::new).collect(Collectors.toList());  // (6)
    }

    @GetMapping(path = "/{id}")
    public WindowDto findById(@PathVariable Long id) {
        return windowDao.findById(id).map(WindowDto::new).orElse(null); // (7)
    }

    @PutMapping(path = "/{id}/switch")
    public WindowDto switchStatus(@PathVariable Long id) {
        Window window = windowDao.findById(id).orElseThrow(IllegalArgumentException::new);
        window.setWindowStatus(window.getWindowStatus() == WindowStatus.OPEN ? WindowStatus.CLOSED: WindowStatus.OPEN);
        return new WindowDto(window);
    }

    @PostMapping // (8)
    public WindowDto create(@RequestBody WindowDto dto) {
        Room room = roomDao.getOne(dto.getRoomId());
        Window window = null;
        if (dto.getId() == null) {
            window = windowDao.save(new Window(room, dto.getName(), dto.getWindowStatus()));
        }
        else {
            window = windowDao.getOne(dto.getId());  // (9)
            window.setWindowStatus(dto.getWindowStatus());
            window.setName(dto.getName());
        }
        return new WindowDto(window);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        List<Heater> heaters = windowDao.findHeatersByWindowId(id);
        if (heaters.size()>0) {
            for (int i = 0; i < heaters.size(); i++) {
                heaterDAO.deleteById(heaters.get(i).getId());
            }
        }
        windowDao.deleteById(id);
    }
}