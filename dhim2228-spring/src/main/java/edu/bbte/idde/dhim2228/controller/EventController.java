package edu.bbte.idde.dhim2228.controller;

import edu.bbte.idde.dhim2228.dto.EventRequestDto;
import edu.bbte.idde.dhim2228.dto.EventResponseDto;
import edu.bbte.idde.dhim2228.mapper.EventMapper;
import edu.bbte.idde.dhim2228.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @PostMapping
    public ResponseEntity<Void> createEvent(@Valid @RequestBody EventRequestDto eventRequestDto) {
        eventService.create(eventMapper.toEntityDto(eventRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventRequestDto eventRequestDto) {
        eventService.update(id, eventMapper.toEntityDto(eventRequestDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
