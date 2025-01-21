package edu.bbte.idde.dhim2228.service;

import org.springframework.security.core.Authentication;

public interface SecurityService {
    boolean hasAcces(Authentication authentication, Long eventId);

    boolean hasAccesAdmin(Authentication authentication, Long eventId);
}
