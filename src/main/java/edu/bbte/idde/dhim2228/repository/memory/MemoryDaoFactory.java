package edu.bbte.idde.dhim2228.repository.memory;

import edu.bbte.idde.dhim2228.repository.DaoFactory;

public class MemoryDaoFactory extends DaoFactory {
    @Override
    public InMemoryEventDao getMemoryDaoFactory() {
        return new InMemoryEventDao();
    }
}
