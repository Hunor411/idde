package edu.bbte.idde.dhim2228.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "database")
public class Config {
    private String type;
    private String jdbcUrl;
    private String driverClass;
    private String username;
    private String password;
}
