package edu.bbte.idde.dhim2228.controller;

import edu.bbte.idde.dhim2228.dto.attendee.AttendeeRequestDto;
import edu.bbte.idde.dhim2228.dto.event.EventUserDetailsResponseDto;
import edu.bbte.idde.dhim2228.model.User;
import edu.bbte.idde.dhim2228.service.InvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("api/events")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @PostMapping("{id}/invitations")
    @PreAuthorize("@securityServiceImpl.hasAccesAdmin(authentication, #id)")
    public ResponseEntity<Void> addUserToEvent(
            @PathVariable Long id,
            @RequestBody @Valid AttendeeRequestDto attendeeRequestDto
    ) {
        invitationService.addUserToEvent(id, attendeeRequestDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}/invitations/accept")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> acceptInvitation(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        invitationService.acceptInvitation(id, user.getId());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}/invitations/decline")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> declineInvitation(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        invitationService.declineInvitation(id, user.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/invitations")
    @PreAuthorize("@securityServiceImpl.hasAcces(authentication, #id)")
    public ResponseEntity<Collection<EventUserDetailsResponseDto>> getEventUsers(@PathVariable Long id) {
        return ResponseEntity.ok(invitationService.getEventUsers(id));
    }

    @DeleteMapping("{eventId}/invitations/{userId}")
    @PreAuthorize("@securityServiceImpl.hasAccesAdmin(authentication, #eventId)")
    public ResponseEntity<Void> deleteUserFromEvent(@PathVariable Long eventId, @PathVariable Long userId) {
        invitationService.deleteUserFromEvent(eventId, userId);
        return ResponseEntity.noContent().build();
    }
}
