package edu.bbte.idde.dhim2228.service;

import edu.bbte.idde.dhim2228.dto.EventRequestDto;
import edu.bbte.idde.dhim2228.dto.EventResponseDto;
import edu.bbte.idde.dhim2228.service.exceptions.ServiceException;

import java.util.Collection;

public interface EventService {
    Long save(EventRequestDto eventRequestDto) throws ServiceException;

    void update(Long id, EventRequestDto eventRequestDto) throws ServiceException;

    Collection<EventResponseDto> getAllEvents() throws ServiceException;

    EventResponseDto getEventById(Long id) throws ServiceException;

    void deleteEvent(Long id) throws ServiceException;

    Collection<EventResponseDto> searchEvents(String name, String location) throws ServiceException;

    EventResponseDto findClosestEvent() throws ServiceException;
}
