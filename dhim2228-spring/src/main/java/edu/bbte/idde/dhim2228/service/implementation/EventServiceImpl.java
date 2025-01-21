package edu.bbte.idde.dhim2228.service.implementation;

import edu.bbte.idde.dhim2228.dto.event.EventRequestDto;
import edu.bbte.idde.dhim2228.dto.event.EventResponseDto;
import edu.bbte.idde.dhim2228.dto.event.EventShortResponseDto;
import edu.bbte.idde.dhim2228.mapper.EventMapper;
import edu.bbte.idde.dhim2228.model.*;
import edu.bbte.idde.dhim2228.repository.AttendeeRepository;
import edu.bbte.idde.dhim2228.repository.EventFilterRepository;
import edu.bbte.idde.dhim2228.service.EventService;
import edu.bbte.idde.dhim2228.service.exceptions.NotFoundException;
import edu.bbte.idde.dhim2228.service.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventFilterRepository eventRepository;
    private final AttendeeRepository attendeeRepository;
    private final EventMapper eventMapper;

    @Override
    public Long save(EventRequestDto eventRequestDto) throws ServiceException {
        log.info("Saving event: {}", eventRequestDto);
        Long newEventId = eventRepository.save(eventMapper.toEntityDto(eventRequestDto)).getId();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Event event = eventRepository.findById(newEventId).orElseThrow(
                () -> new NotFoundException("Event not found"));

        Attendee attendee = new Attendee();
        attendee.setId(new AttendeeId(event.getId(), user.getId()));
        attendee.setEvent(event);
        attendee.setUser(user);
        attendee.setRole(Role.ADMIN);
        attendee.setUserStatus(Status.ACCEPTED);

        attendeeRepository.save(attendee);

        return newEventId;
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
    public Page<EventShortResponseDto> getAllEvents(Pageable pageable) {
        log.info("Getting all events");
        return eventMapper.toShortResponseDtoList(eventRepository.findAll(pageable));
    }

    @Override
    public EventResponseDto getEventById(Long id) {
        log.info("Getting event with id: {}", id);
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
        return eventMapper.toResponseDto(event);
    }

    @Override
    public void deleteEvent(Long id) {
        log.warn("Deleting event with id {}", id);
        checkExistsEventById(id);
        eventRepository.deleteById(id);
    }

    @Override
    public Page<EventShortResponseDto> searchEvents(String name, String location, Pageable pageable) throws ServiceException {
        Page<Event> events = eventRepository
                .findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(name, location, pageable);

        if (events.isEmpty()) {
            throw new NotFoundException("No events found for the given name and location.");
        }

        return eventMapper.toShortResponseDtoList(events);
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
