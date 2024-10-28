package com.globalhackathon.nextlyf.model;

import com.google.firebase.firestore.PropertyName;

public class Events {


    @PropertyName("Name")
    private String name;

    @PropertyName("Image")
    private String eventImage;

    @PropertyName("Tags")
    private String eventTags;

    @PropertyName("Date")
    private String date;
    private String mutual;

    @PropertyName("Capacity")
    private Long total;

    @PropertyName("EventLocation")
    private String eventLocation;

    @PropertyName("Category")
    private String category;

    @PropertyName("Price")
    private String eventPrice;

    public Events(){
    }

    public Events(String name, String eventImage, String eventTags, String date, String mutual, Long total, String eventLocation, String category, String eventPrice) {
        this.name = name;
        this.eventImage = eventImage;
        this.eventTags = eventTags;
        this.date = date;
        this.mutual = mutual;
        this.total = total;
        this.eventLocation = eventLocation;
        this.category = category;
        this.eventPrice = eventPrice;
    }

    @PropertyName("Image")
    public String getEventImage() {
        return eventImage;
    }

    @PropertyName("Image")
    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    @PropertyName("Name")
    public String getName() {
        return name;
    }

    @PropertyName("Name")
    public void setName(String name) {
        this.name = name;
    }
    @PropertyName("Tags")
    public String getEventTags() {
        return eventTags;
    }

    @PropertyName("Tags")
    public void setEventTags(String eventTags) {
        this.eventTags = eventTags;
    }

    @PropertyName("Date")
    public String getDate() {
        return date;
    }

    @PropertyName("Date")
    public void setDate(String date) {
        this.date = date;
    }

    public String getMutual() {
        return mutual;
    }

    public void setMutual(String mutual) {
        this.mutual = mutual;
    }


    @PropertyName("Capacity")
    public Long getTotal() {
        return total;
    }


    @PropertyName("Capacity")
    public void setTotal(Long total) {
        this.total = total;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    @PropertyName("Category")
    public String getCategory() {
        return category;
    }

    @PropertyName("Category")
    public void setCategory(String category) {
        this.category = category;
    }

    @PropertyName("Price")

    public String getEventPrice() {
        return eventPrice;
    }


    @PropertyName("Price")
    public void setEventPrice(String eventPrice) {
        this.eventPrice = eventPrice;
    }
}
