package com.globalhackathon.nextlyf.model;

import com.google.gson.annotations.SerializedName;

public class ChatRequest {

    @SerializedName("session_id")
    private String sessionId;

    @SerializedName("new_session")
    private Boolean newSession;

    @SerializedName("user_query")
    private String userQuery;

    @SerializedName("user_details")
    private UserDetails userDetails;

    public ChatRequest(String sessionId, Boolean newSession, String userQuery, UserDetails userDetails) {
        this.sessionId = sessionId;
        this.newSession = newSession;
        this.userQuery = userQuery;
        this.userDetails = userDetails;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Boolean getNewSession() {
        return newSession;
    }

    public void setNewSession(Boolean newSession) {
        this.newSession = newSession;
    }

    public String getUserQuery() {
        return userQuery;
    }

    public void setUserQuery(String userQuery) {
        this.userQuery = userQuery;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }


}
