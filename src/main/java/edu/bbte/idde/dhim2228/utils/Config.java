package edu.bbte.idde.dhim2228.utils;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class Config {
    private static final Properties properties = new Properties();
    private static final ConfigType config = new ConfigType();

    public static ConfigType getConfig() {
        ConfigType copy = new ConfigType();
        copy.setType(config.getType());
        copy.setJdbcUrl(config.getJdbcUrl());
        copy.setUsername(config.getUsername());
        copy.setPassword(config.getPassword());
        return copy;
    }

    static {
        log.info("Trying to load properties file...");
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                log.warn("application.properties not found in classpath");
                log.info("Using in memory database");
                config.setType("mem");
            } else {
                properties.load(input);
                log.info("Properties file loaded successfully.");

                config.setType(properties.getProperty("database.type"));
                if (config.getType().equals("jdbc")) {
                    Dotenv dotenv = Dotenv.load();
                    String username = dotenv.get("DB_USERNAME");
                    String password = dotenv.get("DB_PASSWORD");

                    config.setJdbcUrl(properties.getProperty("jdbc.url"));
                    config.setUsername(username);
                    config.setPassword(password);
                }
            }
        } catch (IOException e) {
            log.error("Error loading properties file", e);
        }
    }
}
