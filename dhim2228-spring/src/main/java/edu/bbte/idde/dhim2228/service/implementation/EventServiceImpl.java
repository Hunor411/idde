package edu.bbte.idde.dhim2228.service.implementation;

import edu.bbte.idde.dhim2228.dto.attendee.AttendeeRequestDto;
import edu.bbte.idde.dhim2228.dto.event.EventRequestDto;
import edu.bbte.idde.dhim2228.dto.event.EventResponseDto;
import edu.bbte.idde.dhim2228.dto.event.EventShortResponseDto;
import edu.bbte.idde.dhim2228.dto.event.EventUserDetailsResponseDto;
import edu.bbte.idde.dhim2228.mapper.AttendeeMapper;
import edu.bbte.idde.dhim2228.mapper.EventMapper;
import edu.bbte.idde.dhim2228.model.*;
import edu.bbte.idde.dhim2228.repository.AttendeeRepository;
import edu.bbte.idde.dhim2228.repository.EventFilterRepository;
import edu.bbte.idde.dhim2228.repository.UserRepository;
import edu.bbte.idde.dhim2228.service.EventService;
import edu.bbte.idde.dhim2228.service.exceptions.NotFoundException;
import edu.bbte.idde.dhim2228.service.exceptions.ServiceException;
import edu.bbte.idde.dhim2228.service.exceptions.UserAlreadyExistException;
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
    private final EventFilterRepository eventRepository;
    private final AttendeeRepository attendeeRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;
    private final AttendeeMapper attendeeMapper;

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
    public Collection<EventShortResponseDto> getAllEvents() {
        log.info("Getting all events");
        return eventMapper.toShortResponseDtoList(new ArrayList<>(eventRepository.findAll()));
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
    public Collection<EventShortResponseDto> searchEvents(String name, String location) throws ServiceException {
        Collection<Event> events = eventRepository
                .findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(name, location);
        if (events == null || events.isEmpty()) {
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

    @Override
    public void addUserToEvent(Long eventId, AttendeeRequestDto data) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + eventId));

        User user = userRepository.findById(data.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + data.getUserId()));

        if (event.getAttendees().stream().anyMatch(a -> a.getUser().getId().equals(data.getUserId()))) {
            throw new UserAlreadyExistException(
                    "User with id " + data.getUserId() + " is already part of event with id " + eventId
            );
        }

        log.info("Adding user {} to event with id: {}", data.getUserId(), eventId);

        Attendee attendee = new Attendee();
        attendee.setId(new AttendeeId(event.getId(), user.getId()));
        attendee.setEvent(event);
        attendee.setUser(user);
        attendee.setRole(data.getRole());

        attendeeRepository.save(attendee);
    }

    @Override
    public void acceptInvitation(Long eventId, Long userId) {
        checkExistsEventById(eventId);
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        Attendee attendee = attendeeRepository.findById(new AttendeeId(eventId, userId))
                .orElseThrow(() -> new NotFoundException(
                        "User with id " + userId + " has no invitation for event with id " + eventId
                ));

        if (attendee.getUserStatus() == Status.ACCEPTED) {
            throw new IllegalStateException("User has already accepted the invitation.");
        }

        attendee.setUserStatus(Status.ACCEPTED);
        attendeeRepository.save(attendee);
    }

    @Override
    public Collection<EventUserDetailsResponseDto> getEventUsers(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Event not found with id: " + id)
        );
        log.info("Fetching users with id: {}", id);
        return attendeeMapper.toEventUserDetailsResponseDtoList(event.getAttendees());
    }

    @Override
    public void deleteUserFromEvent(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + eventId));

        Attendee attendee = event.getAttendees().stream()
                .filter(a -> a.getUser().getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "User with id " + userId + " is not part of event with id " + eventId
                ));

        event.getAttendees().remove(attendee);
        attendeeRepository.delete(attendee);
    }
}
