package edu.bbte.idde.dhim2228.service;

import edu.bbte.idde.dhim2228.dto.PaginatedResponseDto;
import edu.bbte.idde.dhim2228.dto.event.EventIdResponseDto;
import edu.bbte.idde.dhim2228.dto.event.EventRequestDto;
import edu.bbte.idde.dhim2228.dto.event.EventResponseDto;
import edu.bbte.idde.dhim2228.dto.event.EventShortResponseDto;
import org.springframework.data.domain.Pageable;

public interface EventService {
    EventIdResponseDto save(EventRequestDto eventRequestDto);

    void update(Long id, EventRequestDto eventRequestDto);

    PaginatedResponseDto<EventShortResponseDto> getAllEvents(Pageable pageable);

    EventResponseDto getEventById(Long id);

    void deleteEvent(Long id);

    PaginatedResponseDto<EventShortResponseDto> searchEvents(String name, String location, Pageable pageable);

    EventResponseDto findClosestEvent();
}
