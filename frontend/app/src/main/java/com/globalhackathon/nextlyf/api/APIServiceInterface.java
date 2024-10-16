package com.globalhackathon.nextlyf.api;

import com.globalhackathon.nextlyf.model.OTPResponse;
import com.globalhackathon.nextlyf.model.SendOTPRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIServiceInterface {

    @POST("otp")
    Call<OTPResponse> sendOtp(@Body SendOTPRequest request);

}
