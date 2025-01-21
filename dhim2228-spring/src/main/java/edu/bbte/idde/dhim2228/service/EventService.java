package edu.bbte.idde.dhim2228.service;

import edu.bbte.idde.dhim2228.dto.event.EventRequestDto;
import edu.bbte.idde.dhim2228.dto.event.EventResponseDto;
import edu.bbte.idde.dhim2228.dto.event.EventShortResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {
    Long save(EventRequestDto eventRequestDto);

    void update(Long id, EventRequestDto eventRequestDto);

    Page<EventShortResponseDto> getAllEvents(Pageable pageable);

    EventResponseDto getEventById(Long id);

    void deleteEvent(Long id);

    Page<EventShortResponseDto> searchEvents(String name, String location, Pageable pageable);

    EventResponseDto findClosestEvent();
}
