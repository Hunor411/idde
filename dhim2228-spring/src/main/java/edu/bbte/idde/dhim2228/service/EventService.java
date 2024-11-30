package edu.bbte.idde.dhim2228.service;

import edu.bbte.idde.dhim2228.model.Event;

public interface EventService extends BaseService<Event> {
    Event findClosestEvent();
}
