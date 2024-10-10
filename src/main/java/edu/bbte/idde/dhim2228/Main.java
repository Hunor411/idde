package edu.bbte.idde.dhim2228;

import edu.bbte.idde.dhim2228.dao.EventDao;
import edu.bbte.idde.dhim2228.dao.implementation.InMemoryEventDaoImpl;
import edu.bbte.idde.dhim2228.model.EventModel;
import edu.bbte.idde.dhim2228.service.EventService;
import edu.bbte.idde.dhim2228.service.NotFoundEventException;
import edu.bbte.idde.dhim2228.service.implementation.EventServiceImp;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        EventDao eventDao = new InMemoryEventDaoImpl();
        EventService service = new EventServiceImp(eventDao);

        // Első esemény létrehozása
        LocalDateTime eventDateTime1 = LocalDateTime.of(2024, 10, 8, 14, 0);
        EventModel event1 = new EventModel("Proba 1", "Otthon", eventDateTime1, false);
        service.createEvent(event1);

        // Második esemény létrehozása
        LocalDateTime eventDateTime2 = LocalDateTime.of(2024, 10, 9, 16, 30);
        EventModel event2 = new EventModel("Proba 2", "Iroda", eventDateTime2, true);
        service.createEvent(event2);

        // Harmadik esemény létrehozása
        LocalDateTime eventDateTime3 = LocalDateTime.of(2024, 10, 10, 10, 0);
        EventModel event3 = new EventModel("Proba 3", "Online", eventDateTime3, true);
        service.createEvent(event3);

        // Események kiíratása
        System.out.println("Összes esemény:");
        for (EventModel e : service.getAllEvents()) {
            System.out.println("Esemény ID: " + e.getId() + ", Cím: " + e.getLocation() + ", Időpont: " + e.getDate());
        }
        System.out.println();

        // Első esemény törlése
        Long eventIdToDelete = 1L; // Változtathatod a törlendő esemény ID-ját
        try {
            service.deleteEvent(eventIdToDelete);
            System.out.println("Esemény törölve: " + eventIdToDelete);
        } catch (NotFoundEventException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Események törlés után:");
        for (EventModel e : service.getAllEvents()) {
            System.out.println("Esemény ID: " + e.getId() + ", Cím: " + e.getLocation() + ", Időpont: " + e.getDate());
        }
    }
}
