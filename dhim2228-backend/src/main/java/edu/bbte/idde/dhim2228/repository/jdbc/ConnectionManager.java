package edu.bbte.idde.dhim2228.repository.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.dhim2228.utils.Config;
import edu.bbte.idde.dhim2228.utils.ConfigType;

public class ConnectionManager {
    private static final ConfigType config = Config.getConfigData();
    private static HikariDataSource dataSource;

    public static synchronized HikariDataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(config.getJdbcUrl());
            dataSource.setDriverClassName(config.getDriverClass());
            dataSource.setUsername(config.getUsername());
            dataSource.setPassword(config.getPassword());
            dataSource.setMaximumPoolSize(10);
        }
        return dataSource;
    }
}
