package com.globalhackathon.nextlyf.model;

public class OTPResponse {

    private String emailId;
    private String otp;

    public OTPResponse(String emailId, String otp) {
        this.emailId = emailId;
        this.otp = otp;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }



}
