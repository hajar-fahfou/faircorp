package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.DAO.BuildingDAO;
import com.emse.spring.faircorp.DAO.HeaterDAO;
import com.emse.spring.faircorp.DAO.RoomDAO;
import com.emse.spring.faircorp.DAO.WindowDAO;
import com.emse.spring.faircorp.model.Building;
import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Window;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// @CrossOrigin(origins = "http://localhost:8081")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/building")
@Transactional
public class BuildingController {

    private final BuildingDAO buildingDao;
    HeaterDAO heaterDAO;
    WindowDAO windowDAO;
    RoomDAO roomDAO;

    public BuildingController(BuildingDAO buildingDao, HeaterDAO heaterDAO, WindowDAO windowDAO, RoomDAO roomDAO) {

        this.buildingDao = buildingDao;
        this.heaterDAO = heaterDAO;
        this.windowDAO = windowDAO;
        this.roomDAO = roomDAO;
    }

    @GetMapping
    public List<BuildingDto> findAll() {
        return buildingDao.findAll().stream().map(BuildingDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{building_id}")
    public BuildingDto findById(@PathVariable Long building_id) {
        return buildingDao.findById(building_id).map(BuildingDto::new).orElse(null);
    }

    @DeleteMapping(path = "/{building_id}")
    public void delete(@PathVariable Long building_id) {
        List<Heater> heaters = buildingDao.findHeatersByBuildingId(building_id);
        if (heaters.size()>0) {
            for (int i = 0; i < heaters.size(); i++) {
                heaterDAO.deleteById(heaters.get(i).getId());
            }
        }
        List<Window> windows = buildingDao.findWindowsByBuildingId(building_id);
        if (windows.size()>0) {
            for (int i = 0; i < windows.size(); i++) {
                windowDAO.deleteById(windows.get(i).getId());
            }
        }
            List<Room> rooms = buildingDao.findRoomsByBuildingId(building_id);
        if (rooms.size()>0) {
            for (int i = 0; i < rooms.size(); i++) {
                roomDAO.deleteById(rooms.get(i).getId());
            }
        }

        buildingDao.deleteById(building_id);
    }

    @PutMapping(path = "/{building_id}")
    public BuildingDto update(@RequestBody BuildingDto dto, @PathVariable Long building_id) {
        Building building = buildingDao.findById(building_id).orElseThrow(IllegalArgumentException::new);
        building.setName(dto.getName());
        return new BuildingDto(building);
    }

    @PostMapping // (8)
    public BuildingDto create(@RequestBody BuildingDto dto) {
        Building building = null;
        if (dto.getId() == null) {
            building = buildingDao.save(new Building(dto.getId(), dto.getName()));
        } else {

                building=new Building(dto.getId(), dto.getName());

            building.setName(dto.getName());
            buildingDao.save(building);
        }
        return new BuildingDto(building);
    }
}