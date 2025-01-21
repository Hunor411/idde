package edu.bbte.idde.dhim2228.controller;

import edu.bbte.idde.dhim2228.dto.attendee.AttendeeRequestDto;
import edu.bbte.idde.dhim2228.dto.event.EventUserDetailsResponseDto;
import edu.bbte.idde.dhim2228.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("api/events")
@RequiredArgsConstructor
public class InvitationController {
    private final EventService eventService;

    @PostMapping("{id}/invitations")
    public ResponseEntity<Void> addUserToEvent(
            @PathVariable Long id,
            @RequestBody @Valid AttendeeRequestDto attendeeRequestDto
    ) {
        eventService.addUserToEvent(id, attendeeRequestDto);
        return ResponseEntity.ok().build();
    }

    // projekthez elfogadni es torolni a meghivast, majd a jwt token alapjan
    @PatchMapping("{id}/invitations/accept")
    public ResponseEntity<Void> acceptInvitation(@PathVariable Long id) {

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}/invitations/decline")
    public ResponseEntity<Void> declineInvitation(@PathVariable Long id) {

        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/invitations")
    public ResponseEntity<Collection<EventUserDetailsResponseDto>> getEventUsers(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventUsers(id));
    }

    @DeleteMapping("{eventId}/invitations/{userId}")
    public ResponseEntity<Void> deleteUserFromEvent(@PathVariable Long eventId, @PathVariable Long userId) {
        eventService.deleteUserFromEvent(eventId, userId);
        return ResponseEntity.noContent().build();
    }
}
