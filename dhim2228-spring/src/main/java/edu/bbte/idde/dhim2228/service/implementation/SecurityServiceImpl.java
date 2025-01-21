package edu.bbte.idde.dhim2228.service.implementation;

import edu.bbte.idde.dhim2228.model.Status;
import edu.bbte.idde.dhim2228.model.User;
import edu.bbte.idde.dhim2228.service.SecurityService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class SecurityServiceImpl implements SecurityService {
    @Override
    public boolean hasAcces(Authentication authentication, Long eventId) {
        return checkAccess(authentication, eventId, authority ->
                ("ROLE_USER_EVENT_" + eventId).equals(authority)
                        || ("ROLE_ADMIN_EVENT_" + eventId).equals(authority));
    }

    @Override
    public boolean hasAccesAdmin(Authentication authentication, Long eventId) {
        return checkAccess(authentication, eventId, ("ROLE_ADMIN_EVENT_" + eventId)::equals);
    }

    private boolean checkAccess(Authentication authentication, Long eventId, Predicate<String> additionalRoleCheck) {
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()))) {
            return true;
        }

        User user = (User) authentication.getPrincipal();
        boolean hasValidEventAccess = user.getEvents().stream()
                .anyMatch(attendee -> attendee.getEvent().getId().equals(eventId)
                        && attendee.getUserStatus() == Status.ACCEPTED);

        return hasValidEventAccess
                && authentication.getAuthorities().stream()
                .anyMatch(authority -> additionalRoleCheck.test(authority.getAuthority()));
    }
}