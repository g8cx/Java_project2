package com.example.news;

public class Event {
    private String title;
    private String description;
    private String date;
    private String time;
    private String location;
    private String city;

    public Event(String title, String description, String date, String time,
                 String location, String city) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.location = location;
        this.city = city;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; } // УБЕДИТЕСЬ ЧТО ЕСТЬ
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getLocation() { return location; }
    public String getCity() { return city; }
}