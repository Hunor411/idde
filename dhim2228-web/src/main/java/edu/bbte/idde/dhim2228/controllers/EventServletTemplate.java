package edu.bbte.idde.dhim2228.controllers;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@WebServlet("/events-template")
public class EventServletTemplate extends HttpServlet {
    private EventService eventService;
    private transient Handlebars handlebars;

    @Override
    public void init() throws ServletException {
        super.init();
        log.info("EventServletTemplate init");
        eventService = ServiceFactory.getInstance().getEventService();
        handlebars = new Handlebars(new ClassPathTemplateLoader("/templates", ".hbs"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Map<String, List<EventModel>> map = new ConcurrentHashMap<>();
            map.put("events", eventService.getAllEvents().stream().toList());

            Template template = handlebars.compile("events");
            template.apply(map, resp.getWriter());

            resp.setStatus(HttpServletResponse.SC_OK);
            log.info("GET /events-template {}", HttpServletResponse.SC_OK);
        } catch (ServiceException e) {
            log.info("GET /events-template {}", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            Map<String, Object> errorMap = new ConcurrentHashMap<>();
            errorMap.put("error", "An error occurred while fetching events. Please try again later.");

            Template template = handlebars.compile("events");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            template.apply(errorMap, resp.getWriter());
        }
    }
}
