package com.globalhackathon.nextlyf.model;

public class OTPResponse {

    private String email;
    private String otp;

    public OTPResponse(String emailId, String otp) {
        this.email = emailId;
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }



}
