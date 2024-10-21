package edu.bbte.idde.dhim2228.repository;

import edu.bbte.idde.dhim2228.repository.exceptions.RepositoryException;
import edu.bbte.idde.dhim2228.repository.memory.InMemoryEventDao;
import edu.bbte.idde.dhim2228.repository.memory.MemoryDaoFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DaoFactory {
    private static DaoFactory instance;

    public static synchronized DaoFactory getInstance() {
        if (instance != null) {
            return instance;
        }

        String databaseType = "mem";
        if ("mem".equals(databaseType)) {
            instance = new MemoryDaoFactory();
            log.info("Using in memory implementation.");
        } else if ("jdbc".equals(databaseType)) {
//            instance = new
            log.info("Using JDBC implementation.");
        } else {
            log.error("Unsupported database type: {}", databaseType);
            throw new RepositoryException("Unsupported database type: " + databaseType);
        }
        instance = new MemoryDaoFactory();
        return instance;
    }

    public abstract InMemoryEventDao getMemoryDaoFactory();
}
