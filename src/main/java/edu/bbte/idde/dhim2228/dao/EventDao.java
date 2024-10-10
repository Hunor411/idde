package edu.bbte.idde.dhim2228.dao;

import edu.bbte.idde.dhim2228.model.EventModel;

import java.util.Collection;

public interface EventDao {
    EventModel getEventById(Long id);
    Collection<EventModel> getAllEvents();
    void createEvent(EventModel event);
    void updateEvent(EventModel event);
    void deleteEvent(Long id);
}
