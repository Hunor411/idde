package edu.bbte.idde.dhim2228.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "database")
public class Config {
    private String type;
    private String jdbcUrl;
    private String driverClass;
    private String username;
    private String password;
}
