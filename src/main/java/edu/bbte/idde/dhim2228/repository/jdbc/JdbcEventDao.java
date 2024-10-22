package edu.bbte.idde.dhim2228.repository.jdbc;

import edu.bbte.idde.dhim2228.model.EventModel;
import edu.bbte.idde.dhim2228.repository.EventDao;
import edu.bbte.idde.dhim2228.repository.exceptions.NotFoundEventException;

import java.util.Collection;
import java.util.List;

public class JdbcEventDao implements EventDao {
    @Override
    public EventModel getEventById(Long id) throws NotFoundEventException {
        return null;
    }

    @Override
    public Collection<EventModel> getAllEvents() {
        return List.of();
    }

    @Override
    public void createEvent(EventModel event) {

    }

    @Override
    public void updateEvent(Long id, EventModel event) throws NotFoundEventException {

    }

    @Override
    public void deleteEvent(Long id) throws NotFoundEventException {

    }
}
