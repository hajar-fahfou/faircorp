package com.emse.spring.faircorp.DAO;

import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Window;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RoomDAOCustomImpl implements RoomDAOCustom{
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Room> findRoomByName(String name) {
        String jpql = "select r from Room r where r.name = :name";
        return em.createQuery(jpql, Room.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<Window> findWindowsByRoomId(Long id) {
        String jpql = "select w from Window w where w.room.id = :id";
        return em.createQuery(jpql, Window.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<Heater> findHeatersByRoomId(Long id) {
        String jpql = "select h from Heater h where h.room.id = :id";
        return em.createQuery(jpql, Heater.class)
                .setParameter("id", id)
                .getResultList();
    }

}
