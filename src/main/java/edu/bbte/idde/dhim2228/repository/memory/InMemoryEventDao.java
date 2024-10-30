package edu.bbte.idde.dhim2228.repository.memory;

import edu.bbte.idde.dhim2228.repository.EventDao;
import edu.bbte.idde.dhim2228.model.EventModel;
import edu.bbte.idde.dhim2228.repository.exceptions.RepositoryException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryEventDao implements EventDao {
    private final Map<Long, EventModel> events = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(0);

    @Override
    public EventModel getEventById(Long id) throws RepositoryException {
        EventModel event = events.get(id);
        if (event == null) {
            throw new RepositoryException("Event with id " + id + " not found");
        }
        return event;
    }

    @Override
    public Collection<EventModel> getAllEvents() {
        return new ArrayList<>(events.values());
    }

    @Override
    public void createEvent(EventModel event) {
        Long newId = counter.incrementAndGet();
        event.setId(newId);
        events.put(newId, event);
    }

    @Override
    public void updateEvent(Long id, EventModel event) throws RepositoryException {
        EventModel oldEvent = getEventById(id);
        if (oldEvent == null) {
            throw new RepositoryException("Event with id " + event.getId() + " not found");
        }

        oldEvent.setName(event.getName());
        oldEvent.setDate(event.getDate());
        oldEvent.setLocation(event.getLocation());
        oldEvent.setIsOnline(event.getIsOnline());
        oldEvent.setAttendeesCount(event.getAttendeesCount());
        oldEvent.setDescription(event.getDescription());
        events.put(id, oldEvent);
    }

    @Override
    public void deleteEvent(Long id) throws RepositoryException {
        EventModel eventId = events.remove(id);
        if (eventId == null) {
            throw new RepositoryException("Event with id " + id + " not found");
        }
    }
}
