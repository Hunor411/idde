package edu.bbte.idde.dhim2228.repository.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.dhim2228.utils.Config;
import edu.bbte.idde.dhim2228.utils.ConfigType;

public class ConnectionManager {
    private static final ConfigType config = Config.getConfig();
    private static HikariDataSource dataSource;

    public static synchronized HikariDataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(config.getJdbcUrl());
            dataSource.setUsername(config.getUsername());
            dataSource.setPassword(config.getPassword());
            dataSource.setMaximumPoolSize(10);
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        }
        return dataSource;
    }
}
