package com.example.kino2;

public class Movie {
    public String title;
    public String genre;
    public String year;
    public int imageRes;

    public Movie(String title, String genre, String year, int imageRes) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.imageRes = imageRes;
    }
}