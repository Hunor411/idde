package edu.bbte.idde.dhim2228.service.implementation;

import edu.bbte.idde.dhim2228.dto.EventRequestDto;
import edu.bbte.idde.dhim2228.dto.EventResponseDto;
import edu.bbte.idde.dhim2228.dto.EventShortResponseDto;
import edu.bbte.idde.dhim2228.mapper.EventMapper;
import edu.bbte.idde.dhim2228.model.Event;
import edu.bbte.idde.dhim2228.repository.EventRepository;
import edu.bbte.idde.dhim2228.repository.exceptions.RepositoryException;
import edu.bbte.idde.dhim2228.service.EventService;
import edu.bbte.idde.dhim2228.service.exceptions.NotFoundException;
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
    private final EventMapper eventMapper;

    @Override
    public Long save(EventRequestDto eventRequestDto) throws ServiceException {
        try {
            return eventRepository.createEvent(eventMapper.toEntityDto(eventRequestDto));
        } catch (RepositoryException e) {
            log.error("Error while creating event: {}", eventRequestDto, e);
            throw new ServiceException("Error creating event", e);
        }
    }

    private void checkExistsEventById(Long id) {
        try {
            Event event = eventRepository.getEventById(id);
            if (event == null) {
                log.warn("Event with id {} not found.", id);
                throw new NotFoundException("Event not found with id: " + id);
            }
        } catch (RepositoryException e) {
            log.error("Error while retrieving event with id: {}", id, e);
            throw new ServiceException("Error retrieving event with id: " + id, e);
        }
    }

    @Override
    public void update(Long id, EventRequestDto eventRequestDto) {
        try {
            checkExistsEventById(id);
            Event eventToUpdate = eventMapper.toEntityDto(eventRequestDto);
            eventToUpdate.setId(id);
            eventRepository.updateEvent(id, eventToUpdate);
        } catch (RepositoryException e) {
            log.error("Error while updating event with id: {}", id, e);
            throw new ServiceException("Error updating event with id: " + id, e);
        }
    }

    @Override
    public Collection<EventShortResponseDto> getAllEvents() {
        try {
            return eventMapper.toShortResponseDtoList(new ArrayList<>(eventRepository.getAllEvents()));
        } catch (RepositoryException e) {
            log.error("Error while retrieving all events", e);
            throw new ServiceException("Error retrieving all events", e);
        }
    }

    @Override
    public EventResponseDto getEventById(Long id) {
        try {
            Event event = eventRepository.getEventById(id);
            if (event == null) {
                log.error("Error while retrieving event with id: {}", id);
                throw new NotFoundException("Event not found with id: " + id);
            }
            return eventMapper.toResponseDto(event);
        } catch (RepositoryException e) {
            log.error("Error while getting event by id: {}", id, e);
            throw new ServiceException("Error getting event by id: " + id, e);
        }
    }

    @Override
    public void deleteEvent(Long id) {
        try {
            checkExistsEventById(id);
            eventRepository.deleteEvent(id);
        } catch (RepositoryException e) {
            log.error("Error while deleting event with id: {}", id, e);
            throw new ServiceException("Error deleting event with id: " + id, e);
        }
    }

    @Override
    public Collection<EventShortResponseDto> searchEvents(String name, String location) throws ServiceException {
        Collection<Event> events = eventRepository.searchEvents(name, location);
        if (events == null || events.isEmpty()) {
            throw new NotFoundException("No events found for the given name and location.");
        }

        return eventMapper.toShortResponseDtoList(events);
    }

    @Override
    public EventResponseDto findClosestEvent() {
        log.info("Fetching the closest upcoming event...");
        try {
            Event closestEvent = eventRepository.getAllEvents().stream()
                    .filter(event -> event.getDate().isAfter(LocalDateTime.now()))
                    .min(Comparator.comparing(Event::getDate))
                    .orElse(null);
            if (closestEvent == null) {
                throw new NotFoundException("No closest upcoming event.");
            }

            return eventMapper.toResponseDto(closestEvent);
        } catch (RepositoryException e) {
            log.error("Error while fetching closest upcoming event", e);
            throw new ServiceException("Error while fetching closest upcoming event", e);
        }
    }
}
