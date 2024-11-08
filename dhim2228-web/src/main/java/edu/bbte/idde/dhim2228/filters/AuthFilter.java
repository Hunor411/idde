package edu.bbte.idde.dhim2228.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter("/events-template")
public class AuthFilter extends HttpFilter {
    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null) {
            log.info("Unauthorized access attempt to {} from IP: {}. Redirecting to login.",
                    req.getRequestURI(), req.getRemoteAddr());
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.sendRedirect(req.getContextPath() + "/login.html");
            return;
        }

        log.info("User '{}' accessed {}", username, req.getRequestURI());
        chain.doFilter(req, res);
    }
}
