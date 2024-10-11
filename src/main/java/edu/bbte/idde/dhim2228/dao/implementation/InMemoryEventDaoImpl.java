package edu.bbte.idde.dhim2228.dao.implementation;

import edu.bbte.idde.dhim2228.dao.EventDao;
import edu.bbte.idde.dhim2228.dao.exceptions.NotFoundEventException;
import edu.bbte.idde.dhim2228.model.EventModel;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryEventDaoImpl implements EventDao {
    private final ConcurrentHashMap<Long, EventModel> events = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(0);

    @Override
    public EventModel getEventById(Long id) throws NotFoundEventException {
        EventModel event = events.get(id);
        if (event == null) {
            throw new NotFoundEventException("Event with id " + id + " not found");
        }
        return event;
    }

    @Override
    public Collection<EventModel> getAllEvents() {
        return events.values();
    }

    @Override
    public void createEvent(EventModel event) {
        Long newId = counter.incrementAndGet();
        event.setId(newId);
        events.put(newId, event);
    }

    @Override
    public void updateEvent(EventModel event) throws NotFoundEventException {
        EventModel oldEvent = getEventById(event.getId());
        if (oldEvent == null) {
            throw new NotFoundEventException("Event with id " + event.getId() + " not found");
        }
        System.out.println("Updating event");
    }

    @Override
    public void deleteEvent(Long id) throws NotFoundEventException {
        EventModel eventId = events.remove(id);
        if (eventId == null) {
            throw new NotFoundEventException("Event with id " + id + " not found");
        }
    }
}
