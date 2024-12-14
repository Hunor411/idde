package edu.bbte.idde.dhim2228.service.implementation;

import edu.bbte.idde.dhim2228.dto.event.EventRequestDto;
import edu.bbte.idde.dhim2228.dto.event.EventResponseDto;
import edu.bbte.idde.dhim2228.mapper.EventMapper;
import edu.bbte.idde.dhim2228.model.Event;
import edu.bbte.idde.dhim2228.repository.EventRepository;
import edu.bbte.idde.dhim2228.service.EventService;
import edu.bbte.idde.dhim2228.service.exceptions.NotFoundException;
import edu.bbte.idde.dhim2228.service.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public Long save(EventRequestDto eventRequestDto) throws ServiceException {
        log.info("Saving event: {}", eventRequestDto);
        return eventRepository.save(eventMapper.toEntityDto(eventRequestDto)).getId();
    }

    private void checkExistsEventById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            log.warn("Event with id {} not found.", id);
            throw new NotFoundException("Event not found with id: " + id);
        }
    }

    @Override
    public void update(Long id, EventRequestDto eventRequestDto) {
        log.info("Updating event: {}", eventRequestDto);
        checkExistsEventById(id);
        Event eventToUpdate = eventMapper.toEntityDto(eventRequestDto);
        eventToUpdate.setId(id);
        eventRepository.save(eventToUpdate);
    }

    @Override
    public Collection<EventResponseDto> getAllEvents() {
        log.info("Getting all events");
        return eventMapper.toResponseDtoList(new ArrayList<>(eventRepository.findAll()));
    }

    @Override
    public EventResponseDto getEventById(Long id) {
        log.info("Getting event with id: {}", id);
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            log.warn("Event with id {} not found.", id);
            throw new NotFoundException("Event not found with id: " + id);
        }
        return eventMapper.toResponseDto(event.get());
    }

    @Override
    public void deleteEvent(Long id) {
        log.info("Deleting event with id: {}", id);
        checkExistsEventById(id);
        eventRepository.deleteById(id);
    }

    @Override
    public Collection<EventResponseDto> searchEvents(String name, String location) throws ServiceException {
        Collection<Event> events = eventRepository
                .findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(name, location);
        if (events == null || events.isEmpty()) {
            throw new NotFoundException("No events found for the given name and location.");
        }

        return eventMapper.toResponseDtoList(events);
    }

    @Override
    public EventResponseDto findClosestEvent() {
        log.info("Fetching the closest upcoming event...");
        Optional<Event> closestEvent = eventRepository.findFirstByDateAfterOrderByDateAsc(LocalDateTime.now());
        if (closestEvent.isEmpty()) {
            log.warn("Closest event not found");
            throw new NotFoundException("Closest event not found");
        }

        return eventMapper.toResponseDto(closestEvent.get());
    }
}
