package edu.bbte.idde.dhim2228.security.jwt;

import edu.bbte.idde.dhim2228.model.User;
import edu.bbte.idde.dhim2228.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtUtils jwtutils;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("Incoming request: [Method: {}, URI: {}, IP: {}]",
                request.getMethod(),
                request.getRequestURI(),
                request.getRemoteAddr());

        String jwtToken = jwtutils.getJwtFromCookies(request);
        if (jwtToken != null && !jwtToken.isBlank() && jwtutils.validateJwtToken(jwtToken)) {
            String username = jwtutils.getUsernameFromJwtToken(jwtToken);
            User user = userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            log.warn("No valid JWT token found for request: [Method: {} URI: {}, IP: {}]",
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getRemoteAddr());
        }

        filterChain.doFilter(request, response);
    }
}