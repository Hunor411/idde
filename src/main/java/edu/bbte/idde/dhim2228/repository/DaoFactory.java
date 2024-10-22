package edu.bbte.idde.dhim2228.repository;

import com.sun.tools.jconsole.JConsoleContext;
import edu.bbte.idde.dhim2228.repository.exceptions.RepositoryException;
import edu.bbte.idde.dhim2228.repository.memory.InMemoryEventDao;
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
            instance = new MemoryDaoFactory();
            log.info("Using in memory implementation.");
        } else if ("jdbc".equals(config.getType())) {
//            instance = new
            log.info("Using JDBC implementation.");
        } else {
            log.error("Unsupported database type: {}", config.getType());
            throw new RepositoryException("Unsupported database type: " + config.getType());
        }
        instance = new MemoryDaoFactory();
        return instance;
    }

    public abstract EventDao getEventDao();
}
