package edu.bbte.idde.dhim2228.service;

import edu.bbte.idde.dhim2228.model.EventModel;
import edu.bbte.idde.dhim2228.service.exceptions.ServiceException;

import java.util.Collection;

public interface EventService {
    EventModel getEventById(Long id) throws ServiceException;

    Collection<EventModel> getAllEvents() throws ServiceException;

    void createEvent(EventModel event) throws ServiceException;

    void updateEvent(Long id, EventModel event) throws ServiceException;
    
    void deleteEvent(Long id) throws ServiceException;
}
