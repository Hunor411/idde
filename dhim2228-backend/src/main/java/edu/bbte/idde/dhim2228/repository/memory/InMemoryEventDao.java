package edu.bbte.idde.dhim2228.repository.memory;

import edu.bbte.idde.dhim2228.repository.EventDao;
import edu.bbte.idde.dhim2228.model.EventModel;
import edu.bbte.idde.dhim2228.repository.exceptions.RepositoryException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class InMemoryEventDao implements EventDao {
    private final Map<Long, EventModel> events = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(0);

    @Override
    public EventModel getEventById(Long id) {
        log.info("Getting event by id: {}", id);
        return events.get(id);
    }

    @Override
    public Collection<EventModel> getAllEvents() {
        log.info("Finding all events...");
        log.debug(events.toString());
        return new ArrayList<>(events.values());
    }

    @Override
    public void createEvent(EventModel event) {
        log.info("Creating event: {}", event);
        Long newId = counter.incrementAndGet();
        event.setId(newId);
        event.setLastUpdatedAt(String.valueOf(new Date()));
        events.put(newId, event);
        log.info("Event {} created", events);
    }

    @Override
    public void updateEvent(Long id, EventModel event) throws RepositoryException {
        log.info("Updating event with id: {}", id);
        if (!events.containsKey(id)) {
            log.error("Updating event with id {} failed", id);
            throw new RepositoryException("Event with id " + id + " not found");
        }

        event.setLastUpdatedAt(String.valueOf(new Date()));
        events.put(id, event);
        log.info("Event with id {} updated successfully", id);
    }

    @Override
    public void deleteEvent(Long id) throws RepositoryException {
        log.info("Deleting event: {}", id);
        EventModel eventId = events.remove(id);
        if (eventId == null) {
            log.error("Deleting event failed id:{}: ", id);
            throw new RepositoryException("Event with id " + id + " not found");
        }
    }
}
