package edu.bbte.idde.dhim2228.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        log.info("Login attempt with username: {}", username);

        if (!USERNAME.equals(username) || !PASSWORD.equals(password)) {
            log.warn("Unauthorized login attempt for username: {}", username);
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.sendRedirect(req.getContextPath() + "/login.html");
            return;
        }

        session.setAttribute("username", username);
        log.info("User '{}' logged in successfully", username);
        resp.sendRedirect(req.getContextPath() + "/events-template");
    }
}
