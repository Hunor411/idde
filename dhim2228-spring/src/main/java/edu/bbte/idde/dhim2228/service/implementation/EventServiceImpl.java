package edu.bbte.idde.dhim2228.service.implementation;

import edu.bbte.idde.dhim2228.model.Event;
import edu.bbte.idde.dhim2228.repository.EventRepository;
import edu.bbte.idde.dhim2228.repository.exceptions.RepositoryException;
import edu.bbte.idde.dhim2228.service.EventService;
import edu.bbte.idde.dhim2228.controller.exceptions.NotFoundException;
import edu.bbte.idde.dhim2228.service.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public void create(Event event) {
        try {
            eventRepository.createEvent(event);
        } catch (RepositoryException e) {
            log.error("Error while creating event: {}", event, e);
            throw new ServiceException("Error creating event", e);
        }
    }

    @Override
    public void update(Long id, Event event) {
        try {
            eventRepository.updateEvent(id, event);
        } catch (RepositoryException e) {
            log.error("Error while updating event with id: {}", id, e);
            throw new ServiceException("Error updating event with id: " + id, e);
        }
    }

    @Override
    public Collection<Event> findAll() {
        try {
            return new ArrayList<>(eventRepository.getAllEvents());
        } catch (RepositoryException e) {
            log.error("Error while retrieving all events", e);
            throw new ServiceException("Error retrieving all events", e);
        }
    }

    @Override
    public Event findById(Long id) {
        try {
            Event event = eventRepository.getEventById(id);
            if (event == null) {
                log.error("Error while retrieving event with id: {}", id);
                throw new NotFoundException("Event not found with id: " + id);
            }
            return event;
        } catch (RepositoryException e) {
            log.error("Error while getting event by id: {}", id, e);
            throw new ServiceException("Error getting event by id: " + id, e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            eventRepository.deleteEvent(id);
        } catch (RepositoryException e) {
            log.error("Error while deleting event with id: {}", id, e);
            throw new ServiceException("Error deleting event with id: " + id, e);
        }
    }

    @Override
    public Event findClosestEvent() {
        log.info("Fetching the closest upcoming event...");
        try {
            return eventRepository.getAllEvents().stream()
                    .filter(event -> event.getDate().isAfter(LocalDateTime.now()))
                    .min(Comparator.comparing(Event::getDate))
                    .orElse(null);
        } catch (RepositoryException e) {
            log.error("Error while fetching closest upcoming event", e);
            throw new ServiceException("Error while fetching closest upcoming event", e);
        }
    }
}
