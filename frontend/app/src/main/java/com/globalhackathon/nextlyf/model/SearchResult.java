package com.globalhackathon.nextlyf.model;

public class SearchResult {

    private String title;
    private String subtitle;
    private double rating;

    public SearchResult(String title, String subtitle, double rating) {
        this.title = title;
        this.subtitle = subtitle;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public double getRating() {
        return rating;
    }
}

