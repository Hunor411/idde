package edu.bbte.idde.dhim2228.utils;

import lombok.Data;

@Data
public class ConfigType {
    private String type;
    private String jdbcUrl;
    private String driverClass;
    private String username;
    private String password;
}
