package edu.bbte.idde.dhim2228.repository;

import edu.bbte.idde.dhim2228.model.EventModel;
import edu.bbte.idde.dhim2228.repository.exceptions.RepositoryException;

import java.util.Collection;

public interface EventDao {
    EventModel getEventById(Long id) throws RepositoryException;

    Collection<EventModel> getAllEvents() throws RepositoryException;

    void createEvent(EventModel event) throws RepositoryException;

    void updateEvent(Long id, EventModel event) throws RepositoryException;

    void deleteEvent(Long id)  throws RepositoryException;
}
