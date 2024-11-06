package edu.bbte.idde.dhim2228.repository.memory;

import edu.bbte.idde.dhim2228.repository.DaoFactory;
import edu.bbte.idde.dhim2228.repository.EventDao;

public class MemoryDaoFactory extends DaoFactory {
    private static final InMemoryEventDao eventDao = new InMemoryEventDao();

    @Override
    public EventDao getEventDao() {
        return eventDao;
    }
}
