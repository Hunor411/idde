package edu.bbte.idde.dhim2228.service;

import edu.bbte.idde.dhim2228.dto.attendee.AttendeeRequestDto;
import edu.bbte.idde.dhim2228.dto.event.EventUserDetailsResponseDto;

import java.util.Collection;

public interface InvitationService {
    void addUserToEvent(Long eventId, AttendeeRequestDto data);

    void acceptInvitation(Long eventId, Long userId);

    void declineInvitation(Long eventId, Long userId);

    Collection<EventUserDetailsResponseDto> getEventUsers(Long eventId);

    void deleteUserFromEvent(Long eventId, Long userId);
}
