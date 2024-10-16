package com.globalhackathon.nextlyf.model;

public class OnboardingItem {

    private int image;
    private String text;

    public OnboardingItem(int image, String text) {
        this.image = image;
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

}
