package com.globalhackathon.nextlyf.model;

public class OnboardingItem {

    private int image;
    private String text;

    private String description;

    public OnboardingItem(int image, String text) {
        this.image = image;
        this.text = text;
    }

    public OnboardingItem(int image, String text, String description) {
        this.image = image;
        this.text = text;
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public String getDescription() {
        return description;
    }

}
