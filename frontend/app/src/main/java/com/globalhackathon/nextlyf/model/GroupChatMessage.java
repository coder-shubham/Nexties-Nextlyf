package com.globalhackathon.nextlyf.model;

import java.util.List;

public class GroupChatMessage {

    private List<GroupMessage> messages;

    public void setMessages(List<GroupMessage> messages) {
        this.messages = messages;
    }

    public List<GroupMessage> getMessages() {
        return messages;
    }
}
