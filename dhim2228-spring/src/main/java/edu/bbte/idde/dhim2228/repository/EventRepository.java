package edu.bbte.idde.dhim2228.repository;

import edu.bbte.idde.dhim2228.model.Event;
import edu.bbte.idde.dhim2228.repository.exceptions.RepositoryException;

import java.util.Collection;

public interface EventRepository {
    Event getEventById(Long id) throws RepositoryException;

    Collection<Event> getAllEvents() throws RepositoryException;

    Collection<Event> searchEvents(String name, String location) throws RepositoryException;

    void createEvent(Event event) throws RepositoryException;

    void updateEvent(Long id, Event event) throws RepositoryException;

    void deleteEvent(Long id)  throws RepositoryException;
}
