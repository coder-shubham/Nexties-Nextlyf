package com.globalhackathon.nextlyf.model;

public class ChatMessage {

    private String text;
    private Boolean isBot;
    private Boolean isRoomObject;

    public ChatMessage(String text, Boolean isBot) {
        this.text = text;
        this.isBot = isBot;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getIsBot() {
        return isBot;
    }

    public void setIsBot(Boolean isBot) {
        this.isBot = isBot;
    }

    public Boolean getIsRoomObject() {
        return isRoomObject == null ? false : isRoomObject;
    }

    public void setIsRoomObject(Boolean isRoomObject) {
        this.isRoomObject = isRoomObject;
    }
}
