package edu.bbte.idde.dhim2228.repository.memory;

import edu.bbte.idde.dhim2228.model.Event;
import edu.bbte.idde.dhim2228.repository.EventRepository;
import edu.bbte.idde.dhim2228.repository.exceptions.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
@Profile("dev")
public class InMemoryEventDao implements EventRepository {
    private final Map<Long, Event> events = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(0);

    @Override
    public Event getEventById(Long id) {
        log.info("Getting event by id: {}", id);
        return events.get(id);
    }

    @Override
    public Collection<Event> getAllEvents() {
        log.info("Finding all events...");
        log.debug(events.toString());
        return new ArrayList<>(events.values());
    }

    @Override
    public Collection<Event> searchEvents(String name, String location) throws RepositoryException {
        log.info("Searching events by name: {} and location: {}", name, location);
        return events.values().stream()
                .filter(event -> name == null || event.getName().equalsIgnoreCase(name))
                .filter(event -> location == null || event.getLocation().equalsIgnoreCase(location))
                .toList();
    }

    @Override
    public Long createEvent(Event event) {
        log.info("Creating event: {}", event);
        Long newId = counter.incrementAndGet();
        event.setId(newId);
        events.put(newId, event);
        log.info("Event {} created", events);
        return newId;
    }

    @Override
    public void updateEvent(Long id, Event event) throws RepositoryException {
        log.info("Updating event with id: {}", id);
        if (!events.containsKey(id)) {
            log.error("Updating event with id {} failed", id);
            throw new RepositoryException("Event with id " + id + " not found");
        }

        events.put(id, event);
        log.info("Event with id {} updated successfully", id);
    }

    @Override
    public void deleteEvent(Long id) throws RepositoryException {
        log.info("Deleting event: {}", id);
        Event eventId = events.remove(id);
        if (eventId == null) {
            log.error("Deleting event failed id:{}: ", id);
            throw new RepositoryException("Event with id " + id + " not found");
        }
    }
}
