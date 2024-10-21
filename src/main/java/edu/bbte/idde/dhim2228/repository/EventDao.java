package edu.bbte.idde.dhim2228.repository;

import edu.bbte.idde.dhim2228.repository.exceptions.NotFoundEventException;
import edu.bbte.idde.dhim2228.model.EventModel;

import java.util.Collection;

public interface EventDao {
    EventModel getEventById(Long id) throws NotFoundEventException;
    Collection<EventModel> getAllEvents();
    void createEvent(EventModel event);
    void updateEvent(Long id, EventModel event) throws NotFoundEventException;
    void deleteEvent(Long id)  throws NotFoundEventException;
}
