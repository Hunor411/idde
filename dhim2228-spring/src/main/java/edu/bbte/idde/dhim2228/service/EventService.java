package edu.bbte.idde.dhim2228.service;

import edu.bbte.idde.dhim2228.dto.event.EventRequestDto;
import edu.bbte.idde.dhim2228.dto.event.EventResponseDto;

import java.util.Collection;

public interface EventService {
    Long save(EventRequestDto eventRequestDto);

    void update(Long id, EventRequestDto eventRequestDto);

    Collection<EventResponseDto> getAllEvents();

    EventResponseDto getEventById(Long id);

    void deleteEvent(Long id);

    Collection<EventResponseDto> searchEvents(String name, String location);

    EventResponseDto findClosestEvent();
}
