package edu.bbte.idde.dhim2228.service;

import edu.bbte.idde.dhim2228.dto.attendee.AttendeeRequestDto;
import edu.bbte.idde.dhim2228.dto.event.EventRequestDto;
import edu.bbte.idde.dhim2228.dto.event.EventResponseDto;
import edu.bbte.idde.dhim2228.dto.event.EventShortResponseDto;

import java.util.Collection;

public interface EventService {
    Long save(EventRequestDto eventRequestDto);

    void update(Long id, EventRequestDto eventRequestDto);

    Collection<EventShortResponseDto> getAllEvents();

    EventResponseDto getEventById(Long id);

    void deleteEvent(Long id);

    Collection<EventShortResponseDto> searchEvents(String name, String location);

    EventResponseDto findClosestEvent();

    void addUserToEvent(Long eventId, AttendeeRequestDto data);
}
