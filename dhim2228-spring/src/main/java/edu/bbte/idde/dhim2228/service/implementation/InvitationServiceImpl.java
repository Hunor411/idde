package edu.bbte.idde.dhim2228.service.implementation;

import edu.bbte.idde.dhim2228.dto.attendee.AttendeeRequestDto;
import edu.bbte.idde.dhim2228.dto.event.EventUserDetailsResponseDto;
import edu.bbte.idde.dhim2228.mapper.AttendeeMapper;
import edu.bbte.idde.dhim2228.model.*;
import edu.bbte.idde.dhim2228.repository.AttendeeRepository;
import edu.bbte.idde.dhim2228.repository.EventRepository;
import edu.bbte.idde.dhim2228.repository.UserRepository;
import edu.bbte.idde.dhim2228.service.InvitationService;
import edu.bbte.idde.dhim2228.service.exceptions.NotFoundException;
import edu.bbte.idde.dhim2228.service.exceptions.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final AttendeeRepository attendeeRepository;
    private final AttendeeMapper attendeeMapper;

    @Override
    public void addUserToEvent(Long eventId, AttendeeRequestDto data) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + eventId));

        User user = userRepository.findByUsername(data.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + data.getUsername()));

        if (event.getAttendees().stream().anyMatch(a -> a.getUser().getUsername().equals(data.getUsername()))) {
            throw new UserAlreadyExistException(
                    "User with id " + data.getUsername() + " is already part of event with id " + eventId
            );
        }

        log.info("Adding user {} to event with id: {}", data.getUsername(), eventId);

        Attendee attendee = new Attendee();
        attendee.setId(new AttendeeId(event.getId(), user.getId()));
        attendee.setEvent(event);
        attendee.setUser(user);
        attendee.setRole(data.getRole());

        attendeeRepository.save(attendee);
    }

    @Override
    public void acceptInvitation(Long eventId, Long userId) {
        eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Event not found with id: " + eventId));

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
    public void declineInvitation(Long eventId, Long userId) {
        eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Event not found with id: " + eventId));

        Attendee attendee = attendeeRepository.findById(new AttendeeId(eventId, userId))
                .orElseThrow(() -> new NotFoundException(
                        "User with id " + userId + " has no invitation for event with id " + eventId
                ));

        if (attendee.getUserStatus() == Status.REJECTED) {
            throw new IllegalStateException("User has already rejected the invitation.");
        }

        attendee.setUserStatus(Status.REJECTED);
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
