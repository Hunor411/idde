package edu.bbte.idde.dhim2228.utils;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class Config {
    private static final Properties properties = new Properties();
    private static final ConfigType configData = new ConfigType();

    public static ConfigType getConfigData() {
        ConfigType copy = new ConfigType();
        copy.setType(configData.getType());
        copy.setJdbcUrl(configData.getJdbcUrl());
        copy.setUsername(configData.getUsername());
        copy.setPassword(configData.getPassword());
        return copy;
    }

    static {
        log.info("Trying to load properties file...");
        try (InputStream input = Thread
                .currentThread()
                .getContextClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                log.warn("application.properties not found in classpath");
                log.info("Using in memory database");
                configData.setType("mem");
            } else {
                properties.load(input);
                log.info("Properties file loaded successfully.");

                configData.setType(properties.getProperty("database.type"));
                if ("jdbc".equals(configData.getType())) {
                    Dotenv dotenv = Dotenv.load();
                    String username = dotenv.get("DB_USERNAME");
                    String password = dotenv.get("DB_PASSWORD");

                    configData.setJdbcUrl(properties.getProperty("jdbc.url"));
                    configData.setUsername(username);
                    configData.setPassword(password);
                }
            }
        } catch (IOException e) {
            log.error("Error loading properties file", e);
        }
    }
}
