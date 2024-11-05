package edu.bbte.idde.dhim2228.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.bbte.idde.dhim2228.model.EventModel;
import edu.bbte.idde.dhim2228.service.EventService;
import edu.bbte.idde.dhim2228.service.ServiceFactory;
import edu.bbte.idde.dhim2228.service.exceptions.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@WebServlet("/events")
public class EventServlet extends HttpServlet {
    private ObjectMapper objectMapper;
    private EventService eventService;

    @Override
    public void init() throws ServletException {
        super.init();
        log.info("EventServlet init");
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        eventService = ServiceFactory.getInstance().getEventService();
    }

    private void sendErrorJson(HttpServletResponse resp, String message) throws IOException {
        ObjectNode errorJson = objectMapper.createObjectNode();
        errorJson.put("error", message);
        objectMapper.writeValue(resp.getWriter(), errorJson);
    }

    private void sendMessageJson(HttpServletResponse resp, String message) throws IOException {
        ObjectNode json = objectMapper.createObjectNode();
        json.put("message", message);

        objectMapper.writeValue(resp.getWriter(), message);
    }

    private void sendInvalidNumberFormatError(HttpServletResponse resp, String method) throws IOException {
        log.error("{} /events {} error: Invalid number format.", method, HttpServletResponse.SC_BAD_REQUEST);
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        sendErrorJson(resp, "Invalid number format");
    }

    private void sendServerError(HttpServletResponse resp, String method) throws IOException {
        log.error("{} /events {} error: Internal server error.", method, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        sendErrorJson(resp, "Internal server error");
    }

    private void sendNotFoundError(HttpServletResponse resp, String method, Long id) throws IOException {
        log.error("{} /events {} error: Not found.", method, HttpServletResponse.SC_NOT_FOUND);
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        String errorMessage = String.format("Event with id %d not found", id);
        sendErrorJson(resp, errorMessage);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try {
            String idString = req.getParameter("id");
            if (idString != null) {
                Long id = Long.parseLong(idString);

                EventModel event = eventService.getEventById(id);
                if (event == null) {
                    sendNotFoundError(resp, req.getMethod(), id);
                    return;
                }

                objectMapper.writeValue(resp.getWriter(), event);
                return;
            }

            var events = eventService.getAllEvents();
            objectMapper.writeValue(resp.getWriter(), objectMapper.convertValue(events, ArrayList.class));
        } catch (NumberFormatException e) {
            sendInvalidNumberFormatError(resp, req.getMethod());
        } catch (ServiceException e) {
            sendServerError(resp, req.getMethod());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try {
            String idString = req.getParameter("id");
            if (idString == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                sendErrorJson(resp, "Missing id");
                return;
            }

            Long id = Long.parseLong(idString);

            if (eventService.getEventById(id) == null) {
                sendNotFoundError(resp, req.getMethod(), id);
            }

            eventService.deleteEvent(id);
            resp.setStatus(HttpServletResponse.SC_OK);
            sendMessageJson(resp, "Event successfully deleted");
        } catch (NumberFormatException e) {
            sendInvalidNumberFormatError(resp, req.getMethod());
        } catch (ServiceException e) {
            sendServerError(resp, req.getMethod());
        }
    }
}
