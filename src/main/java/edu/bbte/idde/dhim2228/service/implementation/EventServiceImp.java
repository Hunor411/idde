package edu.bbte.idde.dhim2228.service.implementation;

import edu.bbte.idde.dhim2228.repository.DaoFactory;
import edu.bbte.idde.dhim2228.repository.EventDao;
import edu.bbte.idde.dhim2228.model.EventModel;
import edu.bbte.idde.dhim2228.service.EventService;
import edu.bbte.idde.dhim2228.service.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.Collection;

public class EventServiceImp implements EventService {
    private final EventDao eventDao;

    public EventServiceImp() {
        eventDao = DaoFactory.getInstance().getEventDao();
    }

    @Override
    public EventModel getEventById(Long id) throws ServiceException {
        return eventDao.getEventById(id);
    }

    @Override
    public Collection<EventModel> getAllEvents() {
        return new ArrayList<>(eventDao.getAllEvents());
    }

    @Override
    public void createEvent(EventModel event) throws ServiceException {
        eventDao.createEvent(event);
    }

    @Override
    public void updateEvent(Long id, EventModel event) throws ServiceException {
        eventDao.updateEvent(id, event);
    }

    @Override
    public void deleteEvent(Long id) throws ServiceException {
        eventDao.deleteEvent(id);
    }
}
