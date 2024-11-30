package edu.bbte.idde.dhim2228.controller;

import edu.bbte.idde.dhim2228.dto.EventResponseDto;
import edu.bbte.idde.dhim2228.mapper.EventMapper;
import edu.bbte.idde.dhim2228.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public List<EventResponseDto> getAllEvents() {
        log.info("Get all events");
        return eventMapper.toResponseDtoList(eventService.findAll());
    }

    @GetMapping("/{id}")
    public EventResponseDto getEventById(@PathVariable Long id) {
        log.info("Get event by id: {}", id);
        return eventMapper.toResponseDto(eventService.findById(id));
    }
}
