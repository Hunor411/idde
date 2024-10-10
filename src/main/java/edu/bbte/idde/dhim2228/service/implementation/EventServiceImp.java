package edu.bbte.idde.dhim2228.service.implementation;

import edu.bbte.idde.dhim2228.dao.EventDao;
import edu.bbte.idde.dhim2228.model.EventModel;
import edu.bbte.idde.dhim2228.service.EventService;
import edu.bbte.idde.dhim2228.service.NotFoundEventException;

import java.util.Collection;

public class EventServiceImp implements EventService {
    private final EventDao eventDao;

    public EventServiceImp(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public EventModel getEventById(Long id) throws NotFoundEventException {
        EventModel eventModel = eventDao.getEventById(id);
        if (eventModel == null) {
            throw new NotFoundEventException("Event with id " + id + " not found");
        }

        return eventModel;
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
        EventModel oldEvent = eventDao.getEventById(event.getId());
        if (oldEvent == null) {
            throw new NotFoundEventException("Event with id " + event.getId() + " not found");
        }
        eventDao.updateEvent(event);
    }

    @Override
    public void deleteEvent(Long id) throws NotFoundEventException {
        EventModel event = eventDao.getEventById(id);
        if (event == null) {
            throw new NotFoundEventException("Event with id " + id + " not found");
        }

        eventDao.deleteEvent(id);
    }
}
