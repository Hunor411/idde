package edu.bbte.idde.dhim2228.service.implementation;

import edu.bbte.idde.dhim2228.dao.EventDao;
import edu.bbte.idde.dhim2228.model.EventModel;
import edu.bbte.idde.dhim2228.service.EventService;
import edu.bbte.idde.dhim2228.dao.exceptions.NotFoundEventException;

import java.util.Collection;

public class EventServiceImp implements EventService {
    private final EventDao eventDao;

    public EventServiceImp(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public EventModel getEventById(Long id) throws NotFoundEventException {
        return eventDao.getEventById(id);
    }

    @Override
    public Collection<EventModel> getAllEvents() {
        return eventDao.getAllEvents();
    }

    @Override
    public void createEvent(EventModel event) {
        eventDao.createEvent(event);
    }

    @Override
    public void updateEvent(EventModel event) throws NotFoundEventException {
        eventDao.updateEvent(event);
    }

    @Override
    public void deleteEvent(Long id) throws NotFoundEventException {
        eventDao.deleteEvent(id);
    }
}
