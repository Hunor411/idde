package edu.bbte.idde.dhim2228.service;

import edu.bbte.idde.dhim2228.model.EventModel;

import java.util.Collection;

public interface EventService {
    EventModel getEventById(Long id) throws NotFoundEventException;
    Collection<EventModel> getAllEvents();
    void createEvent(EventModel event);
    void updateEvent(EventModel event) throws NotFoundEventException;
    void deleteEvent(Long id) throws NotFoundEventException;

}
