package edu.bbte.idde.dhim2228.dao.implementation;

import edu.bbte.idde.dhim2228.dao.EventDao;
import edu.bbte.idde.dhim2228.model.EventModel;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryEventDaoImpl implements EventDao {
    private final ConcurrentHashMap<Long, EventModel> events = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(0);

    @Override
    public EventModel getEventById(Long id) {
        return events.get(id);
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
    public void updateEvent(EventModel event) {
        System.out.println("Updating event");
    }

    @Override
    public void deleteEvent(Long id) {
        events.remove(id);
    }
}
