package edu.bbte.idde.dhim2228.repository.jdbc;

import edu.bbte.idde.dhim2228.repository.DaoFactory;
import edu.bbte.idde.dhim2228.repository.EventDao;

public class JdbcDaoFactory extends DaoFactory {
    @Override
    public EventDao getEventDao() {
        return new JdbcEventDao();
    }
}
