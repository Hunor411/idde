package edu.bbte.idde.dhim2228.repository.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.dhim2228.utils.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
@RequiredArgsConstructor
public class ConnectionManager {
    private final Config config;
    private HikariDataSource dataSource;

    public synchronized HikariDataSource getDataSource() {
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
