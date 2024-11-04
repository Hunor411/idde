package edu.bbte.idde.dhim2228.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.dhim2228.repository.DaoFactory;
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
        eventService = ServiceFactory.getInstance().getEventService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var events = eventService.getAllEvents();
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getWriter(), events);
        } catch (ServiceException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
