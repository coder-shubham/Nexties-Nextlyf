package com.globalhackathon.nextlyf.model;

public class SendOTPRequest {

    private String emailId;

    public SendOTPRequest(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

}
