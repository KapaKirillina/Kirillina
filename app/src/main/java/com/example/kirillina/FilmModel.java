package com.example.kirillina;

import java.util.List;

public class FilmModel {
    private String title;
    private String year;
    private String posterUrl;
    private List<String> genres;

    private List<String> countries;

    public FilmModel(String title, String year, String posterUrl, List<String> genres, List<String> countries) {
        this.title = title;
        this.year = year;
        this.posterUrl = posterUrl;
        this.genres = genres;
        this.countries = countries;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

}
