package com.globalhackathon.nextlyf.model;

public class SendOTPRequest {

    private String email;

    public SendOTPRequest(String emailId) {
        this.email = emailId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
