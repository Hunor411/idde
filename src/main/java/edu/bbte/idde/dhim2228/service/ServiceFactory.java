package edu.bbte.idde.dhim2228.service;

import edu.bbte.idde.dhim2228.service.implementation.EventServiceImp;

public class ServiceFactory {
    private static ServiceFactory instance;

    public static synchronized ServiceFactory getInstance() {
        if (instance != null) {
            return instance;
        }

        instance = new ServiceFactory();
        return instance;
    }

    public EventService getEventService() {
        return new EventServiceImp();
    }
}
