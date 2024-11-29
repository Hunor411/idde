package edu.bbte.idde.dhim2228.service.implementation;

import edu.bbte.idde.dhim2228.repository.DaoFactory;
import edu.bbte.idde.dhim2228.repository.EventDao;
import edu.bbte.idde.dhim2228.model.EventModel;
import edu.bbte.idde.dhim2228.repository.exceptions.RepositoryException;
import edu.bbte.idde.dhim2228.service.EventService;
import edu.bbte.idde.dhim2228.service.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class EventServiceImp implements EventService {
    private final EventDao eventDao;

    public EventServiceImp() {
        eventDao = DaoFactory.getInstance().getEventDao();
    }

    @Override
    public EventModel getEventById(Long id) throws ServiceException {
        try {
            return eventDao.getEventById(id);
        } catch (RepositoryException e) {
            log.error("Error while getting event by id: {}", id, e);
            throw new ServiceException("Error getting event by id: " + id, e);
        }
    }

    @Override
    public Collection<EventModel> getAllEvents() {
        try {
            return new ArrayList<>(eventDao.getAllEvents());
        } catch (RepositoryException e) {
            log.error("Error while retrieving all events", e);
            throw new ServiceException("Error retrieving all events", e);
        }
    }

    @Override
    public void createEvent(EventModel event) throws ServiceException {
        try {
            eventDao.createEvent(event);
        } catch (RepositoryException e) {
            log.error("Error while creating event: {}", event, e);
            throw new ServiceException("Error creating event", e);
        }
    }

    @Override
    public void updateEvent(Long id, EventModel event) throws ServiceException {
        try {
            eventDao.updateEvent(id, event);
        } catch (RepositoryException e) {
            log.error("Error while updating event with id: {}", id, e);
            throw new ServiceException("Error updating event with id: " + id, e);
        }
    }

    @Override
    public void deleteEvent(Long id) throws ServiceException {
        try {
            eventDao.deleteEvent(id);
        } catch (RepositoryException e) {
            log.error("Error while deleting event with id: {}", id, e);
            throw new ServiceException("Error deleting event with id: " + id, e);
        }
    }
}
