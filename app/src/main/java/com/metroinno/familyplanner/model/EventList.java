package com.metroinno.familyplanner.model;

/**
 * Created by anuj on 3/30/16.
 */
public class EventList {
    private String eventListName;
    private String owner;
    private String date;
    private String time;
    private String place;
    private String info;

    public EventList() {
    }

    public EventList(String eventListName, String owner, String date, String time, String place, String info) {
        this.eventListName = eventListName;
        this.owner = owner;
        this.date = date;
        this.time = time;
        this.place = place;
        this.info = info;
    }

    public String getEventListName() {
        return eventListName;
    }

    public String getOwner() {
        return owner;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public String getInfo() {
        return info;
    }
}