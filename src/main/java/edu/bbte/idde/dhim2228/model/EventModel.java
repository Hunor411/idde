package edu.bbte.idde.dhim2228.model;

import java.time.LocalDateTime;

public class EventModel extends BaseModel {
    private String name;
    private String location;
    private LocalDateTime date;
    private Boolean isOnline;
    private String description;
    private Integer attendeesCount;

    public EventModel(String name, String location, LocalDateTime date, Boolean isOnline, String description, Integer attendeesCount) {
        super(null);
        this.name = name;
        this.location = location;
        this.date = date;
        this.isOnline = isOnline;
        this.description = description;
        this.attendeesCount = attendeesCount;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAttendeesCount() {
        return attendeesCount;
    }

    public void setAttendeesCount(Integer attendeesCount) {
        this.attendeesCount = attendeesCount;
    }
}
