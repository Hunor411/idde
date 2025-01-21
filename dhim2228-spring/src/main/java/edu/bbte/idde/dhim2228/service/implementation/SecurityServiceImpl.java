package edu.bbte.idde.dhim2228.service.implementation;

import edu.bbte.idde.dhim2228.service.SecurityService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
    @Override
    public boolean hasAcces(Authentication authentication, Long eventId) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority())
                        || ("ROLE_USER_EVENT_" + eventId).equals(authority.getAuthority())
                        || ("ROLE_ADMIN_EVENT_" + eventId).equals(authority.getAuthority())
                );
    }

    @Override
    public boolean hasAccesAdmin(Authentication authentication, Long eventId) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority ->
                        "ROLE_ADMIN".equals(authority.getAuthority())
                                || ("ROLE_ADMIN_EVENT_" + eventId).equals(authority.getAuthority())
                );
    }
}
