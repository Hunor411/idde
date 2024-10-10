package edu.bbte.idde.dhim2228.model;

import java.time.LocalDateTime;

public class EventModel extends BaseModel {
    private String name;
    private String location;
    private LocalDateTime date;
    private Boolean isOnline;

    public EventModel(String name, String location, LocalDateTime date, Boolean isOnline) {
        super(null);
        this.name = name;
        this.location = location;
        this.date = date;
        this.isOnline = isOnline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }
}
