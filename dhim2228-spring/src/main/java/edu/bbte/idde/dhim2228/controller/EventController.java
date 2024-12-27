package edu.bbte.idde.dhim2228.controller;

import edu.bbte.idde.dhim2228.dto.attendee.AttendeeRequestDto;
import edu.bbte.idde.dhim2228.dto.event.EventRequestDto;
import edu.bbte.idde.dhim2228.dto.event.EventResponseDto;
import edu.bbte.idde.dhim2228.dto.event.EventShortResponseDto;
import edu.bbte.idde.dhim2228.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Long> createEvent(@Valid @RequestBody EventRequestDto eventRequestDto) {
        Long id = eventService.save(eventRequestDto);
        URI createUri = URI.create("/api/events/" + id);
        return ResponseEntity.created(createUri).build();
    }

    @GetMapping
    public ResponseEntity<Collection<EventShortResponseDto>> getAllEvents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location
    ) {
        if (name != null || location != null) {
            return ResponseEntity.ok(eventService.searchEvents(name, location));
        }

        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable Long id) {
        log.info("Get event by id: {}", id);
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventRequestDto eventRequestDto) {
        eventService.update(id, eventRequestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/closest")
    public ResponseEntity<EventResponseDto> getClosestEvent() {
        return ResponseEntity.ok(eventService.findClosestEvent());
    }

    @PostMapping("{id}/users")
    public ResponseEntity<Void> addUserToEvent(@PathVariable Long id, @RequestBody AttendeeRequestDto attendeeRequestDto) {
        eventService.addUserToEvent(id, attendeeRequestDto);
        return ResponseEntity.ok().build();
    }
}
