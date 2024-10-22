package edu.bbte.idde.dhim2228.repository;

import edu.bbte.idde.dhim2228.repository.exceptions.RepositoryException;
import edu.bbte.idde.dhim2228.repository.jdbc.JdbcDaoFactory;
import edu.bbte.idde.dhim2228.repository.memory.MemoryDaoFactory;
import edu.bbte.idde.dhim2228.utils.Config;
import edu.bbte.idde.dhim2228.utils.ConfigType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DaoFactory {
    private static DaoFactory instance;

    public static synchronized DaoFactory getInstance() {
        if (instance != null) {
            return instance;
        }

        ConfigType config = Config.getConfig();
        System.out.println(config.toString());
        if ("mem".equals(config.getType())) {
            log.info("Using in memory implementation.");
            instance = new MemoryDaoFactory();
        } else if ("jdbc".equals(config.getType())) {
            log.info("Using JDBC implementation.");
            instance = new JdbcDaoFactory();
        } else {
            log.error("Unsupported database type: {}", config.getType());
            throw new RepositoryException("Unsupported database type: " + config.getType());
        }
        return instance;
    }

    public abstract EventDao getEventDao();
}
